package ec.edu.uce.Pokedex.Service.complements;

import ec.edu.uce.Pokedex.Service.PokemonService;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ThreadDataBase {

    private final PokemonService pokemonService;

    public ThreadDataBase(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    /*
     * PROBLEMAS DE HILOS
     * DEBERIA CARGAR LAS IMAGENES SEGUN LOS HILOS
     */
    public void firstPokemon(){

        Thread thread = new Thread(() -> {
            System.out.println("Ejecutando los primero 166 pokemons...");
            try {
                Thread.sleep(1000);
                for(int i=1;i<=300 ;i++){
                    pokemonService.fetchAndSavePokemon(i);
                }
                System.out.println("Tarea Completada");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("tarea 1 completado!");
        });
        thread.start();
    }

    public void secondPokemon(){

        Thread thread2 = new Thread(()-> {

            System.out.println("Ejecutando los 167-332 pokemons...");
            try {

                Thread.sleep(5000);
                for(int i=301;i<=601 ;i++){
                    pokemonService.fetchAndSavePokemon(i);
                }
                System.out.println("tarea 2 completada!");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
        thread2.start();
    }

    public void thirdPokemon(){
        Thread thread3 = new Thread(()-> {

            System.out.println("Ejecutando los 333-449 pokemons...");
            try {
                Thread.sleep(0);
                for(int i=602;i<=903 ;i++){
                    pokemonService.fetchAndSavePokemon(i);
                }
                System.out.println("tarea 3 completada!");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        thread3.start();
    }
}
