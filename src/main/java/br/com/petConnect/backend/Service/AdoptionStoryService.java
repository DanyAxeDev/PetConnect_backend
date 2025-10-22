package br.com.petConnect.backend.Service;

import br.com.petConnect.backend.DTO.AdoptionStoryDto;
import br.com.petConnect.backend.Form.AdoptionStoryForm;
import br.com.petConnect.backend.Form.AdoptionStoryUpdateForm;
import br.com.petConnect.backend.Model.AdoptionStory;
import br.com.petConnect.backend.Model.User;
import br.com.petConnect.backend.Repository.AdoptionStoryRepository;
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
public class AdoptionStoryService {

    @Autowired
    private AdoptionStoryRepository adoptionStoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public AdoptionStoryDto create(AdoptionStoryForm form) {
        User user = userRepository.findById(form.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        AdoptionStory story = form.toEntity();
        story.setUser(user);
        story = adoptionStoryRepository.save(story);
        
        return AdoptionStoryDto.fromEntity(story);
    }

    public List<AdoptionStoryDto> findAll() {
        return adoptionStoryRepository.findAll().stream()
                .map(AdoptionStoryDto::fromEntity)
                .collect(Collectors.toList());
    }

    public Page<AdoptionStoryDto> findAll(Pageable pageable) {
        return adoptionStoryRepository.findAll(pageable)
                .map(AdoptionStoryDto::fromEntity);
    }

    public List<AdoptionStoryDto> findByUserId(Long userId) {
        return adoptionStoryRepository.findByUserId(userId).stream()
                .map(AdoptionStoryDto::fromEntity)
                .collect(Collectors.toList());
    }

    public Page<AdoptionStoryDto> findByUserId(Long userId, Pageable pageable) {
        return adoptionStoryRepository.findByUserId(userId, pageable)
                .map(AdoptionStoryDto::fromEntity);
    }

    public List<AdoptionStoryDto> findApprovedStories() {
        return adoptionStoryRepository.findByApprovedTrue().stream()
                .map(AdoptionStoryDto::fromEntity)
                .collect(Collectors.toList());
    }

    public Page<AdoptionStoryDto> findApprovedStories(Pageable pageable) {
        return adoptionStoryRepository.findByApprovedTrue(pageable)
                .map(AdoptionStoryDto::fromEntity);
    }

    public List<AdoptionStoryDto> findApprovedStoriesOrderByDateDesc() {
        return adoptionStoryRepository.findApprovedStoriesOrderByDateDesc().stream()
                .map(AdoptionStoryDto::fromEntity)
                .collect(Collectors.toList());
    }

    public Page<AdoptionStoryDto> findByApproved(Boolean approved, Pageable pageable) {
        return adoptionStoryRepository.findByApproved(approved, pageable)
                .map(AdoptionStoryDto::fromEntity);
    }

    public Optional<AdoptionStoryDto> findById(Long id) {
        return adoptionStoryRepository.findById(id)
                .map(AdoptionStoryDto::fromEntity);
    }

    @Transactional
    public Optional<AdoptionStoryDto> update(Long id, AdoptionStoryUpdateForm form) {
        return adoptionStoryRepository.findById(id)
                .map(story -> {
                    form.updateEntity(story);
                    story = adoptionStoryRepository.save(story);
                    return AdoptionStoryDto.fromEntity(story);
                });
    }

    @Transactional
    public Optional<AdoptionStoryDto> approve(Long id) {
        return adoptionStoryRepository.findById(id)
                .map(story -> {
                    story.setApproved(true);
                    story = adoptionStoryRepository.save(story);
                    return AdoptionStoryDto.fromEntity(story);
                });
    }

    @Transactional
    public Optional<AdoptionStoryDto> reject(Long id) {
        return adoptionStoryRepository.findById(id)
                .map(story -> {
                    story.setApproved(false);
                    story = adoptionStoryRepository.save(story);
                    return AdoptionStoryDto.fromEntity(story);
                });
    }

    @Transactional
    public boolean delete(Long id) {
        if (adoptionStoryRepository.existsById(id)) {
            adoptionStoryRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
