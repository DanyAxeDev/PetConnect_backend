package br.com.petConnect.backend.Controller;

import br.com.petConnect.backend.DTO.UserPetDto;
import br.com.petConnect.backend.Form.UserPetForm;
import br.com.petConnect.backend.Service.UserPetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user-pets")
@CrossOrigin(origins = "*")
public class UserPetController {

    @Autowired
    private UserPetService userPetService;

    @PostMapping
    public ResponseEntity<UserPetDto> addFavorite(@Valid @RequestBody UserPetForm form) {
        try {
            UserPetDto userPet = userPetService.addFavorite(form);
            return ResponseEntity.status(HttpStatus.CREATED).body(userPet);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<UserPetDto>> findAll() {
        List<UserPetDto> userPets = userPetService.findAll();
        return ResponseEntity.ok(userPets);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserPetDto>> findByUserId(@PathVariable Long userId) {
        List<UserPetDto> userPets = userPetService.findByUserId(userId);
        return ResponseEntity.ok(userPets);
    }

    @GetMapping("/pet/{petId}")
    public ResponseEntity<List<UserPetDto>> findByPetId(@PathVariable Long petId) {
        List<UserPetDto> userPets = userPetService.findByPetId(petId);
        return ResponseEntity.ok(userPets);
    }

    @GetMapping("/user/{userId}/pet/{petId}")
    public ResponseEntity<UserPetDto> findByUserAndPet(@PathVariable Long userId, @PathVariable Long petId) {
        Optional<UserPetDto> userPet = userPetService.findByUserAndPet(userId, petId);
        return userPet.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}/pet/{petId}/is-favorite")
    public ResponseEntity<Boolean> isFavorite(@PathVariable Long userId, @PathVariable Long petId) {
        boolean isFavorite = userPetService.isFavorite(userId, petId);
        return ResponseEntity.ok(isFavorite);
    }

    @GetMapping("/user/{userId}/count")
    public ResponseEntity<Long> countByUserId(@PathVariable Long userId) {
        long count = userPetService.countByUserId(userId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/pet/{petId}/count")
    public ResponseEntity<Long> countByPetId(@PathVariable Long petId) {
        long count = userPetService.countByPetId(petId);
        return ResponseEntity.ok(count);
    }

    @DeleteMapping("/user/{userId}/pet/{petId}")
    public ResponseEntity<Void> removeFavorite(@PathVariable Long userId, @PathVariable Long petId) {
        boolean removed = userPetService.removeFavorite(userId, petId);
        return removed ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/user/{userId}/pet/{petId}/delete")
    public ResponseEntity<Void> delete(@PathVariable Long userId, @PathVariable Long petId) {
        boolean deleted = userPetService.delete(userId, petId);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
