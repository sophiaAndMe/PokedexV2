package ec.edu.uce.Pokedex.Controlador;

import ec.edu.uce.Pokedex.Service.PokemonWebService;
import ec.edu.uce.Pokedex.Service.complements.ThreadDataBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import reactor.core.publisher.Mono;

@Controller
/*
    /// falta el controler :)
 */
public class PokeController {


    @Autowired
    PokemonWebService pokemonWebService;

    @PostMapping("/cargar")
    public String cargarPokemonsEndpoint() {
        pokemonWebService.fetchAndSabePokemonReactive(1); // Ejemplo: carga los primeros 100 Pokémon
        return "Se han cargado 100 pokemons";
    }
}
