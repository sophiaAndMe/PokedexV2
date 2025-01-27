
package ec.edu.uce.Pokedex.Vista;
import ec.edu.uce.Pokedex.Controlador.PokemonCaptureNotifier;
import ec.edu.uce.Pokedex.Controlador.PokemonCaptureObserver;
import ec.edu.uce.Pokedex.Modelo.Pokemon;
import ec.edu.uce.Pokedex.Modelo.PokemonUsuario;
import ec.edu.uce.Pokedex.Modelo.Usuario;
import ec.edu.uce.Pokedex.Service.PokemonRepository;
import ec.edu.uce.Pokedex.Service.PokemonService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PokedexUI {
    private static PokemonRepository pokemonRepository;
    private static PokemonService pokemonService;
    private Usuario usuario; // Almacena el usuario actual

    public PokedexUI(Usuario usuario) {
        this.usuario = usuario; // Guardar el usuario actual
        ApplicationContext context = new AnnotationConfigApplicationContext("ec.edu.uce.Pokedex");

        // Repositorio de Pokémon
        pokemonRepository = context.getBean(PokemonRepository.class);
        pokemonService = context.getBean(PokemonService.class);

        // Ventana principal
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
        captureButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pokemonName = filterPokemonByName.getText().trim();
                if (!pokemonName.isEmpty()) {
                    // Lógica para capturar el Pokémon por nombre
                    JOptionPane.showMessageDialog(frame, "nombre de Pokémon." + pokemonName);

                } else {
                    JOptionPane.showMessageDialog(frame, "Por favor, ingresa un nombre de Pokémon.");
                }
            }
        });

        inputPanel.add(filterPokemonByName);
        inputPanel.add(captureButton);

        frame.add(inputPanel, BorderLayout.SOUTH);

        // Panel para mostrar todos los Pokémon
        JPanel pokemonPanel = new JPanel();
        pokemonPanel.setLayout(new GridLayout(0, 4, 10, 10)); // Grilla de 4 columnas

        // Obtener todos los Pokémon y los capturados por el usuario
        List<Pokemon> allPokemons = pokemonRepository.findAll(); // Lista completa
        List<Pokemon> capturedPokemons = usuario.getPokemons();  // Pokémon capturados

        for (Pokemon pokemon : allPokemons) {
            boolean isCaptured = capturedPokemons.contains(pokemon);
            JPanel pokemonCard = createPokemonCard(pokemon, isCaptured);
            pokemonPanel.add(pokemonCard);
        }

        JScrollPane scrollPane = new JScrollPane(pokemonPanel);
        frame.add(scrollPane, BorderLayout.CENTER);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    //********************************

    private JPanel createPokemonCard(Pokemon pokemon, boolean isCaptured) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        // Imagen del Pokémon
        JLabel imageLabel;
        pokemonService.onPokemonCapture(pokemon, false);




        if (pokemon.getIs_Default()) {
            imageLabel = new JLabel(new ImageIcon("src/main/resources/static/images/" + pokemon.getName().toLowerCase() + "_front.png"));
        } else {
            imageLabel = new JLabel(new ImageIcon("src/main/resources/static/images/pikachu_front.png")); // Imagen genérica para Pokémon no capturados
        }
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Nombre del Pokémon
        JLabel nameLabel = new JLabel(isCaptured ? pokemon.getName() : "???");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Botón "Más información" (solo para Pokémon capturados)
        JButton infoButton = new JButton("Más información");
        infoButton.setEnabled(isCaptured);
        infoButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoButton.addActionListener(e -> {
            if (isCaptured) {
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