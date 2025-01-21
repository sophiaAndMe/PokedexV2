package ec.edu.uce.Pokedex.Service;

import ec.edu.uce.Pokedex.Modelo.PokemonAbility;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface pokemonAbilityRepository extends JpaRepository <PokemonAbility, Long>{


    Optional<PokemonAbility> findByName(String name);
}
