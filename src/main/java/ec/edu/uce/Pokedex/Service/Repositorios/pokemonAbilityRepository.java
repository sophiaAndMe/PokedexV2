package ec.edu.uce.Pokedex.Service.Repositorios;

import ec.edu.uce.Pokedex.Modelo.PokemonAbility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface pokemonAbilityRepository extends JpaRepository <PokemonAbility, Long>{


    Optional<PokemonAbility> findByName(String name);
    Optional<PokemonAbility> findById(int id);
}
