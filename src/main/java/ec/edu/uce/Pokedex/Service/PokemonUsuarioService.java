package ec.edu.uce.Pokedex.Service;

import ec.edu.uce.Pokedex.Modelo.PokemonUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PokemonUsuarioService {

    @Autowired
    private PokemonUsuarioRepository pokemonUsuarioRepository;

    public List<PokemonUsuario> findById(Long id) {
        return pokemonUsuarioRepository.findById(id);
    }
}
