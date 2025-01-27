package ec.edu.uce.Pokedex.Service.complements;

import ec.edu.uce.Pokedex.Service.PokemonRepository;
import ec.edu.uce.Pokedex.Vista.PokedexUI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PokedexUIFactory {

    private final PokemonRepository pokemonRepository;

    @Autowired
    public PokedexUIFactory(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
    }

    public PokedexUI createPokedexUI() {
        return new PokedexUI(pokemonRepository);
    }
}
