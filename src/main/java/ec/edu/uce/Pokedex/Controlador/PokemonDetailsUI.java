package ec.edu.uce.Pokedex.Controlador;

import ec.edu.uce.Pokedex.Modelo.Pokemon;

import javax.swing.*;
import java.awt.*;

import static ec.edu.uce.Pokedex.Controlador.panel.CreateAreaPanel.createAreaPanel;
import static ec.edu.uce.Pokedex.Controlador.panel.CreateFormsPanel.createFormsPanel;
import static ec.edu.uce.Pokedex.Controlador.panel.CreateInfoPanel.createInfoPanel;

public class PokemonDetailsUI {

    public PokemonDetailsUI(Pokemon pokemon) {
        JFrame detailsFrame = new JFrame("Detalles de " + pokemon.getName());
        detailsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        detailsFrame.setSize(800, 600);

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Nombre del Pokémon y tipo
        JLabel pokemonNameLabel = new JLabel("No. " + pokemon.getId() + " " + pokemon.getName(), SwingConstants.CENTER);
        pokemonNameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        mainPanel.add(pokemonNameLabel, BorderLayout.NORTH);

        // Panel dinamico (cetro)
        JPanel dynamicPanel = new JPanel(new CardLayout());
        mainPanel.add(dynamicPanel, BorderLayout.CENTER);

        // Panel info
            JPanel infoPanel = createInfoPanel(pokemon);
            dynamicPanel.add(infoPanel, "info");

        // Panel Area
        JPanel areaPanel = createAreaPanel(pokemon);
        dynamicPanel.add(areaPanel, "Area");

        // Panel central con imagen y detalles
        JPanel formsPanel = createFormsPanel(pokemon);
        dynamicPanel.add(formsPanel, "Forms");

        //Botones de navegacion
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton infoButton = new JButton("Info");
        JButton areaButton = new JButton("Area");
        JButton formsButton = new JButton("Forms");
        JButton cancelButton = new JButton("Salir");

        buttonPanel.add(infoButton);
        buttonPanel.add(areaButton);
        buttonPanel.add(formsButton);
        buttonPanel.add(cancelButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Agregar acciones a los butones
        CardLayout cl = (CardLayout) dynamicPanel.getLayout();
        infoButton.addActionListener(e -> cl.show(dynamicPanel, "info"));
        areaButton.addActionListener(e -> cl.show(dynamicPanel, "Area"));
        formsButton.addActionListener(e -> cl.show(dynamicPanel, "Forms"));
        cancelButton.addActionListener(e -> detailsFrame.dispose());

        //mostrar frame :)
        detailsFrame.add(mainPanel);
        detailsFrame.setVisible(true);

    }










}
