package ec.edu.uce.Pokedex.Service.Repositorios;

import ec.edu.uce.Pokedex.Modelo.Pokemon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
    public interface PokemonRepository extends JpaRepository<Pokemon, Long> {

    Optional<Pokemon> findByName(String name);
    Pokemon findByNameIgnoreCase(String name);
    Optional<Pokemon> findById(long id);

    Page<Pokemon> findByNameContainingIgnoreCase(String name, Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE Pokemon p SET p.is_Default = :estado WHERE p.id = :id")
    void updatePokemonIsDefault(@Param("id") Long id, @Param("estado") boolean estado);

    // Query para paginación ordenada por ID
    @Query(value = "SELECT * FROM pokemon ORDER BY pokemon_id LIMIT :offset, :pageSize", nativeQuery = true)
    List<Pokemon> findPokemonsPaginatedOrderedById(@Param("offset") int offset, @Param("pageSize") int pageSize);
}






