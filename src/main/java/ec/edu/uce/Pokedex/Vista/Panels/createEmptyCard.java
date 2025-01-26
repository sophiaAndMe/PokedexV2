package ec.edu.uce.Pokedex.Vista.Panels;

import ec.edu.uce.Pokedex.Modelo.Pokemon;
import ec.edu.uce.Pokedex.Vista.PokemonDetailsUI;

import javax.swing.*;
import java.awt.*;

public class createEmptyCard {

    public static JPanel createEmptyCardForUI() {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        // Imagen del Pokémon
        JLabel imageLabel = new JLabel(new ImageIcon("src/main/resources/static/images/loading.png")); // Imagen genérica
        JLabel nameLabel = new JLabel("Cargando...");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);


        card.add(imageLabel);
        card.add(nameLabel);

        return card;
    }
}
