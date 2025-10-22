package br.com.petConnect.backend.Controller;

import br.com.petConnect.backend.DTO.PersonalityDto;
import br.com.petConnect.backend.Form.PersonalityForm;
import br.com.petConnect.backend.Form.PersonalityUpdateForm;
import br.com.petConnect.backend.Service.PersonalityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/personalities")
@CrossOrigin(origins = "*")
public class PersonalityController {

    @Autowired
    private PersonalityService personalityService;

    @PostMapping
    public ResponseEntity<PersonalityDto> create(@Valid @RequestBody PersonalityForm form) {
        try {
            PersonalityDto personality = personalityService.create(form);
            return ResponseEntity.status(HttpStatus.CREATED).body(personality);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<PersonalityDto>> findAll() {
        List<PersonalityDto> personalities = personalityService.findAll();
        return ResponseEntity.ok(personalities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonalityDto> findById(@PathVariable Long id) {
        Optional<PersonalityDto> personality = personalityService.findById(id);
        return personality.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/pet/{petId}")
    public ResponseEntity<PersonalityDto> findByPetId(@PathVariable Long petId) {
        Optional<PersonalityDto> personality = personalityService.findByPetId(petId);
        return personality.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/pet/{petId}/exists")
    public ResponseEntity<Boolean> existsByPetId(@PathVariable Long petId) {
        boolean exists = personalityService.existsByPetId(petId);
        return ResponseEntity.ok(exists);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonalityDto> update(@PathVariable Long id, @Valid @RequestBody PersonalityUpdateForm form) {
        Optional<PersonalityDto> personality = personalityService.update(id, form);
        return personality.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/pet/{petId}")
    public ResponseEntity<PersonalityDto> updateByPetId(@PathVariable Long petId, @Valid @RequestBody PersonalityUpdateForm form) {
        Optional<PersonalityDto> personality = personalityService.updateByPetId(petId, form);
        return personality.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = personalityService.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/pet/{petId}")
    public ResponseEntity<Void> deleteByPetId(@PathVariable Long petId) {
        boolean deleted = personalityService.deleteByPetId(petId);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
