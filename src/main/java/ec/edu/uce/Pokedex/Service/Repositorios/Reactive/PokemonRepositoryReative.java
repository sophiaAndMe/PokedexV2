package ec.edu.uce.Pokedex.Service.Repositorios.Reactive;

import ec.edu.uce.Pokedex.Modelo.Pokemon;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface PokemonRepositoryReative extends R2dbcRepository<Pokemon, Long> {

    Mono<Pokemon> findByName(String name);

}
