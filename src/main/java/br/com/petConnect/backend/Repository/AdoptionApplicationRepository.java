package br.com.petConnect.backend.Repository;

import br.com.petConnect.backend.Model.AdoptionApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdoptionApplicationRepository extends JpaRepository<AdoptionApplication, Long> {
    
    List<AdoptionApplication> findByUserId(Long userId);
    
    List<AdoptionApplication> findByPetId(Long petId);
    
    List<AdoptionApplication> findByStatus(String status);
    
    @Query("SELECT aa FROM AdoptionApplication aa JOIN aa.pet p WHERE p.owner.id = :ownerId")
    List<AdoptionApplication> findByPetOwnerId(@Param("ownerId") Long ownerId);
    
    @Query("SELECT aa FROM AdoptionApplication aa WHERE aa.user.id = :userId AND aa.pet.id = :petId")
    Optional<AdoptionApplication> findByUserIdAndPetId(@Param("userId") Long userId, @Param("petId") Long petId);
    
    Page<AdoptionApplication> findByUserId(Long userId, Pageable pageable);
    
    Page<AdoptionApplication> findByStatus(String status, Pageable pageable);
}
