package ec.edu.uce.Pokedex.Service;

import ec.edu.uce.Pokedex.Modelo.Pokemon;
import ec.edu.uce.Pokedex.Modelo.PokemonUsuario;
import ec.edu.uce.Pokedex.Modelo.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Service
public class UsuarioService {

    @Autowired
    private PokemonUsuarioRepository pokemonUsuarioRepository;

    @Autowired
    private PokemonRepository pokemonRepository;

    public void asignarPokemonInicial(Usuario usuario, Pokemon pokemon) {
        // Buscar el Pokémon
        PokemonUsuario pokemonUsuario = new PokemonUsuario();
        pokemonUsuario.setUsuario(usuario);
        pokemonUsuario.setPokemon(pokemon);

        // Guardar en la base de datos
        pokemonUsuarioRepository.save(pokemonUsuario);
    }

    public boolean verificarCaptura(Usuario usuario, Pokemon pokemon) {
        // Buscar en la tabla intermedia si el usuario ya capturó el Pokémon
        return usuario.getPokemons().contains(pokemon);
    }





}
