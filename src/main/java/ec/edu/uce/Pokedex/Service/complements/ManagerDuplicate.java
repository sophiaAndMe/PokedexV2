package ec.edu.uce.Pokedex.Service.complements;

import ec.edu.uce.Pokedex.Modelo.Pokemon;
import ec.edu.uce.Pokedex.Modelo.PokemonAbility;
import ec.edu.uce.Pokedex.Modelo.PokemonLocation;
import ec.edu.uce.Pokedex.Modelo.PokemonType;
import ec.edu.uce.Pokedex.Service.Repositorios.PokemonRepository;
import ec.edu.uce.Pokedex.Service.Repositorios.Reactive.PokemonAbilitiyRepositoryREACT;
import ec.edu.uce.Pokedex.Service.Repositorios.Reactive.PokemonAreaRepositoryREACT;
import ec.edu.uce.Pokedex.Service.Repositorios.Reactive.PokemonRepositoryReative;
import ec.edu.uce.Pokedex.Service.Repositorios.pokemonTypeRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class ManagerDuplicate {

    /**
     * Manejo de tipos: Busca un tipo de Pokémon por nombre y, si no existe, lo crea.
     * Agrega el tipo tanto a la lista "types" como a la entidad Pokemon.
     */
    public Mono<Void> ManejoTipos(pokemonTypeRepository pokemonTypeRepository,
                                  Pokemon pokemon, String typeName, List<PokemonType> types) {
        return pokemonTypeRepository.findByName(typeName)
                .switchIfEmpty(Mono.defer(() -> {
                    PokemonType newType = new PokemonType();
                    newType.setName(typeName);
                    return pokemonTypeRepository.save(newType);
                }))
                .doOnNext(pokemonType -> {
                    types.add(pokemonType);
                    if (!pokemon.getTypes().contains(pokemonType)) {
                        pokemon.getTypes().add(pokemonType);
                    }
                })
                .then();
    }

    /**
     * Manejo de habilidades: Busca una habilidad por nombre; si no existe, la crea.
     * Luego, la agrega a la lista "abilities" y a la entidad Pokemon si no está ya asociada.
     */
    public Mono<Void> ManejoHabilidad(PokemonAbilitiyRepositoryREACT pokemonAbilitiyRepositoryREACT,
                                      Pokemon pokemon, String abilityName, List<PokemonAbility> abilities) {
        return pokemonAbilitiyRepositoryREACT.findByName(abilityName)
                .switchIfEmpty(Mono.defer(() -> {
                    PokemonAbility newAbility = new PokemonAbility();
                    newAbility.setName(abilityName);
                    return pokemonAbilitiyRepositoryREACT.save(newAbility);
                }))
                .doOnNext(ability -> {
                    abilities.add(ability);
                    if (!pokemon.getAbilities().contains(ability)) {
                        pokemon.getAbilities().add(ability);
                    }
                })
                .then();
    }

    /**
     * Manejo de áreas: Busca una ubicación por nombre; si no existe, la crea.
     * Luego, la agrega a la lista "locations" y a la entidad Pokemon si no está ya asociada.
     */
    public Mono<Void> ManejoAreas(PokemonAreaRepositoryREACT pokemonAreaRepositoryREACT,
                                  Pokemon pokemon, String areaName, List<PokemonLocation> locations) {
        return pokemonAreaRepositoryREACT.findByName(areaName)
                .switchIfEmpty(Mono.defer(() -> {
                    PokemonLocation newLocation = new PokemonLocation();
                    newLocation.setName(areaName);
                    return pokemonAreaRepositoryREACT.save(newLocation);
                }))
                .doOnNext(location -> {
                    locations.add(location);
                    if (!pokemon.getLocation_area_encounters().contains(location)) {
                        pokemon.getLocation_area_encounters().add(location);
                    }
                })
                .then();
    }

    /**
     * Manejo de nombre de Pokémon: Verifica si ya existe un Pokémon con el nombre dado.
     * Imprime un mensaje si existe. Se devuelve Mono<Void> para encadenar operaciones.
     */
    public Mono<Void> manejoPokemonName(PokemonRepositoryReative pokemonRepositoryReative, String name) {
        return pokemonRepositoryReative.findByName(name)
                .doOnNext(pokemon -> {
                    System.out.println("Ya existe " + name + " en la base de datos");
                })
                .then();
    }
}
