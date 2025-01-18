package ec.edu.uce.Pokedex.Vista;

import ec.edu.uce.Pokedex.PokedexApplication;
import ec.edu.uce.Pokedex.Service.PokemonService;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
public class PokedexSwingApp {


    public void showWindow(){
        // ventana principal
        JFrame jFrame = new JFrame("Pokedex");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(600, 600);
        jFrame.setVisible(true);
    }
}
