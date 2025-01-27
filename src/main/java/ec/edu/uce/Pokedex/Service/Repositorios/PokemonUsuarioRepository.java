package ec.edu.uce.Pokedex.Service.Repositorios;

import ec.edu.uce.Pokedex.Modelo.Pokemon;
import ec.edu.uce.Pokedex.Modelo.PokemonUsuario;
import ec.edu.uce.Pokedex.Modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PokemonUsuarioRepository extends JpaRepository<PokemonUsuario,Integer> {

    boolean existsByUsuarioAndPokemon(Usuario usuario, Pokemon pokemon);
    List<PokemonUsuario> findById(Long id);
}