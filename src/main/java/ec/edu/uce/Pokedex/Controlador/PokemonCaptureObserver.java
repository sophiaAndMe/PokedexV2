package ec.edu.uce.Pokedex.Controlador;

import ec.edu.uce.Pokedex.Modelo.Pokemon;

public interface PokemonCaptureObserver {

    void onPokemonCapture(Pokemon pokemon);
}
