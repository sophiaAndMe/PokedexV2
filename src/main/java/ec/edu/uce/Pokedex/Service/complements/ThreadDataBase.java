package ec.edu.uce.Pokedex.Service.complements;

import ec.edu.uce.Pokedex.Service.PokemonService;
import ec.edu.uce.Pokedex.Service.Repositorios.PokemonRepository;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class ThreadDataBase {
    private final PokemonService pokemonService;
    private final ExecutorService executorService = Executors.newFixedThreadPool(4); // Pool de 2 hilos

    private final PokemonRepository pokemonRepository;

    public ThreadDataBase(PokemonService pokemonService, PokemonRepository pokemonRepository) {
        this.pokemonService = pokemonService;
        this.pokemonRepository = pokemonRepository;
    }

    public void cargarPokemons(int start, int end) {

        executorService.submit(() -> {
            System.out.printf("Ejecutando Pokémon del %d al %d...%n", start, end);
            for (int i = start; i <= end; i++) {
                try {
                    pokemonService.fetchAndSavePokemon(i);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.printf("Tarea completada: Pokémon del %d al %d.%n", start, end);
        });
    }

    public  void iniciarCargaDePokemons() {

        if(pokemonRepository.count() == 947){
            System.out.println("Ya se han cargado los pokemons");
            shutdownExecutor();
        }else {
            cargarPokemons(1, 300);
            cargarPokemons(301, 602);
            cargarPokemons(602, 999);
        }
    }


    public void shutdownExecutor() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(6, TimeUnit.MINUTES)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }
}

