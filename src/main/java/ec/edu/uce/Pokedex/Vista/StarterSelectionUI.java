package ec.edu.uce.Pokedex.Vista;

import ec.edu.uce.Pokedex.Modelo.Pokemon;
import ec.edu.uce.Pokedex.Modelo.PokemonUsuario;
import ec.edu.uce.Pokedex.Modelo.Usuario;
import ec.edu.uce.Pokedex.Service.PokemonRepository;
import ec.edu.uce.Pokedex.Service.UsuarioRepository;
import ec.edu.uce.Pokedex.Service.UsuarioService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;


public class StarterSelectionUI {


    private static PokemonRepository pokemonRepository;
    private static UsuarioService usuarioService;
    private final Usuario usuario;

    public StarterSelectionUI(ApplicationContext context,Usuario usuario) {

        pokemonRepository = context.getBean(PokemonRepository.class);
        usuarioService = context.getBean(UsuarioService.class);
        this.usuario = usuario;

        JFrame frame = new JFrame("Seleccion Pokemon :)");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,400);
        frame.setLayout(new BorderLayout());

        JLabel label = new JLabel("Seleccione una pokemon", SwingConstants.CENTER);
        label.setFont(new Font("Tahoma", Font.PLAIN, 20));
        frame.add(label, BorderLayout.CENTER);

        JPanel selectionPanel = new JPanel();
        selectionPanel.setLayout(new GridLayout(1,3));

        String [] startes = {"bulbasaur", "charmander", "squirtle"};
        for(String starter : startes){
            JPanel pokemonPanel1 = createPokemonPanel(starter,frame);
            selectionPanel.add(pokemonPanel1);
        }

        frame.add(selectionPanel, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    private JPanel createPokemonPanel(String pokemonName, JFrame parentFrame) {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Obtener Pokémon del repositorio
        Optional<Pokemon> optionalPokemon = pokemonRepository.findByName(pokemonName);
        if (optionalPokemon.isEmpty()) {
            JLabel errorLabel = new JLabel("Error al cargar " + pokemonName);
            panel.add(errorLabel);
            return panel;
        }

        Pokemon pokemon = optionalPokemon.get();

        // Imagen del Pokémon
        JLabel imageLabel = new JLabel(new ImageIcon("src/main/resources/static/images/" + pokemonName.toLowerCase() + "_front.png"));
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Nombre del Pokémon
        JLabel nameLabel = new JLabel(pokemon.getName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Verificar si el Pokémon ya fue capturado
        boolean isCaptured = usuarioService.verificarCaptura(usuario, pokemon);

        JButton selectButton = new JButton(isCaptured ? "Capturado" : "Elegir " + pokemonName);
        selectButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        selectButton.setEnabled(!isCaptured); // Deshabilitar si ya fue capturado
        selectButton.addActionListener(e -> {
            // Asocia el Pokémon inicial al usuario
            usuarioService.asignarPokemonInicial(usuario, pokemon);

            // Muestra un mensaje de éxito
            JOptionPane.showMessageDialog(parentFrame, "¡Has elegido a " + pokemon.getName() + "!");

            // Cierra la ventana actual y abre PokedexUI

            SwingUtilities.invokeLater(() -> new PokedexUI(usuario));
            parentFrame.dispose();
        });


        // Agregar componentes al panel
        panel.add(imageLabel);
        panel.add(nameLabel);
        panel.add(selectButton);
        return panel;
    }

}
