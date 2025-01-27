
package ec.edu.uce.Pokedex.Controlador;
import ec.edu.uce.Pokedex.Modelo.Pokemon;
import ec.edu.uce.Pokedex.Modelo.PokemonUsuario;
import ec.edu.uce.Pokedex.Modelo.Usuario;
import ec.edu.uce.Pokedex.Service.Repositorios.PokemonRepository;
import ec.edu.uce.Pokedex.Service.PokemonService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static ec.edu.uce.Pokedex.Vista.UserRegistrationUI.usuario;


public class PokedexUI {
    private static PokemonRepository pokemonRepository;
    private static PokemonService pokemonService;
    private JPanel pokemonPanel; // Panel para mostrar los Pokémon
    private JFrame frame; // Ventana principal
     static Map<String, JPanel> pokemonCards = new HashMap<>();
    private final ExecutorService executorService =
            Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public PokedexUI(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
        this.pokemonCards = new HashMap<>();
        iniciar(usuario);
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
        /// **** TAMBIEN SE CAMBIOOO
        captureButton.addActionListener(e -> {
            String pokemonName = filterPokemonByName.getText().trim();
            if (!pokemonName.isEmpty()) {
                executorService.submit(() -> {
                    Optional<Pokemon> optionalPokemon = pokemonRepository.findByName(pokemonName);
                    SwingUtilities.invokeLater(() -> {
                        if (optionalPokemon.isPresent()) {
                            Pokemon pokemon = optionalPokemon.get();
                            pokemon.setIs_Default(true);
                            pokemonRepository.save(pokemon);
                            updatePokemonCard(pokemon);
                            JOptionPane.showMessageDialog(frame, pokemonName + " ha sido capturado.");
                        } else {
                            JOptionPane.showMessageDialog(frame, "El Pokémon " + pokemonName + " no existe.");
                        }
                    });
                });
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


            ///**** AQUI MODIFIQUE

        executorService.submit(() -> {
            List<Pokemon> allPokemons = pokemonRepository.findAll();
            int batchSize = 100; // Cantidad de Pokémon procesados por lote
            for (int i = 0; i < allPokemons.size(); i += batchSize) {
                List<Pokemon> batch = allPokemons.subList(i, Math.min(i + batchSize, allPokemons.size()));

                // Procesa cada lote en un hilo secundario
                SwingUtilities.invokeLater(() -> {
                    for (Pokemon pokemon : batch) {
                        boolean isCaptured = capturedPokemons.contains(pokemon);
                        JPanel pokemonCard = createPokemonCard(pokemon, isCaptured);
                        pokemonCards.put(pokemon.getName().toLowerCase(), pokemonCard);
                        pokemonPanel.add(pokemonCard);
                    }
                    pokemonPanel.revalidate();
                    pokemonPanel.repaint();
                });
            }
        });


            JScrollPane scrollPane = new JScrollPane(pokemonPanel);
            frame.add(scrollPane, BorderLayout.CENTER);

            frame.setLocationRelativeTo(null);
            frame.setVisible(true);



    }



    // actualizar cartas
    private void updatePokemonCard(Pokemon pokemon) {
        executorService.submit(() -> {
            String pokemonName = pokemon.getName().toLowerCase();
            boolean isCaptured = true;

            JPanel updatedCard = createPokemonCard(pokemon, isCaptured);

            SwingUtilities.invokeLater(() -> {
                JPanel existingCard = pokemonCards.get(pokemonName);
                if (existingCard != null) {
                    pokemonPanel.remove(existingCard);
                }
                pokemonCards.put(pokemonName, updatedCard);
                pokemonPanel.add(updatedCard);
                pokemonPanel.revalidate();
                pokemonPanel.repaint();
            });
        });
    }



    // crear cartas
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
