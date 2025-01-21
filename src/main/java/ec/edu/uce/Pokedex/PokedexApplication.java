package ec.edu.uce.Pokedex;

import ec.edu.uce.Pokedex.Modelo.Pokemon;
import ec.edu.uce.Pokedex.Service.PokemonRepository;
import ec.edu.uce.Pokedex.Service.PokemonService;
import ec.edu.uce.Pokedex.Vista.PokedexSwingApp;
import ec.edu.uce.Pokedex.Vista.Ventana;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataIntegrityViolationException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

@SpringBootApplication
public class PokedexApplication implements CommandLineRunner {

	private final PokemonService pokemonService;
	private final PokemonRepository pokemonRepository;

	public PokedexApplication(PokemonService pokemonService, PokemonRepository pokemonRepository) {
		this.pokemonService = pokemonService;
		this.pokemonRepository = pokemonRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(PokedexApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {

			pokemonRepository.deleteAll();

//		try{
//			for(int i=7; i<=1000; i++){
//				pokemonService.fetchAndSavePokemon(i);
//			}
//			System.out.println("Se ha evitado los duplicados");
//		}catch (DataIntegrityViolationException e){
//			e.printStackTrace();
//			System.out.println("EXISTE DUPLICIDAD DE POKEMON");
//		}



		}
	}

