package br.com.petConnect.backend.Service;

import br.com.petConnect.backend.DTO.PreferencesDto;
import br.com.petConnect.backend.Form.PreferencesForm;
import br.com.petConnect.backend.Form.PreferencesUpdateForm;
import br.com.petConnect.backend.Model.Preferences;
import br.com.petConnect.backend.Model.User;
import br.com.petConnect.backend.Repository.PreferencesRepository;
import br.com.petConnect.backend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PreferencesService {

    @Autowired
    private PreferencesRepository preferencesRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public PreferencesDto create(PreferencesForm form) {
        User user = userRepository.findById(form.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        if (preferencesRepository.existsByUserId(form.getUserId())) {
            throw new RuntimeException("Usuário já possui preferências cadastradas");
        }
        
        Preferences preferences = form.toEntity();
        preferences.setUser(user);
        preferences = preferencesRepository.save(preferences);
        
        return PreferencesDto.fromEntity(preferences);
    }

    public List<PreferencesDto> findAll() {
        return preferencesRepository.findAll().stream()
                .map(PreferencesDto::fromEntity)
                .collect(Collectors.toList());
    }

    public Optional<PreferencesDto> findById(Long id) {
        return preferencesRepository.findById(id)
                .map(PreferencesDto::fromEntity);
    }

    public Optional<PreferencesDto> findByUserId(Long userId) {
        return preferencesRepository.findByUserId(userId)
                .map(PreferencesDto::fromEntity);
    }

    @Transactional
    public Optional<PreferencesDto> update(Long id, PreferencesUpdateForm form) {
        return preferencesRepository.findById(id)
                .map(preferences -> {
                    form.updateEntity(preferences);
                    preferences = preferencesRepository.save(preferences);
                    return PreferencesDto.fromEntity(preferences);
                });
    }

    @Transactional
    public Optional<PreferencesDto> updateByUserId(Long userId, PreferencesUpdateForm form) {
        return preferencesRepository.findByUserId(userId)
                .map(preferences -> {
                    form.updateEntity(preferences);
                    preferences = preferencesRepository.save(preferences);
                    return PreferencesDto.fromEntity(preferences);
                });
    }

    @Transactional
    public boolean delete(Long id) {
        if (preferencesRepository.existsById(id)) {
            preferencesRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean deleteByUserId(Long userId) {
        return preferencesRepository.findByUserId(userId)
                .map(preferences -> {
                    preferencesRepository.delete(preferences);
                    return true;
                })
                .orElse(false);
    }

    public boolean existsByUserId(Long userId) {
        return preferencesRepository.existsByUserId(userId);
    }
}
