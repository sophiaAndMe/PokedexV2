package ec.edu.uce.Pokedex.Service.complements;

import ec.edu.uce.Pokedex.Modelo.Pokemon;
import ec.edu.uce.Pokedex.Modelo.PokemonAbility;
import ec.edu.uce.Pokedex.Modelo.PokemonLocation;
import ec.edu.uce.Pokedex.Modelo.PokemonType;
import ec.edu.uce.Pokedex.Service.Repositorios.PokemonRepository;
import ec.edu.uce.Pokedex.Service.Repositorios.pokemonAbilityRepository;
import ec.edu.uce.Pokedex.Service.Repositorios.pokemonAreaRepository;
import ec.edu.uce.Pokedex.Service.Repositorios.pokemonTypeRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
public class ManagerDuplicate {


    /*
     * Manejo de tipos
     */

    public void ManejoTipos(pokemonTypeRepository pokemonTypeRepository,
                            Pokemon pokemon, String typeName, List<PokemonType> types) {


        // Verifica si la habilidad ya existe en la base de datos
        PokemonType pokemonType = pokemonTypeRepository.findByName(typeName)
                .orElseGet(() -> {
                    // Si no existe, crea una nueva habilidad
                    PokemonType newType = new PokemonType();
                    newType.setName(typeName);
                    // implementar block
                    return pokemonTypeRepository.save(newType);
                });
        types.add(pokemonType);

        // Agregar la habilidad al Pokémon si no está ya asociada
        if (!pokemon.getTypes().contains(pokemonType)) {
            pokemon.getTypes().add(pokemonType);
        }
    }


    /// Manejo de Habilidades
    public void ManejoHabilidad(pokemonAbilityRepository pokemonAbilitiyRepository,
                                Pokemon pokemon, String abilityName, List<PokemonAbility> abilities) {


        // Verifica si la habilidad ya existe en la base de datos
        PokemonAbility ability = pokemonAbilitiyRepository.findByName(abilityName)
                .orElseGet(() -> {
                    // Si no existe, crea una nueva habilidad
                    PokemonAbility newAbility = new PokemonAbility();
                    newAbility.setName(abilityName);
                    return pokemonAbilitiyRepository.save(newAbility);
                });
        abilities.add(ability);

        // Agregar la habilidad al Pokémon si no está ya asociada
        if (!pokemon.getAbilities().contains(ability)) {
            pokemon.getAbilities().add(ability);
        }
    }


    /// Manejo de areas

    public void ManejoAreas(pokemonAreaRepository pokemonAreaRepository,
                            Pokemon pokemon, String areaName, List<PokemonLocation> locations){
        // Verifica si la habilidad ya existe en la base de datos
        PokemonLocation location = pokemonAreaRepository.findByName(areaName).
                orElseGet(() -> {
                    // verifica si el valor existe
                    // si es asi, lo retorna
                    // si no, ejectuta el suplicier de OPTIONAL
                    PokemonLocation pokemonLocation = new PokemonLocation();
                    pokemonLocation.setName(areaName);
                    return pokemonAreaRepository.save(pokemonLocation);
                });
        locations.add(location);

        // agrega el area al Pokemon si ay no esta asociada
        if(!pokemon.getLocation_area_encounters().contains(location)){
            pokemon.getLocation_area_encounters().add(location);
        }
    }

    /// manejo de pokemons
    public void manejoPokemonName(PokemonRepository pokemonRepository, String name){
        //Verifica si existe en la db
        Optional<Pokemon> existePokemon = pokemonRepository.findByName(name);
        if(existePokemon.isPresent()){
            System.out.println("Ya existe " + name + " , en la base de datos");
            return;// evita guardar dulicados
        }
    }


}