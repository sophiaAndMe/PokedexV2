package ec.edu.uce.Pokedex.Modelo;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuario")
@Component
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String genero;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Pokemon> pokemons = new ArrayList<>();

    // tabla intermedia
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PokemonUsuario> pokemonUsuarios = new ArrayList<>();


    public List<PokemonUsuario> getPokemonUsuarios() {
        return pokemonUsuarios;
    }

    public void setPokemonUsuarios(List<PokemonUsuario> pokemonUsuarios) {
        this.pokemonUsuarios = pokemonUsuarios;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public List<Pokemon> getPokemons() {
        return pokemons;
    }

    public void setPokemons(List<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }
}
