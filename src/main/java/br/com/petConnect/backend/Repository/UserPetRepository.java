package br.com.petConnect.backend.Repository;

import br.com.petConnect.backend.Model.UserPet;
import br.com.petConnect.backend.Model.User;
import br.com.petConnect.backend.Model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserPetRepository extends JpaRepository<UserPet, Long> {
    
    Optional<UserPet> findByUserAndPet(User user, Pet pet);
    
    boolean existsByUserAndPet(User user, Pet pet);
    
    @Query("SELECT up.pet FROM UserPet up WHERE up.user.id = :userId")
    List<Pet> findPetsByUserId(@Param("userId") Long userId);
    
    @Query("SELECT up.user FROM UserPet up WHERE up.pet.id = :petId")
    List<User> findUsersByPetId(@Param("petId") Long petId);
    
    void deleteByUserAndPet(User user, Pet pet);
    
    @Query("SELECT COUNT(up) FROM UserPet up WHERE up.pet.id = :petId")
    long countByPetId(@Param("petId") Long petId);
    
    @Query("SELECT COUNT(up) FROM UserPet up WHERE up.user.id = :userId")
    long countByUserId(@Param("userId") Long userId);
}
