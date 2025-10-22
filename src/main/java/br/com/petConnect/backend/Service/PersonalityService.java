package br.com.petConnect.backend.Service;

import br.com.petConnect.backend.DTO.PersonalityDto;
import br.com.petConnect.backend.Form.PersonalityForm;
import br.com.petConnect.backend.Form.PersonalityUpdateForm;
import br.com.petConnect.backend.Model.Personality;
import br.com.petConnect.backend.Model.Pet;
import br.com.petConnect.backend.Repository.PersonalityRepository;
import br.com.petConnect.backend.Repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonalityService {

    @Autowired
    private PersonalityRepository personalityRepository;

    @Autowired
    private PetRepository petRepository;

    @Transactional
    public PersonalityDto create(PersonalityForm form) {
        Pet pet = petRepository.findById(form.getPetId())
                .orElseThrow(() -> new RuntimeException("Pet não encontrado"));
        
        if (personalityRepository.existsByPetId(form.getPetId())) {
            throw new RuntimeException("Pet já possui personalidade cadastrada");
        }
        
        Personality personality = form.toEntity();
        personality.setPet(pet);
        personality = personalityRepository.save(personality);
        
        return PersonalityDto.fromEntity(personality);
    }

    public List<PersonalityDto> findAll() {
        return personalityRepository.findAll().stream()
                .map(PersonalityDto::fromEntity)
                .collect(Collectors.toList());
    }

    public Optional<PersonalityDto> findById(Long id) {
        return personalityRepository.findById(id)
                .map(PersonalityDto::fromEntity);
    }

    public Optional<PersonalityDto> findByPetId(Long petId) {
        return personalityRepository.findByPetId(petId)
                .map(PersonalityDto::fromEntity);
    }

    @Transactional
    public Optional<PersonalityDto> update(Long id, PersonalityUpdateForm form) {
        return personalityRepository.findById(id)
                .map(personality -> {
                    form.updateEntity(personality);
                    personality = personalityRepository.save(personality);
                    return PersonalityDto.fromEntity(personality);
                });
    }

    @Transactional
    public Optional<PersonalityDto> updateByPetId(Long petId, PersonalityUpdateForm form) {
        return personalityRepository.findByPetId(petId)
                .map(personality -> {
                    form.updateEntity(personality);
                    personality = personalityRepository.save(personality);
                    return PersonalityDto.fromEntity(personality);
                });
    }

    @Transactional
    public boolean delete(Long id) {
        if (personalityRepository.existsById(id)) {
            personalityRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean deleteByPetId(Long petId) {
        return personalityRepository.findByPetId(petId)
                .map(personality -> {
                    personalityRepository.delete(personality);
                    return true;
                })
                .orElse(false);
    }

    public boolean existsByPetId(Long petId) {
        return personalityRepository.existsByPetId(petId);
    }
}
