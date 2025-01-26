
package ec.edu.uce.Pokedex.Vista;
import ec.edu.uce.Pokedex.Modelo.Pokemon;
import ec.edu.uce.Pokedex.Modelo.Usuario;
import ec.edu.uce.Pokedex.Service.PokemonRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PokedexUI {
    private static PokemonRepository pokemonRepository;

    public PokedexUI(Usuario usuario) {
        ApplicationContext context = new AnnotationConfigApplicationContext("ec.edu.uce.Pokedex");

        // Repositorio de Pokémon
        pokemonRepository = context.getBean(PokemonRepository.class);

        // Ventana principal
        JFrame frame = new JFrame("Pokedex");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Tu Pokédex", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        frame.add(titleLabel, BorderLayout.NORTH);

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

    private JPanel createPokemonCard(Pokemon pokemon, boolean isCaptured) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        // Imagen del Pokémon
        JLabel imageLabel;
        if (pokemon.getDefault()) {
            imageLabel = new JLabel(new ImageIcon("src/main/resources/static/images/" + pokemon.getName().toLowerCase() + "_front.png"));
        } else {
            imageLabel = new JLabel(new ImageIcon("src/main/resources/static/images/unknown.png")); // Imagen genérica para Pokémon no capturados
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