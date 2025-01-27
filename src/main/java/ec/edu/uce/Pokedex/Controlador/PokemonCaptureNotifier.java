package ec.edu.uce.Pokedex.Controlador;

import ec.edu.uce.Pokedex.Modelo.Pokemon;
import ec.edu.uce.Pokedex.Service.PokemonRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


public class PokemonCaptureNotifier {

    PokemonRepository pokemonRepository;

    // cree una lista para obtener los observados
    List<PokemonCaptureObserver> observers = new ArrayList<>();

    // si lo ha observado lo implementa en la lista
    public void addObserver(PokemonCaptureObserver observer) {
        observers.add(observer);
    }
    // si no lo ha observado lo quita de la lista
    public void removeObserver(PokemonCaptureObserver observer) {
        observers.remove(observer);
    }
    // metodo para notificar que se ha capturado un pokemon
    public void notifyPokemonCaptured(Pokemon pokemon) {
        for (PokemonCaptureObserver observer : observers) {
            //observer.onPokemonCapture(pokemon);
        }
    }





}
