package br.com.petConnect.backend.Service;

import br.com.petConnect.backend.DTO.PetDto;
import br.com.petConnect.backend.Form.PetForm;
import br.com.petConnect.backend.Form.PetUpdateForm;
import br.com.petConnect.backend.Model.Pet;
import br.com.petConnect.backend.Model.User;
import br.com.petConnect.backend.Repository.PetRepository;
import br.com.petConnect.backend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public PetDto create(PetForm form) {
        User owner = userRepository.findById(form.getOwnerId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        Pet pet = form.toEntity();
        pet.setOwner(owner);
        pet = petRepository.save(pet);
        
        return PetDto.fromEntity(pet);
    }

    public List<PetDto> findAll() {
        return petRepository.findAll().stream()
                .map(PetDto::fromEntity)
                .collect(Collectors.toList());
    }

    public Page<PetDto> findAll(Pageable pageable) {
        return petRepository.findAll(pageable)
                .map(PetDto::fromEntity);
    }

    public List<PetDto> findAvailablePets() {
        return petRepository.findByAvailableTrue().stream()
                .map(PetDto::fromEntity)
                .collect(Collectors.toList());
    }

    public List<PetDto> findAvailablePetsExcludingUser(Long userId) {
        return petRepository.findByAvailableTrueExcludingUser(userId).stream()
                .map(PetDto::fromEntity)
                .collect(Collectors.toList());
    }

    public Page<PetDto> findAvailablePets(Pageable pageable) {
        return petRepository.findByAvailableTrue(pageable)
                .map(PetDto::fromEntity);
    }

    public Page<PetDto> findAvailablePetsWithFilters(String species, String gender, String size, Pageable pageable) {
        return petRepository.findAvailablePetsWithFilters(species, gender, size, pageable)
                .map(PetDto::fromEntity);
    }

    public List<PetDto> findByLocation(String city, String state) {
        return petRepository.findByLocation(city, state).stream()
                .map(PetDto::fromEntity)
                .collect(Collectors.toList());
    }

    public List<PetDto> findByName(String name) {
        return petRepository.findByNameContainingIgnoreCase(name).stream()
                .map(PetDto::fromEntity)
                .collect(Collectors.toList());
    }

    public List<PetDto> findByOwnerId(Long ownerId) {
        return petRepository.findByOwnerId(ownerId).stream()
                .map(PetDto::fromEntity)
                .collect(Collectors.toList());
    }

    public List<PetDto> findFavoritesByUserId(Long userId) {
        return petRepository.findFavoritesByUserId(userId).stream()
                .map(PetDto::fromEntity)
                .collect(Collectors.toList());
    }

    public Optional<PetDto> findById(Long id) {
        return petRepository.findById(id)
                .map(PetDto::fromEntity);
    }

    public Optional<PetDto> findAvailableById(Long id) {
        return petRepository.findByIdAndAvailableTrue(id)
                .map(PetDto::fromEntity);
    }

    @Transactional
    public Optional<PetDto> update(Long id, PetUpdateForm form) {
        return petRepository.findById(id)
                .map(pet -> {
                    form.updateEntity(pet);
                    pet = petRepository.save(pet);
                    return PetDto.fromEntity(pet);
                });
    }

    @Transactional
    public boolean delete(Long id) {
        if (petRepository.existsById(id)) {
            petRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean setAvailability(Long id, boolean available) {
        return petRepository.findById(id)
                .map(pet -> {
                    pet.setAvailable(available);
                    petRepository.save(pet);
                    return true;
                })
                .orElse(false);
    }
}
