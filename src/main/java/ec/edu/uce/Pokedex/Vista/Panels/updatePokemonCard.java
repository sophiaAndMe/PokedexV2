package ec.edu.uce.Pokedex.Vista.Panels;

import ec.edu.uce.Pokedex.Modelo.Pokemon;
import ec.edu.uce.Pokedex.Vista.PokemonDetailsUI;

import javax.swing.*;
import java.awt.*;

public class updatePokemonCard {

    public static void updatePokemonCardForUI(JPanel card, Pokemon pokemon) {
        card.removeAll(); // Limpiar el contenido previo (tarjeta vacía)

        // Imagen del Pokémon
        JLabel imageLabel = new JLabel(new ImageIcon("src/main/resources/static/images/" + pokemon.getName().toLowerCase() + "_front.png"));
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Nombre del Pokémon
        JLabel nameLabel = new JLabel(pokemon.getName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Botón "Más información"
        JButton infoButton = new JButton("Más información");
        infoButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoButton.addActionListener(e -> new PokemonDetailsUI(pokemon)); // Abre detalles del Pokémon

        // Agregar nuevos componentes
        card.add(imageLabel);
        card.add(nameLabel);
        card.add(infoButton);

        card.revalidate();
        card.repaint(); // Actualizar tarjeta visualmente
    }
}
