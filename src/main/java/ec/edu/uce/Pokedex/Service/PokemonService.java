package ec.edu.uce.Pokedex.Service;

import ec.edu.uce.Pokedex.Modelo.PokemonLocation;
import ec.edu.uce.Pokedex.Modelo.Pokemon;
import ec.edu.uce.Pokedex.Modelo.PokemonAbility;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class PokemonService {

    private final PokemonRepository pokemonRepository;


    public PokemonService(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
    }

    // metodo para cargar los datos desde una URL

    public void fetchAndSavePokemon(String pokemonName) {

        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "https://pokeapi.co/api/v2/pokemon/" + pokemonName;
        String apiEncounters = "https://pokeapi.co/api/v2/pokemon/" + pokemonName + "/encounters";

        // Mapea los datos JSON a un objeto
        Map<String, Object> response = restTemplate.getForObject(apiUrl, Map.class);
        List<Map<String, Object>> responseArea = restTemplate.getForObject(apiEncounters, List.class);


        Pokemon pokemon = new Pokemon();
        pokemon.setName((String) response.get("name"));
        pokemon.setHeight((Integer) response.get("height"));
        pokemon.setWeight((Integer) response.get("weight"));
        pokemon.setDefault((boolean) response.get("is_default"));
        // no setea el area

        pokemon.setOrder((Integer) response.get("order_pokemon"));
        pokemon.setBaseExperience((Integer) response.get("base_experience"));

        // Mapea las habilidades
        List<Map<String, Object>> abilities = (List<Map<String, Object>>) response.get("abilities");

        for (Map<String, Object> abilityInfo : abilities) {
            Map<String, Object> ability = (Map<String, Object>) abilityInfo.get("ability");
            String abilityName = (String) ability.get("name");

            PokemonAbility pokemonAbility = new PokemonAbility();
            pokemonAbility.setAbilities(abilityName);
            pokemonAbility.setPokemon(pokemon);

            // Agrega la habilidad al Pokémon
            pokemon.getAbilities().add(pokemonAbility);

            // Itera sobre las áreas de encuentro
            for (Map<String, Object> areaInfo : responseArea) {
                Map<String, Object> area = (Map<String, Object>) areaInfo.get("location_area");
                String areaName = (String) area.get("name");

                PokemonLocation pokemonLocation = new PokemonLocation();
                pokemonLocation.setName(areaName);
                pokemonLocation.setPokemon(pokemon);

                // Agrega la ubicación a la lista de ubicaciones del Pokémon
                pokemon.getLocation_area_encounters().add(pokemonLocation);
            }
        }
        pokemonRepository.save(pokemon);






    }
}
