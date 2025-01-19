package ec.edu.uce.Pokedex.Service;

import ec.edu.uce.Pokedex.Modelo.PokemonImagen;
import ec.edu.uce.Pokedex.Modelo.PokemonLocation;
import ec.edu.uce.Pokedex.Modelo.Pokemon;
import ec.edu.uce.Pokedex.Modelo.PokemonAbility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.server.ExportException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PokemonService {

    private final PokemonRepository pokemonRepository;





    public PokemonService(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
    }

    // metodo para cargar los datos desde una URL



    public <T> void fetchAndSavePokemon(T pokemonName) throws IOException {

        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = "https://pokeapi.co/api/v2/pokemon/" + pokemonName;
        String apiEncounters = "https://pokeapi.co/api/v2/pokemon/" + pokemonName + "/encounters";

        // Mapea los datos JSON a un objeto
        Map<String, Object> response = restTemplate.getForObject(apiUrl, Map.class);
        List<Map<String, Object>> responseArea = restTemplate.getForObject(apiEncounters, List.class);

        Pokemon pokemon = new Pokemon();
        pokemon.setName((String) response.get("name"));
        manejoDuplicados((String) response.get("name"));
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
        //String apiImagen = "https://pokeapi.co/api/v2/pokemon/" + pokemonName;

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
                saveImageFromUrl(pokemonNamePng, directory + pokemonName + "_back.png");
            }
            if (pokemonNamePng2 != null) {
                saveImageFromUrl(pokemonNamePng2, directory + pokemonName + "_front.png");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        pokemonRepository.save(pokemon);

    }

    private void saveImageFromUrl (String imageUrl, String destinationPath) throws IOException {
        URL url = new URL(imageUrl);
        try (InputStream in = url.openStream()) {
            Path outputPath = Paths.get(destinationPath);
            Files.createDirectories(outputPath.getParent()); // Crea los directorios si no existen
            Files.copy(in, outputPath);
        }catch (ExportException e){
            e.printStackTrace();
        }
    }

    private void manejoDuplicados(String name){
        //Verifica si existe en la db
        Optional<Pokemon> existePokemon = pokemonRepository.findByName(name);

        if(existePokemon.isPresent()){
            System.out.println("Ya existe " + name + " , en la base de datos");
             return;// evita guardar dulicados
        }
    }
}
