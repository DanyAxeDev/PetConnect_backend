package br.com.petConnect.backend.Service;

import br.com.petConnect.backend.DTO.UserPetDto;
import br.com.petConnect.backend.Form.UserPetForm;
import br.com.petConnect.backend.Model.Pet;
import br.com.petConnect.backend.Model.User;
import br.com.petConnect.backend.Model.UserPet;
import br.com.petConnect.backend.Repository.PetRepository;
import br.com.petConnect.backend.Repository.UserPetRepository;
import br.com.petConnect.backend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserPetService {

    @Autowired
    private UserPetRepository userPetRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PetRepository petRepository;

    @Transactional
    public UserPetDto addFavorite(UserPetForm form) {
        User user = userRepository.findById(form.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        Pet pet = petRepository.findById(form.getPetId())
                .orElseThrow(() -> new RuntimeException("Pet não encontrado"));
        
        if (userPetRepository.existsByUserAndPet(user, pet)) {
            throw new RuntimeException("Pet já está nos favoritos do usuário");
        }
        
        UserPet userPet = new UserPet();
        userPet.setUser(user);
        userPet.setPet(pet);
        userPet = userPetRepository.save(userPet);
        
        return UserPetDto.fromEntity(userPet);
    }

    public List<UserPetDto> findAll() {
        return userPetRepository.findAll().stream()
                .map(UserPetDto::fromEntity)
                .collect(Collectors.toList());
    }

    public List<UserPetDto> findByUserId(Long userId) {
        return userPetRepository.findPetsByUserId(userId).stream()
                .map(pet -> {
                    UserPetDto dto = new UserPetDto();
                    dto.setPetId(pet.getId());
                    dto.setPetName(pet.getName());
                    dto.setUserId(userId);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<UserPetDto> findByPetId(Long petId) {
        return userPetRepository.findUsersByPetId(petId).stream()
                .map(user -> {
                    UserPetDto dto = new UserPetDto();
                    dto.setUserId(user.getId());
                    dto.setUserName(user.getFirstName() + " " + user.getLastName());
                    dto.setPetId(petId);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public Optional<UserPetDto> findByUserAndPet(Long userId, Long petId) {
        User user = userRepository.findById(userId).orElse(null);
        Pet pet = petRepository.findById(petId).orElse(null);
        
        if (user == null || pet == null) {
            return Optional.empty();
        }
        
        return userPetRepository.findByUserAndPet(user, pet)
                .map(UserPetDto::fromEntity);
    }

    public boolean isFavorite(Long userId, Long petId) {
        User user = userRepository.findById(userId).orElse(null);
        Pet pet = petRepository.findById(petId).orElse(null);
        
        if (user == null || pet == null) {
            return false;
        }
        
        return userPetRepository.existsByUserAndPet(user, pet);
    }

    public long countByUserId(Long userId) {
        return userPetRepository.countByUserId(userId);
    }

    public long countByPetId(Long petId) {
        return userPetRepository.countByPetId(petId);
    }

    @Transactional
    public boolean removeFavorite(Long userId, Long petId) {
        User user = userRepository.findById(userId).orElse(null);
        Pet pet = petRepository.findById(petId).orElse(null);
        
        if (user == null || pet == null) {
            return false;
        }
        
        Optional<UserPet> userPet = userPetRepository.findByUserAndPet(user, pet);
        if (userPet.isPresent()) {
            userPetRepository.deleteByUserAndPet(user, pet);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean delete(Long userId, Long petId) {
        User user = userRepository.findById(userId).orElse(null);
        Pet pet = petRepository.findById(petId).orElse(null);
        
        if (user == null || pet == null) {
            return false;
        }
        
        return removeFavorite(userId, petId);
    }
}
