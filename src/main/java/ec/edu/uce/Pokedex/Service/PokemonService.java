package ec.edu.uce.Pokedex.Service;

import ec.edu.uce.Pokedex.Modelo.*;
import ec.edu.uce.Pokedex.Service.Repositorios.*;
import ec.edu.uce.Pokedex.Service.complements.ManagerDuplicate;
import ec.edu.uce.Pokedex.Service.complements.SaveImagen;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.domain.Page;
@Service
public class PokemonService {


    /// es final para mantener la integridad de los POKEMOS
    private PokemonRepository pokemonRepository;
    private final ec.edu.uce.Pokedex.Service.Repositorios.pokemonAbilityRepository pokemonAbilityRepository;
    private final ec.edu.uce.Pokedex.Service.Repositorios.pokemonAreaRepository pokemonAreaRepository;
    private final pokemonTypeRepository pokemonTypeRepository;
    private  Pokemon pokemon;
    private ManagerDuplicate managerDuplicate;
    private SaveImagen saveImagen;


    public PokemonService(PokemonRepository pokemonRepository,
                          pokemonAbilityRepository pokemonAbilityRepository,
                          pokemonAreaRepository pokemonAreaRepository,
                          pokemonTypeRepository pokemonTypeRepository,
                          ManagerDuplicate managerDuplicate,
                          Pokemon pokemon,
                          SaveImagen saveImagen) {
        this.pokemonRepository = pokemonRepository;
        this.pokemonAbilityRepository = pokemonAbilityRepository;
        this.pokemonAreaRepository = pokemonAreaRepository;
        this.pokemonTypeRepository = pokemonTypeRepository;
        this.managerDuplicate = managerDuplicate;
        this.pokemon = pokemon;
        this.saveImagen = saveImagen;
    }


    // metodo para cargar los datos desde una URL
    public <T> void fetchAndSavePokemon(T pokemonId) throws IOException {

        // No puede ser nulo
        RestTemplate restTemplate = new RestTemplate();

        //-------------------------------------
        String apiUrl = "https://pokeapi.co/api/v2/pokemon/" + pokemonId;
        String apiEncounters = "https://pokeapi.co/api/v2/pokemon/" + pokemonId + "/encounters";

        if (apiEncounters == null) {
            throw new IllegalStateException("No se pudo obtener información del Pokémon desde la API.");
        }

        //------------------------------------
        // Guarda en una lista todo de la URL
        Map<String, Object> response = restTemplate.getForObject(apiUrl, Map.class);
        List<Map<String, Object>> responseArea = restTemplate.getForObject(apiEncounters, List.class);

        Pokemon pokemon = new Pokemon();
        assert response != null;
        pokemon.setId((int) response.get("id"));
        pokemon.setName((String) response.get("name"));
        managerDuplicate.manejoPokemonName(pokemonRepository,(String)response.get("name"));
        pokemon.setHeight((int) response.get("height"));
        pokemon.setWeight((int) response.get("weight"));
        pokemon.setIs_Default(false);
        /// creo que me va a causar problemas con la palabra order
        pokemon.setOrder((int) response.get("order"));
        pokemon.setBaseExperience((int) response.get("base_experience"));
        // Aqui las abilites lo guarda en una Lista
        // recuerda que MAP funciona con clave valor
        List<Map<String, Object>> abilities = (List<Map<String, Object>>) response.get("abilities");

        //------------------------------------
        /*
         * TYPES POKEMON
         */
        List<Map<String, Object>> types = (List<Map<String, Object>>) response.get("types");
        List<PokemonType> pokemonTypes = new ArrayList<>();

        for(Map<String, Object> type : types) {
            Map<String, Object> pokemonTypeMap = (Map<String, Object>) type.get("type");
            String typeName = (String) pokemonTypeMap.get("name");
            managerDuplicate.ManejoTipos(pokemonTypeRepository, pokemon,
                                         typeName, pokemonTypes);

        }
        pokemon.setTypes(pokemonTypes);
        //-------------------------------------

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
                saveImagen.saveImageFromUrl(pokemonNamePng, directory +   pokemon.getName() + "_back.png");
            }
            if (pokemonNamePng2 != null) {
                saveImagen.saveImageFromUrl(pokemonNamePng2, directory + pokemon.getName() + "_front.png");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        pokemonRepository.save(pokemon);
    }


    // Obtener 10 Pokémon ordenados por ID
    public List<Pokemon> getPokemonsPaginated(int page) {
        int pageSize = 10;
        int offset = page * pageSize;
        return pokemonRepository.findPokemonsPaginatedOrderedById(offset, pageSize);
    }

    public Optional<Pokemon> getPokemonById(Long id) {
        return pokemonRepository.findById(id);
    }

    public Page<Pokemon> searchPokemons(String query, Pageable pageable) {
        return pokemonRepository.findByNameContainingIgnoreCase(query, pageable);
    }

    public Page<Pokemon> getAllPokemons(Pageable pageable) {
        return pokemonRepository.findAll(pageable);
    }

    // obtener las habilidides de ese pokemon
//    public List<PokemonAbility> findAbilities(int id){
//        pokemon = pokemonRepository.findById(id);
//        Hibernate.initialize(pokemon.getAbilities());
//        return pokemon.getAbilities();
//    }

    // Obtener las areas
//    public List<PokemonLocation> getPokemonLocations(int id){
//
//            pokemon = pokemonRepository.findById(id);
//        Hibernate.initialize(pokemon.getLocation_area_encounters());
//        return pokemon.getLocation_area_encounters();
//
//    }




}
