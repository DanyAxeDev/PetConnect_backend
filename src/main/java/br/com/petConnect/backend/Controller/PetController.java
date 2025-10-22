package br.com.petConnect.backend.Controller;

import br.com.petConnect.backend.DTO.PetDto;
import br.com.petConnect.backend.Form.PetForm;
import br.com.petConnect.backend.Form.PetUpdateForm;
import br.com.petConnect.backend.Service.PetService;
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
@RequestMapping("/pets")
@CrossOrigin(origins = "*")
public class PetController {

    @Autowired
    private PetService petService;

    @PostMapping
    public ResponseEntity<PetDto> create(@Valid @RequestBody PetForm form) {
        try {
            PetDto pet = petService.create(form);
            return ResponseEntity.status(HttpStatus.CREATED).body(pet);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<Page<PetDto>> findAll(Pageable pageable) {
        Page<PetDto> pets = petService.findAll(pageable);
        return ResponseEntity.ok(pets);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PetDto>> findAllList() {
        List<PetDto> pets = petService.findAll();
        return ResponseEntity.ok(pets);
    }

    @GetMapping("/available")
    public ResponseEntity<Page<PetDto>> findAvailablePets(Pageable pageable) {
        Page<PetDto> pets = petService.findAvailablePets(pageable);
        return ResponseEntity.ok(pets);
    }

    @GetMapping("/available/list")
    public ResponseEntity<List<PetDto>> findAvailablePetsList(@RequestParam(required = false) Long userId) {
        List<PetDto> pets = petService.findAvailablePetsExcludingUser(userId);
        return ResponseEntity.ok(pets);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<PetDto>> findAvailablePetsWithFilters(
            @RequestParam(required = false) String species,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) String size,
            Pageable pageable) {
        Page<PetDto> pets = petService.findAvailablePetsWithFilters(species, gender, size, pageable);
        return ResponseEntity.ok(pets);
    }

    @GetMapping("/location")
    public ResponseEntity<List<PetDto>> findByLocation(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String state) {
        List<PetDto> pets = petService.findByLocation(city, state);
        return ResponseEntity.ok(pets);
    }

    @GetMapping("/search/name")
    public ResponseEntity<List<PetDto>> findByName(@RequestParam String name) {
        List<PetDto> pets = petService.findByName(name);
        return ResponseEntity.ok(pets);
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<PetDto>> findByOwnerId(@PathVariable Long ownerId) {
        List<PetDto> pets = petService.findByOwnerId(ownerId);
        return ResponseEntity.ok(pets);
    }

    @GetMapping("/favorites/{userId}")
    public ResponseEntity<List<PetDto>> findFavoritesByUserId(@PathVariable Long userId) {
        List<PetDto> pets = petService.findFavoritesByUserId(userId);
        return ResponseEntity.ok(pets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetDto> findById(@PathVariable Long id) {
        Optional<PetDto> pet = petService.findById(id);
        return pet.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/available")
    public ResponseEntity<PetDto> findAvailableById(@PathVariable Long id) {
        Optional<PetDto> pet = petService.findAvailableById(id);
        return pet.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetDto> update(@PathVariable Long id, @Valid @RequestBody PetUpdateForm form) {
        Optional<PetDto> pet = petService.update(id, form);
        return pet.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/availability")
    public ResponseEntity<Boolean> setAvailability(@PathVariable Long id, @RequestParam boolean available) {
        boolean updated = petService.setAvailability(id, available);
        return updated ? ResponseEntity.ok(true) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = petService.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
