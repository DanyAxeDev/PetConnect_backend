package br.com.petConnect.backend.Controller;

import br.com.petConnect.backend.DTO.TokenDto;
import br.com.petConnect.backend.Model.Address;
import br.com.petConnect.backend.Model.User;
import br.com.petConnect.backend.Repository.UserRepository;
import br.com.petConnect.backend.Service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth/oauth2")
@CrossOrigin("http://localhost:5173")
public class OAuth2Controller {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @GetMapping("/success")
    public ResponseEntity<?> oauth2Success(Authentication authentication) {
        try {
            if (authentication == null || !(authentication.getPrincipal() instanceof OAuth2User)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Falha na autenticação OAuth2 - Authentication null ou não é OAuth2User");
            }

            OAuth2User principal = (OAuth2User) authentication.getPrincipal();

            // Extrair informações do usuário GitHub
            String email = principal.getAttribute("email");
            String name = principal.getAttribute("name");
            String login = principal.getAttribute("login"); // username do GitHub
            
            if (email == null || email.isEmpty()) {
                // Se não tiver email público, usar o login + @github.local
                email = login + "@github.local";
            }

            // Verificar se o usuário já existe
            Optional<User> existingUser = userRepository.findByEmail(email);
            User user;

            if (existingUser.isPresent()) {
                user = existingUser.get();
                System.out.println("Usuário existente encontrado: " + user.getEmail());
            } else {
                // Criar novo usuário se não existir
                user = new User();
                user.setEmail(email);
                user.setFirstName(name != null ? name.split(" ")[0] : login);
                user.setLastName(name != null && name.split(" ").length > 1 ? 
                    name.substring(name.indexOf(" ") + 1) : "");
                user.setPhone(""); // Campo obrigatório, mas não temos do GitHub
                user.setPassword(""); // Usuários OAuth2 não têm senha
                
                // Endereço padrão (pode ser atualizado depois)
                Address address = new Address();
                address.setStreet("");
                address.setNumber(0);
                address.setNeighborhood("");
                address.setCity("");
                address.setState("");

                user.setAddress(address);
                
                user = userRepository.save(user);
            }

            // Gerar token JWT
            String token = tokenService.generateTokenForOAuth2User(user);
            
            // Redirecionar para o frontend com o token
            String redirectUrl = "http://localhost:5173/login?token=" + token + "&type=Bearer";
            return ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", redirectUrl)
                .build();
            
        } catch (Exception e) {
            System.out.println("Erro no OAuth2 Success: " + e.getMessage());
            e.printStackTrace();
            String errorUrl = "http://localhost:5173/login?error=oauth2_failed";
            return ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", errorUrl)
                .build();
        }
    }

    @GetMapping("/failure")
    public ResponseEntity<?> oauth2Failure() {
        String errorUrl = "http://localhost:5173/login?error=oauth2_failed";
        return ResponseEntity.status(HttpStatus.FOUND)
            .header("Location", errorUrl)
            .build();
    }

    @GetMapping("/user")
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Usuário não autenticado");
        }
        
        return ResponseEntity.ok(principal.getAttributes());
    }
}
