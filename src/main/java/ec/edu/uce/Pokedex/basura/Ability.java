package ec.edu.uce.Pokedex.basura;

import javax.lang.model.element.Name;
import java.util.List;

public class Ability {

    private Integer id;
    private String name;
    private Boolean isMainSeries;
   // private NamedApiResource<Generation> generation;
    private List<Name> names;
    //private List<VerboseEffect> effectEntries;
    //private List<AbilityEffectChange> effectChanges;
    //private List<AbilityFlavorText> flavorTextEntries;
    //private List<AbilityPokemon> pokemon;

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
    public Boolean getIsMainSeries() {
        return isMainSeries;
    }
    public void setIsMainSeries(Boolean isMainSeries) {
        this.isMainSeries = isMainSeries;
    }
    public List<Name> getNames() {
        return names;
    }
    public void setNames(List<Name> names) {
        this.names = names;
    }

}
