package ec.edu.uce.Pokedex.Service;

import ec.edu.uce.Pokedex.Modelo.PokemonImagen;
import ec.edu.uce.Pokedex.Modelo.PokemonLocation;
import ec.edu.uce.Pokedex.Modelo.Pokemon;
import ec.edu.uce.Pokedex.Modelo.PokemonAbility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.server.ExportException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


/*
*APLICAR
*RESPONSABILIDAD
* UNICA :) (mas legible)
 */
@Service
public class PokemonService {


    /// es final para mantener la integridad de los POKEMOS
    private final PokemonRepository pokemonRepository;
    private final pokemonAbilityRepository pokemonAbilityRepository;
    private final pokemonAreaRepository pokemonAreaRepository;
    private  ManagerDuplicate managerDuplicate;
    private SaveImagen saveImagen;

    public PokemonService(PokemonRepository pokemonRepository,
                          pokemonAbilityRepository pokemonAbilityRepository,
                          pokemonAreaRepository pokemonAreaRepository,
                          ManagerDuplicate managerDuplicate,
                          SaveImagen saveImagen) {
        this.pokemonRepository = pokemonRepository;
        this.pokemonAbilityRepository = pokemonAbilityRepository;
        this.pokemonAreaRepository = pokemonAreaRepository;
        this.managerDuplicate = managerDuplicate;
        this.saveImagen = saveImagen;
    }

    // metodo para cargar los datos desde una URL
    public <T> void fetchAndSavePokemon(T pokemonName) throws IOException {

        // No puede ser nulo
        RestTemplate restTemplate = new RestTemplate();

        //-------------------------------------
        String apiUrl = "https://pokeapi.co/api/v2/pokemon/" + pokemonName;
        String apiEncounters = "https://pokeapi.co/api/v2/pokemon/" + pokemonName + "/encounters";

        if (apiEncounters == null) {
            throw new IllegalStateException("No se pudo obtener información del Pokémon desde la API.");
        }

        //------------------------------------
        // Guarda en una lista todo de la URL
        Map<String, Object> response = restTemplate.getForObject(apiUrl, Map.class);
        List<Map<String, Object>> responseArea = restTemplate.getForObject(apiEncounters, List.class);

        Pokemon pokemon = new Pokemon();
        pokemon.setName((String) response.get("name"));
        managerDuplicate.manejoPokemonName(pokemonRepository,(String)response.get("name"));
        pokemon.setHeight((int) response.get("height"));
        pokemon.setWeight((int) response.get("weight"));
        pokemon.setIs_Default((boolean) response.get("is_default"));
        /// creo que me va a causar problemas con la palabra order
        pokemon.setOrder((int) response.get("order"));
        pokemon.setBaseExperience((int) response.get("base_experience"));
        // Aqui las abilites lo guarda en una Lista
        // recuerda que MAP funciona con clave valor
        List<Map<String, Object>> abilities = (List<Map<String, Object>>) response.get("abilities");

        //------------------------------------

        List<PokemonAbility> pokemonAbilities = new ArrayList<>();
        // recorriendo la lista de abilities
        for (Map<String, Object> abilityInfo : abilities) {
            Map<String, Object> ability = (Map<String, Object>) abilityInfo.get("ability");

            String abilityName = (String) ability.get("name");
            managerDuplicate.ManejoHabilidad(pokemonAbilityRepository, pokemon,
                                            abilityName, pokemonAbilities);
        }
        pokemon.setAbilities(pokemonAbilities);

        //------------------------------------
        // location
        /*
         * CREAR UN METODO PARA MANEJO DE AREAS
         */

        //--------------------------------
        // Itera sobre las áreas de encuentro
        // donde lo guardo :)
        List<PokemonLocation> pokemonLocations = new ArrayList<>();
        if (responseArea != null) {
            for (Map<String, Object> locationArea : responseArea) {
                Map<String, Object> area = (Map<String, Object>) locationArea.get("location_area");
                if (area != null) {
                    String areaName = (String) area.get("name");
                    // Verifica si el área ya existe
                    managerDuplicate.ManejoAreas(pokemonAreaRepository,
                            pokemon, areaName, pokemonLocations);
                }
            }
        }
        pokemon.setLocation_area_encounters(pokemonLocations);

        Map<String, Object> responsePng = restTemplate.getForObject(apiUrl, Map.class);
        // Itera dentro de documento sprites
        // mapea las imagenes
        Map<String, Object> pokemonPng = (Map<String, Object>) responsePng.get("sprites");
        // obtenga la info necesitada
        PokemonImagen pokemonImagen = new PokemonImagen();

        String pokemonNamePng = (String) pokemonPng.get("back_default");
        String pokemonNamePng2 = (String) pokemonPng.get("front_default");

        pokemonImagen.setBack_default(pokemonNamePng);
        pokemonImagen.setFront_default(pokemonNamePng2);
        pokemonImagen.setPokemon(pokemon);

        pokemon.getSprites().add(pokemonImagen);

        // Directorio donde guardar las imágenes
        String directory = "src/main/resources/static/images/";

        // Descarga y guarda las imágenes
        try {
            if (pokemonNamePng != null) {
                saveImagen.saveImageFromUrl(pokemonNamePng, directory + pokemonName + "_back.png");
            }
            if (pokemonNamePng2 != null) {
                saveImagen.saveImageFromUrl(pokemonNamePng2, directory + pokemonName + "_front.png");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        pokemonRepository.save(pokemon);
    }

}
