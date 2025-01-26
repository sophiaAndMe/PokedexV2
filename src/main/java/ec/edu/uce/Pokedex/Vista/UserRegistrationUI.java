package ec.edu.uce.Pokedex.Vista;

import ec.edu.uce.Pokedex.Modelo.Pokemon;
import ec.edu.uce.Pokedex.Modelo.PokemonUsuario;
import ec.edu.uce.Pokedex.Modelo.Usuario;
import ec.edu.uce.Pokedex.Service.PokemonRepository;
import ec.edu.uce.Pokedex.Service.PokemonService;
import ec.edu.uce.Pokedex.Service.complements.ThreadDataBase;
import ec.edu.uce.Pokedex.Service.UsuarioRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.swing.*;
import java.util.Optional;

public class UserRegistrationUI {

    // uso del repositorio
    private static PokemonService pokemonService;
    private static UsuarioRepository UsuarioRepository;
    private static ThreadDataBase threadDataBase;
    private static PokemonRepository pokemonRepository;
    private static Pokemon pokemon;
    private static Usuario usuario;
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

        JFrame frame = new JFrame("Registro de aventurero Pokemon!!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,300);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel usernameLabel = new JLabel("Usuario:");
        JTextField nameField = new JTextField();
        JLabel genero = new JLabel("Genero");
        JComboBox<String> generoCombo = new JComboBox<>(new String[]{"Masculino", "Feminino"});
        JButton registrarButton = new JButton("Ingresar");

        registrarButton.addActionListener(e -> {

            //guardo la info en un String
            String name = nameField.getText();
            String generoLogin = (String)generoCombo.getSelectedItem();

            // verificar si el usuario existe
            Optional<Usuario> user = UsuarioRepository.findByName(name);
            if(user.isPresent()) {
                JOptionPane.showMessageDialog(frame, "Usuario ya existe" + name);
                if(pokemon==null){
                    threadDataBase.firstPokemon();
                }else {
                    try {
                        Thread.sleep(2000);
                        JOptionPane.showMessageDialog(frame, "Cargando pokemons...");
                        new PokedexUI(usuario);
                    }catch (InterruptedException exx){
                        exx.printStackTrace();
                    }
                    // modificado

                    frame.dispose();
                }
            }else{
                pokemonService.saveUser(name, generoLogin);
                JOptionPane.showMessageDialog(frame, "Usuario registrado exitosamente");
                new StarterSelectionUI(context, usuario);
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
