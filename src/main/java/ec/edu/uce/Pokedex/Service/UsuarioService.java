package ec.edu.uce.Pokedex.Service;

import ec.edu.uce.Pokedex.Modelo.Pokemon;
import ec.edu.uce.Pokedex.Modelo.PokemonUsuario;
import ec.edu.uce.Pokedex.Modelo.Usuario;
import ec.edu.uce.Pokedex.Service.Repositorios.PokemonUsuarioRepository;
import ec.edu.uce.Pokedex.Service.Repositorios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PokemonUsuarioRepository pokemonUsuarioRepository;

    @Transactional
    public void asignarPokemonInicial(Usuario usuario, Pokemon pokemon) {
        // Asegurarse de que el usuario está persistido
        if (usuario.getId() == null) {
            usuarioRepository.save(usuario); // Guardar usuario si no está persistido
        }

        // Crear relación entre usuario y Pokémon
        PokemonUsuario pokemonUsuario = new PokemonUsuario();
        pokemonUsuario.setUsuario(usuario);
        pokemonUsuario.setPokemon(pokemon);

        // Guardar la relación en la base de datos
        pokemonUsuarioRepository.save(pokemonUsuario);
    }

    public boolean verificarCaptura(Usuario usuario, Pokemon pokemon) {
        // Lógica para verificar si el Pokémon ya fue capturado
        return pokemonUsuarioRepository.existsByUsuarioAndPokemon(usuario, pokemon);
    }
    public Usuario guardarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

}

