package ec.edu.uce.Pokedex;

import ec.edu.uce.Pokedex.Service.PokemonRepository;
import ec.edu.uce.Pokedex.Service.PokemonService;
import ec.edu.uce.Pokedex.Service.complements.ThreadDataBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


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

		threadDataBase.firstPokemon();

	}
	}

