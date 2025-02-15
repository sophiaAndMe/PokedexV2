package ec.edu.uce.Pokedex.Service.complements;

import ec.edu.uce.Pokedex.Service.PokemonWebService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class ThreadDataBase {
    private final PokemonWebService pokemonWebService;
    private final ExecutorService executorService = Executors.newFixedThreadPool(4); // Pool de 2 hilos

    public ThreadDataBase(PokemonWebService pokemopokemonWebServicenService) {
        this.pokemonWebService = pokemopokemonWebServicenService;
    }

    public void cargarPokemons(int start, int end) {

        executorService.submit(() -> {
            System.out.printf("Ejecutando Pokémon del %d al %d...%n", start, end);
            for (int i = start; i <= end; i++) {
                pokemonWebService.fetchAndSabePokemonReactive(i);
            }
            System.out.printf("Tarea completada: Pokémon del %d al %d.%n", start, end);
        });
    }

    public void cargarPokemonsReactivo(int start, int end) {
        Flux.range(start, end - start + 1) // Genera un rango de IDs
                .flatMap(id ->
                        Mono.fromCallable(() -> {
                                    // Ejecuta en un scheduler elástico para no bloquear el hilo principal
                                    pokemonWebService.fetchAndSabePokemonReactive(id);
                                    return id;
                                })
                                .subscribeOn(Schedulers.boundedElastic()) // Para operaciones bloqueantes
                                .doOnSuccess(i -> System.out.printf("Pokémon %d cargado%n", i))
                                .doOnError(e -> System.err.printf("Error en Pokémon %d: %s%n", id, e.getMessage()))
                )
                .then()
                .doOnSuccess(v -> System.out.printf("Tarea completada: Pokémon del %d al %d.%n", start, end));
    }
    // usando Threads



    public void iniciarCargaDePokemons() {
        // Dividir la carga en dos rangos
        cargarPokemonsReactivo(1, 333);
        cargarPokemonsReactivo(334, 666);
        cargarPokemonsReactivo(667, 1000);
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

