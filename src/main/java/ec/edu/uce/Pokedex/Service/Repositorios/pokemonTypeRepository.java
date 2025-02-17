package ec.edu.uce.Pokedex.Service.Repositorios;

import ec.edu.uce.Pokedex.Modelo.PokemonType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface pokemonTypeRepository extends JpaRepository<PokemonType, Integer> {


    Optional<PokemonType> findById(Integer integer);
    Optional<PokemonType> findByName(String name);


}
