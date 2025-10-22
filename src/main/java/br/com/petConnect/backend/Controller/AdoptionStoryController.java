package br.com.petConnect.backend.Controller;

import br.com.petConnect.backend.DTO.AdoptionStoryDto;
import br.com.petConnect.backend.Form.AdoptionStoryForm;
import br.com.petConnect.backend.Form.AdoptionStoryUpdateForm;
import br.com.petConnect.backend.Service.AdoptionStoryService;
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
@RequestMapping("/adoption-stories")
@CrossOrigin(origins = "*")
public class AdoptionStoryController {

    @Autowired
    private AdoptionStoryService adoptionStoryService;

    @PostMapping
    public ResponseEntity<AdoptionStoryDto> create(@Valid @RequestBody AdoptionStoryForm form) {
        try {
            AdoptionStoryDto story = adoptionStoryService.create(form);
            return ResponseEntity.status(HttpStatus.CREATED).body(story);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<Page<AdoptionStoryDto>> findAll(Pageable pageable) {
        Page<AdoptionStoryDto> stories = adoptionStoryService.findAll(pageable);
        return ResponseEntity.ok(stories);
    }

    @GetMapping("/all")
    public ResponseEntity<List<AdoptionStoryDto>> findAllList() {
        List<AdoptionStoryDto> stories = adoptionStoryService.findAll();
        return ResponseEntity.ok(stories);
    }

    @GetMapping("/approved")
    public ResponseEntity<List<AdoptionStoryDto>> findApprovedStories() {
        List<AdoptionStoryDto> stories = adoptionStoryService.findApprovedStories();
        return ResponseEntity.ok(stories);
    }

    @GetMapping("/approved/page")
    public ResponseEntity<Page<AdoptionStoryDto>> findApprovedStories(Pageable pageable) {
        Page<AdoptionStoryDto> stories = adoptionStoryService.findApprovedStories(pageable);
        return ResponseEntity.ok(stories);
    }

    @GetMapping("/approved/recent")
    public ResponseEntity<List<AdoptionStoryDto>> findApprovedStoriesOrderByDateDesc() {
        List<AdoptionStoryDto> stories = adoptionStoryService.findApprovedStoriesOrderByDateDesc();
        return ResponseEntity.ok(stories);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AdoptionStoryDto>> findByUserId(@PathVariable Long userId) {
        List<AdoptionStoryDto> stories = adoptionStoryService.findByUserId(userId);
        return ResponseEntity.ok(stories);
    }

    @GetMapping("/user/{userId}/page")
    public ResponseEntity<Page<AdoptionStoryDto>> findByUserId(@PathVariable Long userId, Pageable pageable) {
        Page<AdoptionStoryDto> stories = adoptionStoryService.findByUserId(userId, pageable);
        return ResponseEntity.ok(stories);
    }

    @GetMapping("/approved/{approved}")
    public ResponseEntity<Page<AdoptionStoryDto>> findByApproved(@PathVariable Boolean approved, Pageable pageable) {
        Page<AdoptionStoryDto> stories = adoptionStoryService.findByApproved(approved, pageable);
        return ResponseEntity.ok(stories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdoptionStoryDto> findById(@PathVariable Long id) {
        Optional<AdoptionStoryDto> story = adoptionStoryService.findById(id);
        return story.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdoptionStoryDto> update(@PathVariable Long id, @Valid @RequestBody AdoptionStoryUpdateForm form) {
        Optional<AdoptionStoryDto> story = adoptionStoryService.update(id, form);
        return story.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/approve")
    public ResponseEntity<AdoptionStoryDto> approve(@PathVariable Long id) {
        Optional<AdoptionStoryDto> story = adoptionStoryService.approve(id);
        return story.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/reject")
    public ResponseEntity<AdoptionStoryDto> reject(@PathVariable Long id) {
        Optional<AdoptionStoryDto> story = adoptionStoryService.reject(id);
        return story.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = adoptionStoryService.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
