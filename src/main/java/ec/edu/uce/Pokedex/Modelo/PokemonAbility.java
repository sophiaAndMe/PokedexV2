package ec.edu.uce.Pokedex.Modelo;

import jakarta.persistence.*;

@Entity
//@Table(name = "pokemon_ability" , uniqueConstraints =
//        {@UniqueConstraint(columnNames = {"abilities"})})

public class PokemonAbility {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private Boolean isHidden;
    private Integer slot;
    @ManyToOne
    @JoinColumn(name = "pokemon_id")
    private Pokemon pokemon;
    private String abilities;

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

    public Pokemon getPokemon() {
        return pokemon;
    }

    public void setPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
    }

    public String getAbilities() {
        return abilities;
    }

    public void setAbilities(String abilities) {
        this.abilities = abilities;
    }
}
