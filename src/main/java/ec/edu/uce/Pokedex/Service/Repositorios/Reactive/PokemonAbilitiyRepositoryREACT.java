package ec.edu.uce.Pokedex.Service.Repositorios.Reactive;

import ec.edu.uce.Pokedex.Modelo.PokemonAbility;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface PokemonAbilitiyRepositoryREACT extends R2dbcRepository<PokemonAbility, Long> {

    Mono<PokemonAbility> findByName(String name);
}
