package ec.edu.uce.Pokedex.Modelo;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Component
@Table(name = "pokemon" , uniqueConstraints =
        {@UniqueConstraint(columnNames = {"name"})})
public class Pokemon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;
    private Integer baseExperience;
    private Integer height;
    private Boolean is_Default;
    @Column(name = "order_pokemon")
    private Integer order ;
    private Integer weight;
    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(
            name = "pokemon_ability_seq", // Nombre único para la tabla intermedia
            joinColumns = @JoinColumn(name = "pokemon_id"), // Columna que referencia a Pokemon
            inverseJoinColumns = @JoinColumn(name = "ability_id") // Columna que referencia a Ability
    )
    List<PokemonAbility> abilities = new ArrayList<>();
    //private List<NamedApiResource<PokemonForm>> forms;
   // private List<VersionGameIndex> gameIndices;
   // private List<PokemonHeldItem> heldItems;
    @ManyToMany()
    @JoinTable(
            name = "pokemon_location_seq",
            joinColumns = @JoinColumn(name = "pokemon_id"),
            inverseJoinColumns = @JoinColumn(name = "location_id")
    )
    private List<PokemonLocation> location_area_encounters = new ArrayList<>();

    @OneToMany(mappedBy = "pokemon", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PokemonImagen> sprites = new ArrayList<>();

   // private List<PokemonMove> moves;
   // private PokemonSprites sprites;
    //private NamedApiResource<PokemonSpecies> species;
    //private List<PokemonStat> stats;
   // private List<PokemonType> types;
    //private List<PokemonTypePast> pastTypes;

    // getter and setters


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBaseExperience() {
        return baseExperience;
    }

    public void setBaseExperience(Integer baseExperience) {
        this.baseExperience = baseExperience;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Boolean getDefault() {
        return is_Default;
    }

    public void setDefault(Boolean aDefault) {
        is_Default = aDefault;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Boolean getIs_Default() {
        return is_Default;
    }

    public void setIs_Default(Boolean is_Default) {
        this.is_Default = is_Default;
    }

    public List<PokemonLocation> getLocation_area_encounters() {
        return location_area_encounters;
    }

    public void setLocation_area_encounters(List<PokemonLocation> location_area_encounters) {
        this.location_area_encounters = location_area_encounters;
    }

    public List<PokemonAbility> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<PokemonAbility> abilities) {
        this.abilities = abilities;
    }

    public List<PokemonImagen> getSprites() {
        return sprites;
    }

    public void setSprites(List<PokemonImagen> sprites) {
        this.sprites = sprites;
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", baseExperience=" + baseExperience +
                ", height=" + height +
                ", is_Default=" + is_Default +
                ", order=" + order +
                ", weight=" + weight +
                ", abilities=" + abilities +
                ", location_area_encounters=" + location_area_encounters +
                ", sprites=" + sprites +
                '}';
    }
}
