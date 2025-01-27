
package ec.edu.uce.Pokedex.Vista;
import ec.edu.uce.Pokedex.Modelo.Pokemon;
import ec.edu.uce.Pokedex.Modelo.PokemonUsuario;
import ec.edu.uce.Pokedex.Modelo.Usuario;
import ec.edu.uce.Pokedex.Service.PokemonRepository;
import ec.edu.uce.Pokedex.Service.PokemonService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static ec.edu.uce.Pokedex.Vista.UserRegistrationUI.usuario;


public class PokedexUI {
    private static PokemonRepository pokemonRepository;
    private static PokemonService pokemonService;
    private JPanel pokemonPanel; // Panel para mostrar los Pokémon
    private JFrame frame; // Ventana principal
     // Mapa para guardar las tarjetas de Pokémon por nombre
    ///*
    /// No me refresca la pantalla por que es null
    ///
     static Map<String, JPanel> pokemonCards = new HashMap<>();

    public PokedexUI(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
        this.pokemonCards = new HashMap<>(); // Asegúrate de inicializar el mapa aquí
        iniciar(usuario); // Si tienes una lógica para construir la interfaz
    }


    public void iniciar(Usuario usuario){

                ApplicationContext context = new AnnotationConfigApplicationContext("ec.edu.uce.Pokedex");

        // Repositorio de Pokémon
        pokemonRepository = context.getBean(PokemonRepository.class);
        pokemonService = context.getBean(PokemonService.class);



        JFrame frame = new JFrame("Pokedex");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Tu Pokédex", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        frame.add(titleLabel, BorderLayout.NORTH);

        // Panel para la barra de búsqueda y el botón de captura
        JPanel inputPanel = new JPanel();

        JTextField filterPokemonByName = new JTextField(20);

        JButton captureButton = new JButton("Capturar");

        // Acción del botón de captura
        captureButton.addActionListener(e -> {
            String pokemonName = filterPokemonByName.getText().trim();
            if (!pokemonName.isEmpty()) {
                Optional<Pokemon> optionalPokemon = pokemonRepository.findByName(pokemonName);
                if (optionalPokemon.isPresent()) {
                    Pokemon pokemon = optionalPokemon.get();
                    // Cambiar el estado del Pokémon a capturado
                    //pokemonService.capturarPokemon(usuario, pokemon);
                    pokemon.setIs_Default(true); // Cambia su estado
                    pokemonRepository.save(pokemon); // Guarda el cambio en la base de datos

                    // Actualizar la tarjeta específica del Pokémon
                    updatePokemonCard(pokemon);
                    JOptionPane.showMessageDialog(frame, pokemonName + " ha sido capturado.");
                } else {
                    JOptionPane.showMessageDialog(frame, "El Pokémon " + pokemonName + " no existe.");
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Por favor, ingresa un nombre de Pokémon.");
            }
        });
                inputPanel.add(filterPokemonByName);
        inputPanel.add(captureButton);
        frame.add(inputPanel, BorderLayout.SOUTH);

        pokemonPanel = new JPanel();
        pokemonPanel.setLayout(new GridLayout(0, 4, 10, 10));

        List<Pokemon> capturedPokemons = usuario.getPokemonUsuarios()
                .stream()
                .map(PokemonUsuario::getPokemon)
                .toList();

        for (Pokemon pokemon : pokemonRepository.findAll()) {
            boolean isCaptured = capturedPokemons.contains(pokemon);
            JPanel pokemonCard = createPokemonCard(pokemon, isCaptured);

            // Agrega la tarjeta al mapa
            pokemonCards.put(pokemon.getName().toLowerCase(), pokemonCard);

            pokemonPanel.add(pokemonCard); // Agrega al panel principal
        }


        JScrollPane scrollPane = new JScrollPane(pokemonPanel);
        frame.add(scrollPane, BorderLayout.CENTER);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    /**
     * Método para actualizar la tarjeta específica de un Pokémon.
     */

    private void updatePokemonCard(Pokemon pokemon) {
        String pokemonName = pokemon.getName().toLowerCase();

        if (!pokemonCards.containsKey(pokemonName)) {
            System.out.println("La tarjeta no existe para: " + pokemonName + ". Creando una nueva tarjeta...");
            boolean isCaptured = true; // Suponemos que el Pokémon está capturado
            JPanel newCard = createPokemonCard(pokemon, isCaptured);

            pokemonCards.put(pokemonName, newCard); // Añade al mapa
            pokemonPanel.add(newCard); // Añade al panel

            pokemonPanel.revalidate(); // Actualiza diseño
            pokemonPanel.repaint();   // Redibuja
        } else {
            JPanel card = pokemonCards.get(pokemonName);
            if (card != null) {
                card.removeAll();
                boolean isCaptured = true;
                JPanel updatedCard = createPokemonCard(pokemon, isCaptured);

                pokemonCards.put(pokemonName, updatedCard);
                pokemonPanel.remove(card);
                pokemonPanel.add(updatedCard);

                pokemonPanel.revalidate();
                pokemonPanel.repaint();
            }
        }
    }


    /**
     * Método para crear una tarjeta de Pokémon.
     */
    private JPanel createPokemonCard(Pokemon pokemon, boolean isCaptured) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        // Imagen del Pokémon
        JLabel imageLabel;
        if (pokemon.getIs_Default()) {
            imageLabel = new JLabel(new ImageIcon("src/main/resources/static/images/" + pokemon.getName().toLowerCase() + "_front.png"));
        } else {
            imageLabel = new JLabel(new ImageIcon(new ImageIcon("src/main/resources/static/black/unknow.png")
                    .getImage().getScaledInstance(100,100,Image.SCALE_SMOOTH))); // Imagen genérica para Pokémon no capturados
        }
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Nombre del Pokémon
        JLabel nameLabel = new JLabel(pokemon.getIs_Default() ? pokemon.getName() : "???");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Botón "Más información" (solo para Pokémon capturados)
        JButton infoButton = new JButton("Más información");
        infoButton.setEnabled(pokemon.getIs_Default()); // Habilitar o deshabilitar según el estado de captura
        infoButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoButton.addActionListener(e -> {
            if (pokemon.getIs_Default()) {
                new PokemonDetailsUI(pokemon); // Abre la ventana con más detalles
            }
        });

        // Agregar componentes al panel
        card.add(imageLabel);
        card.add(nameLabel);
        card.add(infoButton);

        return card;
    }
}
