package ec.edu.uce.Pokedex.Modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "pokemon_imagen")
public class PokemonImagen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // se van a guardar las url
    private String back_default;
    private String front_default;
    @ManyToOne
    @JoinColumn(name = "pokemon_id")
    private Pokemon pokemon;


    public String getBack_default() {
        return back_default;
    }

    public void setBack_default(String back_default) {
        this.back_default = back_default;
    }

    public String getFront_default() {
        return front_default;
    }

    public void setFront_default(String front_default) {
        this.front_default = front_default;
    }

    public Pokemon getPokemon() {
        return pokemon;
    }

    public void setPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
    }
}
