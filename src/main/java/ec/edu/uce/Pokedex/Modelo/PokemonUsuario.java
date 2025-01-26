package ec.edu.uce.Pokedex.Modelo;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Entity
@Component
@Table(name = "pokemon_usuario_seq") // Nombre explícito de la tabla
public class PokemonUsuario  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "pokemon_id", nullable = false)
    private Pokemon pokemon;

    @Column(name = "capture_date")
    private String captureDate;

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Pokemon getPokemon() {
        return pokemon;
    }

    public void setPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
    }

    public String getCaptureDate() {
        return captureDate;
    }

    public void setCaptureDate(String captureDate) {
        this.captureDate = captureDate;
    }





}
