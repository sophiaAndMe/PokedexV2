package ec.edu.uce.Pokedex.Controlador;

import ec.edu.uce.Pokedex.Modelo.Pokemon;
import ec.edu.uce.Pokedex.Modelo.PokemonImagen;
import ec.edu.uce.Pokedex.Service.PokemonService;
import ec.edu.uce.Pokedex.Service.Repositorios.PokemonRepository;
import ec.edu.uce.Pokedex.Service.complements.ThreadDataBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
/*
    /// falta el controler :)
 */
public class PokeController {

    @Autowired
    ThreadDataBase threadDataBase;

    @Autowired
    PokemonService pokemonService;

    @Autowired
    PokemonRepository pokemonRepository;


    @GetMapping("/")
    public String home() {
        return "index"; // Nombre del archivo HTML sin extensión
    }

    @GetMapping("/cargar")
    public String cargarPokemonsEndpoint(Model model)  {

        threadDataBase.iniciarCargaDePokemons();
        model.addAttribute("message", "Se han cargado 30 pokemons");
        return "index";
    }



    @GetMapping("/galeria")
    public String showGallery(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "search", required = false) String searchQuery,
            Model model) {

        int pageSize = 10;
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<Pokemon> pokemonPage;

        if(searchQuery != null && !searchQuery.isEmpty()) {
            pokemonPage = pokemonService.searchPokemons(searchQuery, pageable);
        } else {
            pokemonPage = pokemonService.getAllPokemons(pageable);
        }

        model.addAttribute("pokemons", pokemonPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("nextPage", page + 1);
        model.addAttribute("prevPage", page - 1);

        return "cargarPokemonsView";
    }

    @GetMapping("/pokemon/{id}")
    public String showPokemonDetail(@PathVariable Long id, Model model) throws InterruptedException {
        Optional<Pokemon> pokemon = pokemonService.getPokemonById(id);
        if (pokemon.isPresent()) {
            model.addAttribute("pokemon", pokemon.get());
            //threadDataBase.finalCargaDePokemons();
            return "pokemon-detail"; // Nombre de la nueva plantilla
        } else {
            return "error"; // Plantilla para Pokémon no encontrado
        }

    }


}
