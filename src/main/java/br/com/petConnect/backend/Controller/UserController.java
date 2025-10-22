package br.com.petConnect.backend.Controller;

import br.com.petConnect.backend.DTO.UserDto;
import br.com.petConnect.backend.Form.UserForm;
import br.com.petConnect.backend.Form.UserUpdateForm;
import br.com.petConnect.backend.Form.PasswordChangeForm;
import br.com.petConnect.backend.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@CrossOrigin("http://localhost:5173")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody @Valid UserForm form) {
        try {
            UserDto userDto = userService.createUser(form.convert());
            return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erro ao criar usuário: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno do servidor: " + e.getMessage());
        }
    }
    
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(org.springframework.security.core.Authentication authentication) {
        try {
            if (authentication == null || authentication.getPrincipal() == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não autenticado");
            }
            
            br.com.petConnect.backend.Model.User user = (br.com.petConnect.backend.Model.User) authentication.getPrincipal();
            System.out.println("Usuário autenticado: " + user.getEmail() + " (ID: " + user.getId() + ")");
            
            UserDto userDto = userService.getUserById(user.getId());
            return ResponseEntity.ok(userDto);
        } catch (ClassCastException e) {
            System.err.println("Erro de cast na autenticação: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Erro na autenticação: " + e.getMessage());
        } catch (RuntimeException e) {
            System.err.println("Erro ao buscar usuário: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao buscar usuário: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro interno: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno do servidor: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            UserDto userDto = userService.getUserById(id);
            return ResponseEntity.ok(userDto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao buscar usuário: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno do servidor: " + e.getMessage());
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody @Valid UserUpdateForm form) {
        try {
            UserDto userDto = userService.updateUser(id, form);
            return ResponseEntity.ok(userDto);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erro ao atualizar usuário: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno do servidor: " + e.getMessage());
        }
    }
    
    @PutMapping("/{id}/password")
    public ResponseEntity<?> changePassword(@PathVariable Long id, @RequestBody @Valid PasswordChangeForm form) {
        try {
            userService.changePassword(id, form);
            return ResponseEntity.ok("Senha alterada com sucesso");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erro ao alterar senha: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno do servidor: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok("Usuário deletado com sucesso");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erro ao deletar usuário: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno do servidor: " + e.getMessage());
        }
    }
}
