package ec.edu.uce.Pokedex.Service.Repositorios;

import ec.edu.uce.Pokedex.Modelo.PokemonType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Repository
public interface pokemonTypeRepository extends R2dbcRepository<PokemonType, Integer> {


    Mono<PokemonType> findById(Integer integer);
    Mono<PokemonType> findByName(String name);


}
