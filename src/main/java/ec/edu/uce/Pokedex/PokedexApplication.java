package ec.edu.uce.Pokedex;

import ec.edu.uce.Pokedex.Service.complements.ThreadDataBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class PokedexApplication implements CommandLineRunner {

	@Autowired
	ThreadDataBase threadDataBase;

	public static void main(String[] args) {
		SpringApplication.run(PokedexApplication.class, args);
	}


	@Override
	public void run(String... args)  {


	}
	}

