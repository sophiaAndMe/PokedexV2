package ec.edu.uce.Pokedex.Vista.Panels;

import ec.edu.uce.Pokedex.Modelo.Pokemon;
import ec.edu.uce.Pokedex.Modelo.PokemonLocation;
import ec.edu.uce.Pokedex.Service.PokemonRepository;
import ec.edu.uce.Pokedex.Service.PokemonService;
import ec.edu.uce.Pokedex.Service.pokemonAreaRepository;
import org.hibernate.Session;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CreateAreaPanel {
    // area
    private static PokemonService pokemonService;


    public static JPanel createAreaPanel(Pokemon pokemon) {

        ApplicationContext context = new AnnotationConfigApplicationContext("ec.edu.uce.Pokedex");

        // Obtén el servicio desde el contexto
        pokemonService= context.getBean(PokemonService.class);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel titleArea = new JLabel("Areas de aparicion");
        titleArea.setFont(new Font("Arial", Font.BOLD, 20));
        titleArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleArea);

        // Buscar áreas en el repositorio
        System.out.println("Buscando áreas para: " + pokemon.getName());

            List<PokemonLocation> locations = pokemonService.getPokemonLocations(Math.toIntExact((Long) pokemon.getId())); // Asegúrate que devuelve una lista

            if (locations != null ) {
                for (PokemonLocation location : locations) {
                    JLabel locationLabel = new JLabel("Ability:" + location.getName());
                    locationLabel.setFont(new Font("Arial", Font.PLAIN, 16));
                    locationLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                    panel.add(locationLabel);
                }
            } else {
                JLabel noLocationLabel = new JLabel("No se encontraron áreas.");
                noLocationLabel.setFont(new Font("Arial", Font.BOLD, 16));
                noLocationLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                panel.add(noLocationLabel);
            }


        return panel;

    }
}

