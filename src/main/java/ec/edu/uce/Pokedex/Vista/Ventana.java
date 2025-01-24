package ec.edu.uce.Pokedex.Vista;


import ec.edu.uce.Pokedex.Service.PokemonService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;


public class Ventana  {


    private static PokemonService pokemonService;
    private static int id;

    public static void main(String[] args) {

        // Inicializa el contexto de Spring
        ApplicationContext context = new AnnotationConfigApplicationContext("ec.edu.uce.Pokedex");

        // Obtén el servicio desde el contexto
        pokemonService = context.getBean(PokemonService.class);

        // Inicializa la ventana gráfica
        SwingUtilities.invokeLater(() -> crearInterfaz());

    }
    static JLabel resultLabel = new JLabel();
    static JLabel imagenLabel = new JLabel();

    public static void crearInterfaz() {
        JFrame frame = new JFrame("Pokedex");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JLabel label = new JLabel("Ingrese el ID del Pokémon:");
        JLabel label2 = new JLabel("Ingrese el ID del Pokémon:");
        JTextField textField = new JTextField(10);
        JButton button = new JButton("Buscar");



        button.addActionListener(e -> {
            try {
                id = Integer.parseInt(textField.getText());
                var pokemon = pokemonService.findById(id);
                Icon icon = new ImageIcon(new ImageIcon(("src/main/resources/static/images/"+id+"_front.png"))
                        .getImage().getScaledInstance(500,500,2));
                resultLabel.setText("Nombre: " + pokemon.getName());
                imagenLabel.setIcon(icon);
            } catch (Exception ex) {
                resultLabel.setText("Error: " + ex.getMessage());
            }
        });




        JPanel panel = new JPanel();
        panel.add(label);
        panel.add(label2);
        panel.add(textField);
        panel.add(button);
        panel.add(resultLabel);
        panel.add(imagenLabel);

        frame.add(panel);
        frame.setVisible(true);
    }

//    public void jButton1ActionPerformed(ActionEvent event){
//        Icon icon = new ImageIcon(new ImageIcon(getClass().getResource("src/main/resources/static/images/1_front.png"))
//                .getImage().getScaledInstance(1,1,0));
//
//        resultLabel.setIcon(icon);
//    }
}
