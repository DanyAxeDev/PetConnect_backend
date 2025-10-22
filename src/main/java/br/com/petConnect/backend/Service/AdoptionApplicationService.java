package br.com.petConnect.backend.Service;

import br.com.petConnect.backend.DTO.AdoptionApplicationDto;
import br.com.petConnect.backend.Form.AdoptionApplicationForm;
import br.com.petConnect.backend.Form.AdoptionApplicationUpdateForm;
import br.com.petConnect.backend.Model.AdoptionApplication;
import br.com.petConnect.backend.Model.Pet;
import br.com.petConnect.backend.Model.User;
import br.com.petConnect.backend.Repository.AdoptionApplicationRepository;
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
public class AdoptionApplicationService {

    @Autowired
    private AdoptionApplicationRepository adoptionApplicationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PetRepository petRepository;

    @Transactional
    public AdoptionApplicationDto create(AdoptionApplicationForm form) {
        User user = userRepository.findById(form.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        Pet pet = petRepository.findById(form.getPetId())
                .orElseThrow(() -> new RuntimeException("Pet não encontrado"));
        
        if (!pet.getAvailable()) {
            throw new RuntimeException("Pet não está disponível para adoção");
        }
        
        // Verificar se já existe uma aplicação do mesmo usuário para o mesmo pet
        Optional<AdoptionApplication> existingApplication = 
                adoptionApplicationRepository.findByUserIdAndPetId(form.getUserId(), form.getPetId());
        
        if (existingApplication.isPresent()) {
            throw new RuntimeException("Usuário já possui uma aplicação pendente para este pet");
        }
        
        AdoptionApplication application = form.toEntity();
        application.setUser(user);
        application.setPet(pet);
        application = adoptionApplicationRepository.save(application);
        
        return AdoptionApplicationDto.fromEntity(application);
    }

    public List<AdoptionApplicationDto> findAll() {
        return adoptionApplicationRepository.findAll().stream()
                .map(AdoptionApplicationDto::fromEntity)
                .collect(Collectors.toList());
    }

    public Page<AdoptionApplicationDto> findAll(Pageable pageable) {
        return adoptionApplicationRepository.findAll(pageable)
                .map(AdoptionApplicationDto::fromEntity);
    }

    public List<AdoptionApplicationDto> findByUserId(Long userId) {
        return adoptionApplicationRepository.findByUserId(userId).stream()
                .map(AdoptionApplicationDto::fromEntity)
                .collect(Collectors.toList());
    }

    public Page<AdoptionApplicationDto> findByUserId(Long userId, Pageable pageable) {
        return adoptionApplicationRepository.findByUserId(userId, pageable)
                .map(AdoptionApplicationDto::fromEntity);
    }

    public List<AdoptionApplicationDto> findByPetId(Long petId) {
        return adoptionApplicationRepository.findByPetId(petId).stream()
                .map(AdoptionApplicationDto::fromEntity)
                .collect(Collectors.toList());
    }

    public List<AdoptionApplicationDto> findByPetOwnerId(Long ownerId) {
        return adoptionApplicationRepository.findByPetOwnerId(ownerId).stream()
                .map(AdoptionApplicationDto::fromEntity)
                .collect(Collectors.toList());
    }

    public List<AdoptionApplicationDto> findByStatus(String status) {
        return adoptionApplicationRepository.findByStatus(status).stream()
                .map(AdoptionApplicationDto::fromEntity)
                .collect(Collectors.toList());
    }

    public Page<AdoptionApplicationDto> findByStatus(String status, Pageable pageable) {
        return adoptionApplicationRepository.findByStatus(status, pageable)
                .map(AdoptionApplicationDto::fromEntity);
    }

    public Optional<AdoptionApplicationDto> findById(Long id) {
        return adoptionApplicationRepository.findById(id)
                .map(AdoptionApplicationDto::fromEntity);
    }

    @Transactional
    public Optional<AdoptionApplicationDto> update(Long id, AdoptionApplicationUpdateForm form) {
        return adoptionApplicationRepository.findById(id)
                .map(application -> {
                    form.updateEntity(application);
                    application = adoptionApplicationRepository.save(application);
                    return AdoptionApplicationDto.fromEntity(application);
                });
    }

    @Transactional
    public Optional<AdoptionApplicationDto> updateStatus(Long id, String status) {
        return adoptionApplicationRepository.findById(id)
                .map(application -> {
                    application.setStatus(status);
                    application = adoptionApplicationRepository.save(application);
                    return AdoptionApplicationDto.fromEntity(application);
                });
    }

    @Transactional
    public boolean delete(Long id) {
        if (adoptionApplicationRepository.existsById(id)) {
            adoptionApplicationRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
