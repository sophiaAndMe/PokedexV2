package ec.edu.uce.Pokedex.Service;

import ec.edu.uce.Pokedex.Modelo.PokemonUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface pokemonUserRepository extends JpaRepository<PokemonUsuario, Long> {

    Optional<PokemonUsuario> findByName(String name);
}
