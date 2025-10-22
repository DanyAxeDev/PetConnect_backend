package br.com.petConnect.backend.Repository;

import br.com.petConnect.backend.Model.Personality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonalityRepository extends JpaRepository<Personality, Long> {
    
    Optional<Personality> findByPetId(Long petId);
    
    boolean existsByPetId(Long petId);
}
