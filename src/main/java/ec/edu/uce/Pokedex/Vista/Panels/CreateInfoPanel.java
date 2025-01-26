package ec.edu.uce.Pokedex.Vista.Panels;

import ec.edu.uce.Pokedex.Modelo.Pokemon;

import ec.edu.uce.Pokedex.Modelo.PokemonAbility;
import ec.edu.uce.Pokedex.Service.PokemonService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.swing.*;
import java.awt.*;
import java.util.List;


public class CreateInfoPanel {

    private static PokemonService pokemonService;

    // crear panel de "Info"
    public static JPanel createInfoPanel(Pokemon pokemon){

        //obtenemos el contexto
        ApplicationContext context = new AnnotationConfigApplicationContext("ec.edu.uce.Pokedex");

        // obtenemos el servicio desde el contexto
        pokemonService = context.getBean(PokemonService.class);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel pokemonImage = new JLabel();
        pokemonImage.setIcon(new ImageIcon(new ImageIcon("src/main/resources/static/images/" + pokemon.getName() + "_front.png")
                .getImage().getScaledInstance(350, 350, Image.SCALE_SMOOTH)));
        pokemonImage.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel heightLabel = new JLabel("Height: " + pokemon.getHeight() + " m");
        JLabel weightLabel = new JLabel("Weight: " + pokemon.getWeight() + " kg");

        // lo veremos despues
        List<PokemonAbility> pokemonAbilityList = pokemonService.findAbilities(pokemon.getId());
        JLabel abilityPanel = null;

        for(PokemonAbility pokemonAbility : pokemonAbilityList){
            abilityPanel = new JLabel(pokemonAbility.getName());
            abilityPanel.setFont(new Font("Arial", Font.PLAIN, 20));
            abilityPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        }

        panel.add(pokemonImage);
        panel.add(Box.createVerticalStrut(10));
        panel.add(heightLabel);
        panel.add(weightLabel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(abilityPanel);
        return panel;
    }
}
