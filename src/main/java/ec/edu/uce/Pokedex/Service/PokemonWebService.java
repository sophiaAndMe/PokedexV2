package ec.edu.uce.Pokedex.Service;

import ec.edu.uce.Pokedex.Modelo.*;
import ec.edu.uce.Pokedex.Service.Repositorios.PokemonRepository;
import ec.edu.uce.Pokedex.Service.Repositorios.Reactive.PokemonAbilitiyRepositoryREACT;
import ec.edu.uce.Pokedex.Service.Repositorios.Reactive.PokemonAreaRepositoryREACT;
import ec.edu.uce.Pokedex.Service.Repositorios.Reactive.PokemonRepositoryReative;
import ec.edu.uce.Pokedex.Service.Repositorios.pokemonAbilityRepository;
import ec.edu.uce.Pokedex.Service.Repositorios.pokemonAreaRepository;
import ec.edu.uce.Pokedex.Service.Repositorios.pokemonTypeRepository;
import ec.edu.uce.Pokedex.Service.complements.ManagerDuplicate;
import ec.edu.uce.Pokedex.Service.complements.SaveImagen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class PokemonWebService {

    @Autowired
    PokemonAreaRepositoryREACT pokemonAreaRepositoryREACT;

    private final WebClient webClient;

    @Autowired
    Pokemon pokemon;

    @Autowired
    ManagerDuplicate managerDuplicate;

    @Autowired
    PokemonAbilitiyRepositoryREACT pokemonAbilityRepository;

    @Autowired
    pokemonTypeRepository pokemonTypeRepository;

    @Autowired
    SaveImagen saveImagen;

    @Autowired
    PokemonRepositoryReative pokemonRepositoryReative;

    public PokemonWebService(WebClient.Builder webClientBuilder) {
        // Configura el WebClient base con la URL de la PokeAPI
        this.webClient = webClientBuilder.baseUrl("https://pokeapi.co/api/v2").build();

    }

    // O bien, para cargar una lista
    public Mono<Pokemon> fetchAndSabePokemonReactive(int pokemonId) {
        // Entrar al url principal de pokemon
        Mono<Map> responseMono = webClient.get()
                .uri("/pokemon/{id}", pokemonId)
                .retrieve()
                .bodyToMono(Map.class);

        // obtener la informacion de las areas

        Mono<List<Map<String, Object>>> responseAreaMono = webClient.get()
                .uri("/pokemon/{id}/encounters", pokemonId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Map<String, Object>>>() {
                });


        return responseMono.flatMap(response -> {

            // crear un pokemon y mapear los datos
            pokemon.setName((String) response.get("name"));
            pokemon.setHeight((int) response.get("height"));
            pokemon.setWeight((int) response.get("weight"));
            pokemon.setIs_Default(true);
            pokemon.setOrder((int) response.get("order"));
            pokemon.setBaseExperience((int) response.get("base_experience"));

            //manejo de abilities
            List<Map<String, Object>> abilities = (List<Map<String, Object>>) response.get("abilities");
            List<PokemonAbility> pokemonAbilities = new ArrayList<>();
            if (abilities != null) {
                for (Map<String, Object> abilityInfo : abilities) {
                    Map<String, Object> ability = (Map<String, Object>) abilityInfo.get("ability");
                    String abilityName = (String) ability.get("name");
                    // Se asume que este método agrega o actualiza la habilidad sin duplicados
                    managerDuplicate.ManejoHabilidad(pokemonAbilityRepository, pokemon, abilityName, pokemonAbilities);
                }
            }
            pokemon.setAbilities(pokemonAbilities);

            //Manejo de tipos
            // Manejar tipos (types)
            List<Map<String, Object>> types = (List<Map<String, Object>>) response.get("types");
            List<PokemonType> pokemonTypes = new ArrayList<>();
            if (types != null) {
                for (Map<String, Object> typeMap : types) {
                    Map<String, Object> typeInfo = (Map<String, Object>) typeMap.get("type");
                    String typeName = (String) typeInfo.get("name");
                    managerDuplicate.ManejoTipos(pokemonTypeRepository, pokemon, typeName, pokemonTypes);
                }
            }
            pokemon.setTypes(pokemonTypes);

            // manejo de sprites = .png

            Map<String, Object> pokemonPng = (Map<String, Object>) response.get("sprites");
            if (pokemonPng != null) {
                String backImage = (String) pokemonPng.get("back_default");
                String frontImage = (String) pokemonPng.get("front_default");
                PokemonImagen pokemonImagen = new PokemonImagen();
                pokemonImagen.setBack_default(backImage);
                pokemonImagen.setFront_default(frontImage);
                pokemonImagen.setPokemon(pokemon);
                pokemon.getSprites().add(pokemonImagen);

                // Directorio de imágenes (esto sigue siendo bloqueante; idealmente lo envolverías en Mono.fromCallable)
                String directory = "src/main/resources/static/images/";
                try {
                    if (backImage != null) {
                        saveImagen.saveImageFromUrl(backImage, directory + pokemon.getName() + "_back.png");
                    }
                    if (frontImage != null) {
                        saveImagen.saveImageFromUrl(frontImage, directory + pokemon.getName() + "_front.png");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            // Procesar las áreas (encounters) usando responseAreaMono
            return responseAreaMono.flatMap(responseArea -> {
                List<PokemonLocation> pokemonLocations = new ArrayList<>();
                if (responseArea != null) {
                    for (Map<String, Object> locationArea : responseArea) {
                        Map<String, Object> area = (Map<String, Object>) locationArea.get("location_area");
                        if (area != null) {
                            String areaName = (String) area.get("name");
                            managerDuplicate.ManejoAreas(pokemonAreaRepositoryREACT, pokemon, areaName, pokemonLocations);
                        }
                    }
                }
                pokemon.setLocation_area_encounters(pokemonLocations);

                // Evitar duplicados: si el Pokémon ya existe, no lo inserta de nuevo.
                return pokemonRepositoryReative.findByName(pokemon.getName())
                        .switchIfEmpty(pokemonRepositoryReative.save(pokemon));
            });
        });
    }

}
