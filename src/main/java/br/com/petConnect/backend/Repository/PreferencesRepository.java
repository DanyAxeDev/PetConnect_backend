package br.com.petConnect.backend.Repository;

import br.com.petConnect.backend.Model.Preferences;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PreferencesRepository extends JpaRepository<Preferences, Long> {
    
    Optional<Preferences> findByUserId(Long userId);
    
    boolean existsByUserId(Long userId);
}
