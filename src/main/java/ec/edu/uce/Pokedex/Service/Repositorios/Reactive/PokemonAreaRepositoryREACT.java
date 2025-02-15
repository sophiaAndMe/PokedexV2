package ec.edu.uce.Pokedex.Service.Repositorios.Reactive;

import ec.edu.uce.Pokedex.Modelo.PokemonAbility;
import ec.edu.uce.Pokedex.Modelo.PokemonLocation;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface PokemonAreaRepositoryREACT extends R2dbcRepository<PokemonLocation, Long> {

    Mono<PokemonLocation> findByName(String name);
}

