package br.com.petConnect.backend.Repository;

import br.com.petConnect.backend.Model.Pet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    
    List<Pet> findByOwnerId(Long ownerId);
    
    List<Pet> findByAvailableTrue();
    
    Page<Pet> findByAvailableTrue(Pageable pageable);
    
    @Query("SELECT p FROM Pet p WHERE p.available = true AND (:userId IS NULL OR p.owner.id != :userId)")
    List<Pet> findByAvailableTrueExcludingUser(@Param("userId") Long userId);
    
    @Query("SELECT p FROM Pet p WHERE p.available = true AND " +
           "(:species IS NULL OR p.species = :species) AND " +
           "(:gender IS NULL OR p.gender = :gender) AND " +
           "(:size IS NULL OR p.size = :size)")
    Page<Pet> findAvailablePetsWithFilters(@Param("species") String species,
                                          @Param("gender") String gender,
                                          @Param("size") String size,
                                          Pageable pageable);
    
    @Query("SELECT p FROM Pet p JOIN p.owner u WHERE p.available = true AND " +
           "(:city IS NULL OR u.address.city = :city) AND " +
           "(:state IS NULL OR u.address.state = :state)")
    List<Pet> findByLocation(@Param("city") String city, @Param("state") String state);
    
    @Query("SELECT p FROM Pet p WHERE p.available = true AND p.name ILIKE %:name%")
    List<Pet> findByNameContainingIgnoreCase(@Param("name") String name);
    
    @Query("SELECT up.pet FROM UserPet up WHERE up.user.id = :userId")
    List<Pet> findFavoritesByUserId(@Param("userId") Long userId);
    
    Optional<Pet> findByIdAndAvailableTrue(Long id);
}
