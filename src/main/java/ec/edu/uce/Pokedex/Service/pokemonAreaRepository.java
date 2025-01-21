package ec.edu.uce.Pokedex.Service;

import ec.edu.uce.Pokedex.Modelo.PokemonAbility;
import ec.edu.uce.Pokedex.Modelo.PokemonLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface pokemonAreaRepository extends JpaRepository <PokemonLocation, Long>{

    Optional<PokemonLocation> findByName(String nameArea);

}
