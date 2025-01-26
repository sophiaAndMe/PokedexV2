package ec.edu.uce.Pokedex.Vista;

import ec.edu.uce.Pokedex.Modelo.Pokemon;
import ec.edu.uce.Pokedex.Service.PokemonRepository;
import ec.edu.uce.Pokedex.Service.PokemonService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PokedexUI {

    // servicios
    private static PokemonRepository pokemonRepository;

    public PokedexUI(PokemonRepository pokemonRepository) {
        // implementado contexto
        // Inicializa el contexto de Spring

        ApplicationContext context = new AnnotationConfigApplicationContext("ec.edu.uce.Pokedex");

        // Obtén el servicio desde el contexto
        pokemonRepository = context.getBean(PokemonRepository.class);


        PokemonRepository finalPokemonRepository = pokemonRepository;
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Pokedex");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);

            // Main Panel
            JPanel mainPanel = new JPanel(new BorderLayout());

            // Left Panel (List of Pokémon)
            DefaultListModel<String> pokemonListModel = new DefaultListModel<>();
            JList<String> pokemonList = new JList<>(pokemonListModel);

            for (Pokemon pokemon : finalPokemonRepository.findAll()) {
                pokemonListModel.addElement(pokemon.getId() + " " + pokemon.getName());
            }

            JScrollPane listScrollPane = new JScrollPane(pokemonList);
            listScrollPane.setPreferredSize(new Dimension(200, 600));

            mainPanel.add(listScrollPane, BorderLayout.WEST);

            // Right Panel (Details of Selected Pokémon)
            JPanel detailsPanel = new JPanel();
            detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));

            JLabel pokemonImage = new JLabel();
            pokemonImage.setPreferredSize(new Dimension(600, 200));
            pokemonImage.setHorizontalAlignment(SwingConstants.CENTER);
            pokemonImage.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            JLabel pokemonNameLabel = new JLabel("Name: ", SwingConstants.RIGHT);

            detailsPanel.removeAll();
            detailsPanel.add(Box.createVerticalStrut(20));
            detailsPanel.add(pokemonNameLabel); // nombre del pokemon
            detailsPanel.add(Box.createVerticalStrut(10));
            detailsPanel.add(pokemonImage); //imagen del pokemon
            detailsPanel.add(Box.createVerticalStrut(10));

            // poner aqui arriba las abilidades

            //button
            JButton moreInfoButton = new JButton("Mas Informacion...");
            moreInfoButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            detailsPanel.add(moreInfoButton);
            detailsPanel.revalidate();
            detailsPanel.repaint();

            //button
            moreInfoButton.addActionListener(e2 -> {
                String selectedPokemon2 = pokemonList.getSelectedValue();
                if (selectedPokemon2 != null) {
                    int pokemonId = Integer.parseInt(selectedPokemon2.split(" ")[0]);
                    Pokemon selected = finalPokemonRepository.findById(pokemonId);

                    if (selected != null) {
                        // Abre la nueva ventana con la información completa del Pokémon
                        new PokemonDetailsUI(selected);
                    }
                }
            });

            mainPanel.add(detailsPanel, BorderLayout.CENTER);

            // Add Main Panel to Frame
            frame.add(mainPanel);

            pokemonList.addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    String selectedPokemon = pokemonList.getSelectedValue();

                    if (selectedPokemon != null) {
                        try {
                            // Extrae el ID del Pokémon (antes del espacio en el texto de la lista)
                            int pokemonId = Integer.parseInt(selectedPokemon.split(" ")[0]);

                            // Obtén el Pokémon desde el repositorio
                            Pokemon pokemon = finalPokemonRepository.findById(pokemonId);

                            if (pokemon != null) {
                                // Actualiza los detalles en el panel
                                pokemonNameLabel.setText(pokemon.getName());
                                pokemonNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);// Nombre arriba
                                pokemonImage.setIcon(new ImageIcon(new ImageIcon("src/main/resources/static/images/" + pokemon.getName() + "_front.png")
                                        .getImage().getScaledInstance(350, 350, Image.SCALE_SMOOTH)));
                                pokemonImage.setAlignmentX(Component.CENTER_ALIGNMENT);// Imagen más grande
                            } else {
                                // Si no encuentra el Pokémon, limpia los detalles
                                pokemonImage.setIcon(null);
                                pokemonNameLabel.setText("Name: ");
                            }
                        } catch (NumberFormatException ex) {
                            // Manejo de errores por si el ID no es un número
                            System.err.println("Error parsing Pokémon ID: " + ex.getMessage());
                        }
                    }
                }
            });

            frame.setVisible(true);
        });

    }
}
