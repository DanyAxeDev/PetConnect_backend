package br.com.petConnect.backend.Controller;

import br.com.petConnect.backend.DTO.AdoptionApplicationDto;
import br.com.petConnect.backend.Form.AdoptionApplicationForm;
import br.com.petConnect.backend.Form.AdoptionApplicationUpdateForm;
import br.com.petConnect.backend.Service.AdoptionApplicationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/adoption-applications")
@CrossOrigin(origins = "*")
public class AdoptionApplicationController {

    @Autowired
    private AdoptionApplicationService adoptionApplicationService;

    @PostMapping
    public ResponseEntity<AdoptionApplicationDto> create(@Valid @RequestBody AdoptionApplicationForm form) {
        try {
            AdoptionApplicationDto application = adoptionApplicationService.create(form);
            return ResponseEntity.status(HttpStatus.CREATED).body(application);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<Page<AdoptionApplicationDto>> findAll(Pageable pageable) {
        Page<AdoptionApplicationDto> applications = adoptionApplicationService.findAll(pageable);
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/all")
    public ResponseEntity<List<AdoptionApplicationDto>> findAllList() {
        List<AdoptionApplicationDto> applications = adoptionApplicationService.findAll();
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AdoptionApplicationDto>> findByUserId(@PathVariable Long userId) {
        List<AdoptionApplicationDto> applications = adoptionApplicationService.findByUserId(userId);
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/user/{userId}/page")
    public ResponseEntity<Page<AdoptionApplicationDto>> findByUserId(@PathVariable Long userId, Pageable pageable) {
        Page<AdoptionApplicationDto> applications = adoptionApplicationService.findByUserId(userId, pageable);
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/pet/{petId}")
    public ResponseEntity<List<AdoptionApplicationDto>> findByPetId(@PathVariable Long petId) {
        List<AdoptionApplicationDto> applications = adoptionApplicationService.findByPetId(petId);
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/pet-owner/{ownerId}")
    public ResponseEntity<List<AdoptionApplicationDto>> findByPetOwnerId(@PathVariable Long ownerId) {
        List<AdoptionApplicationDto> applications = adoptionApplicationService.findByPetOwnerId(ownerId);
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<AdoptionApplicationDto>> findByStatus(@PathVariable String status) {
        List<AdoptionApplicationDto> applications = adoptionApplicationService.findByStatus(status);
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/status/{status}/page")
    public ResponseEntity<Page<AdoptionApplicationDto>> findByStatus(@PathVariable String status, Pageable pageable) {
        Page<AdoptionApplicationDto> applications = adoptionApplicationService.findByStatus(status, pageable);
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdoptionApplicationDto> findById(@PathVariable Long id) {
        Optional<AdoptionApplicationDto> application = adoptionApplicationService.findById(id);
        return application.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdoptionApplicationDto> update(@PathVariable Long id, @Valid @RequestBody AdoptionApplicationUpdateForm form) {
        Optional<AdoptionApplicationDto> application = adoptionApplicationService.update(id, form);
        return application.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<AdoptionApplicationDto> updateStatus(@PathVariable Long id, @RequestParam String status) {
        Optional<AdoptionApplicationDto> application = adoptionApplicationService.updateStatus(id, status);
        return application.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = adoptionApplicationService.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
