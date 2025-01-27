package ec.edu.uce.Pokedex.Service.complements;


import ec.edu.uce.Pokedex.Modelo.Pokemon;
import ec.edu.uce.Pokedex.Modelo.PokemonAbility;
import ec.edu.uce.Pokedex.Modelo.PokemonLocation;
import ec.edu.uce.Pokedex.Service.Repositorios.PokemonRepository;
import ec.edu.uce.Pokedex.Service.Repositorios.pokemonAbilityRepository;
import ec.edu.uce.Pokedex.Service.Repositorios.pokemonAreaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
public class ManagerDuplicate {

    /// Manejo de Habilidades
    public void ManejoHabilidad(pokemonAbilityRepository pokemonAbilityRepository,
                                Pokemon pokemon, String abilityName, List<PokemonAbility> abilities) {


        // Verifica si la habilidad ya existe en la base de datos
        PokemonAbility ability = pokemonAbilityRepository.findByName(abilityName)
                .orElseGet(() -> {
                    // Si no existe, crea una nueva habilidad
                    PokemonAbility newAbility = new PokemonAbility();
                    newAbility.setName(abilityName);
                    return pokemonAbilityRepository.save(newAbility);
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

    public void manejoPokemonName(PokemonRepository pokemonRepository, String name){
        //Verifica si existe en la db
        Optional<Pokemon> existePokemon = pokemonRepository.findByName(name);
        if(existePokemon.isPresent()){
            System.out.println("Ya existe " + name + " , en la base de datos");
            return;// evita guardar dulicados
        }
    }


}
