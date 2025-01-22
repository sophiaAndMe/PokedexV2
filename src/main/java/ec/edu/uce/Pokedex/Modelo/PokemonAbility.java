package ec.edu.uce.Pokedex.Modelo;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pokemon_ability" , uniqueConstraints =
        {@UniqueConstraint(columnNames = {"name"})})
public class PokemonAbility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Boolean isHidden;
    //no es necesario solo es pokedex
    @Transient
    private Integer slot;
    @ManyToMany(mappedBy = "abilities")
    private List<Pokemon> pokemons = new ArrayList<>();
    @Column(name = "name")
    private String name;

    public Boolean getIsHidden() {
        return isHidden;
    }
    public void setIsHidden(Boolean isHidden) {
        this.isHidden = isHidden;
    }
    public Integer getSlot() {
        return slot;
    }
    public void setSlot(Integer slot) {
        this.slot = slot;
    }

    public List<Pokemon> getPokemons() {
        return pokemons;
    }

    public void setPokemons(List<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }

    public String getName() {
        return name;
    }

    public void setName(String abilities) {
        this.name = abilities;
    }
}
