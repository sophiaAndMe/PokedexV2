package ec.edu.uce.Pokedex;

import ec.edu.uce.Pokedex.Service.PokemonRepository;
import ec.edu.uce.Pokedex.Service.PokemonService;
import ec.edu.uce.Pokedex.Service.complements.ThreadDataBase;
import ec.edu.uce.Pokedex.Vista.BuscarID;
import ec.edu.uce.Pokedex.Vista.PokedexSwingApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.task.ThreadPoolTaskExecutorBuilder;


@SpringBootApplication
public class PokedexApplication implements CommandLineRunner {


	/// para cargar los pokemos :)
	@Autowired
	ThreadDataBase threadDataBase;

	private final PokemonService pokemonService;
	private final PokemonRepository pokemonRepository;


	public PokedexApplication(PokemonService pokemonService,
							  PokemonRepository pokemonRepository) {
		this.pokemonService = pokemonService;
		this.pokemonRepository = pokemonRepository;

	}

	public static void main(String[] args) {
		SpringApplication.run(PokedexApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {

		//pokemonRepository.deleteAll();


		 threadDataBase.firstPokemon();




//        String pokemonName = "metapod";
//
//		Optional<Pokemon> pokemonOptional = pokemonRepository.findByName(pokemonName);
//		if(pokemonOptional.isPresent()) {
//			Pokemon pokemon = pokemonOptional.get();
//			System.out.println("Se ha encontrado: " + pokemon.getName());
//		}else{
//			System.out.println("pokemon no encontrado :( ");
//		}


	}
	}

