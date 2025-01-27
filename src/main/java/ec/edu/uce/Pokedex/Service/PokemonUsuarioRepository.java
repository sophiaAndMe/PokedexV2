package ec.edu.uce.Pokedex.Service;

import ec.edu.uce.Pokedex.Modelo.Pokemon;
import ec.edu.uce.Pokedex.Modelo.PokemonUsuario;
import ec.edu.uce.Pokedex.Modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PokemonUsuarioRepository extends JpaRepository<PokemonUsuario,Integer> {

    boolean existsByUsuarioAndPokemon(Usuario usuario, Pokemon pokemon);
    // Ejemplo: Encontrar por usuario
    List<PokemonUsuario> findById(Long id);
}