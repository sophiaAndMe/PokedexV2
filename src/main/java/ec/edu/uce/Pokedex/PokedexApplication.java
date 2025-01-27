package ec.edu.uce.Pokedex;

import ec.edu.uce.Pokedex.Service.Repositorios.PokemonRepository;
import ec.edu.uce.Pokedex.Service.PokemonService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class PokedexApplication implements CommandLineRunner {



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

	}
	}

