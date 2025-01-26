package ec.edu.uce.Pokedex.Service;

import ec.edu.uce.Pokedex.Modelo.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PokemonRepository extends JpaRepository<Pokemon, Long> {

    Optional<Pokemon> findByName(String name);
    Pokemon findById(long id);

}
