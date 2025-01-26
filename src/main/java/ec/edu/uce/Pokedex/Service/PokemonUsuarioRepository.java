package ec.edu.uce.Pokedex.Service;

import ec.edu.uce.Pokedex.Modelo.PokemonUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PokemonUsuarioRepository extends JpaRepository<PokemonUsuario,Integer> {

    // Ejemplo: Encontrar por usuario
    List<PokemonUsuario> findById(Long id);
}