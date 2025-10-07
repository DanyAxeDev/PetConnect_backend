package br.com.petConnect.backend.Repository;

import br.com.petConnect.backend.Model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByNome(String nome);
}
