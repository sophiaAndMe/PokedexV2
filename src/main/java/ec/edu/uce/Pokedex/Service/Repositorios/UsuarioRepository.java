package ec.edu.uce.Pokedex.Service.Repositorios;

import ec.edu.uce.Pokedex.Modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("SELECT u FROM Usuario u LEFT JOIN FETCH u.pokemonUsuarios pu LEFT JOIN FETCH pu.pokemon WHERE u.name = :name")
    Optional<Usuario> findByNameWithPokemons(@Param("name") String name);
}

