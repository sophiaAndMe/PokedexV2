package ec.edu.uce.Pokedex.Vista.Panels;

import ec.edu.uce.Pokedex.Modelo.Pokemon;

import javax.swing.*;
import java.awt.*;

public class CreateFormsPanel {

    // Crear panel de "Formas"
    public static JPanel createFormsPanel(Pokemon pokemon){
        JPanel panel = new JPanel(new BorderLayout());

        // Mock : Obtener las formas del pokemon desde el repositorio
        ImageIcon form1 = new ImageIcon(new ImageIcon("src/main/resources/static/images/" + pokemon.getName() + "_front.png")
                .getImage().getScaledInstance(600, 600, Image.SCALE_SMOOTH));
        ImageIcon form2 = new ImageIcon(new ImageIcon("src/main/resources/static/images/" + pokemon.getName() + "_back.png")
                .getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));

        JLabel form1Label = new JLabel(form1);
        JLabel form2Label = new JLabel(form2);

        panel.add(form1Label);
        panel.add(form2Label);
        return panel;

    }
}
