package ec.edu.uce.Pokedex.Service.complements;

import ec.edu.uce.Pokedex.Service.PokemonService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class ThreadDataBase {
    private final PokemonService pokemonService;
    private final ExecutorService executorService = Executors.newFixedThreadPool(4); // Pool de 2 hilos

    public ThreadDataBase(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    public void cargarPokemons(int start, int end) {
        executorService.submit(() -> {
            System.out.printf("Ejecutando Pokémon del %d al %d...%n", start, end);
            try {
                for (int i = start; i <= end; i++) {
                    pokemonService.fetchAndSavePokemon(i);
                }
                System.out.printf("Tarea completada: Pokémon del %d al %d.%n", start, end);
            } catch (IOException e) {
                System.err.printf("Error al cargar Pokémon del %d al %d: %s%n", start, end, e.getMessage());
            }
        });
        shutdownExecutor();
    }

    public void iniciarCargaDePokemons() {
        // Dividir la carga en dos rangos
        cargarPokemons(1, 333);
        cargarPokemons(334, 666);
        cargarPokemons(667, 1000);

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

