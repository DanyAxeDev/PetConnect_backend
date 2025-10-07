package br.com.petConnect.backend.Service;

import br.com.petConnect.backend.DTO.UserDto;
import br.com.petConnect.backend.Form.UserUpdateForm;
import br.com.petConnect.backend.Model.Profile;
import br.com.petConnect.backend.Model.User;
import br.com.petConnect.backend.Repository.ProfileRepository;
import br.com.petConnect.backend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ProfileRepository profileRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public UserDto createUser(User user) {
        // Verificar se o email já existe
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Email já está em uso");
        }
        
        // Criptografar a senha
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        // Associar automaticamente o perfil "User"
        Optional<Profile> userProfile = profileRepository.findByNome("User");
        if (userProfile.isPresent()) {
            if (user.getPerfis() == null) {
                user.setPerfis(new ArrayList<>());
            }
            user.getPerfis().add(userProfile.get());
        } else {
            throw new RuntimeException("Perfil 'User' não encontrado no sistema");
        }
        
        // Salvar o usuário
        User savedUser = userRepository.save(user);
        
        // Converter para DTO e retornar
        return convertToDto(savedUser);
    }
    
    public UserDto getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return convertToDto(user.get());
        } else {
            throw new RuntimeException("Usuário não encontrado com ID: " + id);
        }
    }
    
    public UserDto updateUser(Long id, UserUpdateForm form) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado com ID: " + id);
        }
        
        User user = optionalUser.get();
        
        // Verificar se o email já existe em outro usuário (apenas se email foi fornecido)
        if (form.getEmail() != null && !user.getEmail().equals(form.getEmail())) {
            Optional<User> existingUser = userRepository.findByEmail(form.getEmail());
            if (existingUser.isPresent()) {
                throw new RuntimeException("Email já está em uso por outro usuário");
            }
        }
        
        // Atualizar apenas os campos fornecidos (não nulos)
        if (form.getFirstName() != null) {
            user.setFirstName(form.getFirstName());
        }
        if (form.getLastName() != null) {
            user.setLastName(form.getLastName());
        }
        if (form.getBirthDate() != null) {
            user.setBirthDate(form.getBirthDate());
        }
        if (form.getPhone() != null) {
            user.setPhone(form.getPhone());
        }
        if (form.getAddress() != null) {
            user.setAddress(form.getAddress());
        }
        if (form.getEmail() != null) {
            user.setEmail(form.getEmail());
        }
        
        // Atualizar senha apenas se fornecida
        if (form.getPassword() != null && !form.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(form.getPassword()));
        }
        
        // Salvar as alterações
        User savedUser = userRepository.save(user);
        
        return convertToDto(savedUser);
    }
    
    public void deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.delete(user.get());
        } else {
            throw new RuntimeException("Usuário não encontrado com ID: " + id);
        }
    }
    
    private UserDto convertToDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setBirthDate(user.getBirthDate());
        dto.setPhone(user.getPhone());
        dto.setAddress(user.getAddress());
        dto.setEmail(user.getEmail());
        dto.setPerfis(user.getPerfis());
        return dto;
    }
}
