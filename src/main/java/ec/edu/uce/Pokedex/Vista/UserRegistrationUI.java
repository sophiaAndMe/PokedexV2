package ec.edu.uce.Pokedex.Vista;

import ec.edu.uce.Pokedex.Modelo.Pokemon;
import ec.edu.uce.Pokedex.Modelo.PokemonUsuario;
import ec.edu.uce.Pokedex.Modelo.Usuario;
import ec.edu.uce.Pokedex.Service.PokemonRepository;
import ec.edu.uce.Pokedex.Service.PokemonService;
import ec.edu.uce.Pokedex.Service.complements.PokedexUIFactory;
import ec.edu.uce.Pokedex.Service.complements.ThreadDataBase;
import ec.edu.uce.Pokedex.Service.UsuarioRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Optional;

public class UserRegistrationUI {

    // uso del repositorio
    private static PokemonService pokemonService;
    private static UsuarioRepository UsuarioRepository;
    private static ThreadDataBase threadDataBase;
    private static PokemonRepository pokemonRepository;
    private static Pokemon pokemon;
    static Usuario usuario;
    // metodo de registro de usuario
    public static void main(String[] args) {


        //aqui le damos el contexto de spring
        ApplicationContext context = new AnnotationConfigApplicationContext("ec.edu.uce.Pokedex");
        pokemonService = context.getBean(PokemonService.class);
        UsuarioRepository = context.getBean(UsuarioRepository.class);
        threadDataBase = context.getBean(ThreadDataBase.class);
        pokemonRepository = context.getBean(PokemonRepository.class);
        pokemon = context.getBean(Pokemon.class);
        usuario = context.getBean(Usuario.class);
        PokedexUIFactory factory = context.getBean(PokedexUIFactory.class);

        JFrame frame = new JFrame("Registro de aventurero Pokemon!!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,300);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel usernameLabel = new JLabel("Usuario:", SwingConstants.CENTER);
         // Fondo oscuro
        usernameLabel.setOpaque(true);
        JTextField nameField = new JTextField();
        JLabel genero = new JLabel("Genero");
        JComboBox<String> generoCombo = new JComboBox<>(new String[]{"Masculino", "Feminino"});
        JButton registrarButton = new JButton("Ingresar");

        registrarButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String generoLogin = (String) generoCombo.getSelectedItem();

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Por favor, ingrese un nombre de usuario.");
                return;
            }

            Optional<Usuario> userOpt = UsuarioRepository.findByNameWithPokemons(name);

            if (userOpt.isPresent()) {
                Usuario existingUser = userOpt.get();
                JOptionPane.showMessageDialog(frame, "Bienvenido de nuevo, " + existingUser.getName());

                if (pokemonRepository.count() == 0) {
                    threadDataBase.iniciarCargaDePokemons();
                }
                /// aqui aqui
                // Crear e iniciar la UI
                PokedexUI pokedexUI = factory.createPokedexUI();
                pokedexUI.iniciar(usuario);
                frame.dispose();
            } else {
                pokemonService.saveUser(name, generoLogin);
                JOptionPane.showMessageDialog(frame, "Usuario registrado exitosamente");

                threadDataBase.iniciarCargaDePokemons();
                new StarterSelectionUI(context, new Usuario(name, generoLogin));
                frame.dispose();
            }
        });




        panel.add(usernameLabel);
        panel.add(nameField);
        panel.add(genero);
        panel.add(generoCombo);
        panel.add(registrarButton);

        frame.add(panel);
        frame.setVisible(true);

    }

}
