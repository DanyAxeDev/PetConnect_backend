package br.com.petConnect.backend.Controller;

import br.com.petConnect.backend.DTO.PreferencesDto;
import br.com.petConnect.backend.Form.PreferencesForm;
import br.com.petConnect.backend.Form.PreferencesUpdateForm;
import br.com.petConnect.backend.Service.PreferencesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/preferences")
@CrossOrigin(origins = "*")
public class PreferencesController {

    @Autowired
    private PreferencesService preferencesService;

    @PostMapping
    public ResponseEntity<PreferencesDto> create(@Valid @RequestBody PreferencesForm form) {
        try {
            PreferencesDto preferences = preferencesService.create(form);
            return ResponseEntity.status(HttpStatus.CREATED).body(preferences);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<PreferencesDto>> findAll() {
        List<PreferencesDto> preferences = preferencesService.findAll();
        return ResponseEntity.ok(preferences);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PreferencesDto> findById(@PathVariable Long id) {
        Optional<PreferencesDto> preferences = preferencesService.findById(id);
        return preferences.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<PreferencesDto> findByUserId(@PathVariable Long userId) {
        Optional<PreferencesDto> preferences = preferencesService.findByUserId(userId);
        return preferences.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}/exists")
    public ResponseEntity<Boolean> existsByUserId(@PathVariable Long userId) {
        boolean exists = preferencesService.existsByUserId(userId);
        return ResponseEntity.ok(exists);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PreferencesDto> update(@PathVariable Long id, @Valid @RequestBody PreferencesUpdateForm form) {
        Optional<PreferencesDto> preferences = preferencesService.update(id, form);
        return preferences.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/user/{userId}")
    public ResponseEntity<PreferencesDto> updateByUserId(@PathVariable Long userId, @Valid @RequestBody PreferencesUpdateForm form) {
        Optional<PreferencesDto> preferences = preferencesService.updateByUserId(userId, form);
        return preferences.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = preferencesService.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Void> deleteByUserId(@PathVariable Long userId) {
        boolean deleted = preferencesService.deleteByUserId(userId);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
