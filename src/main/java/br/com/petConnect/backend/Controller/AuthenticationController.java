package br.com.petConnect.backend.Controller;

import br.com.petConnect.backend.DTO.TokenDto;
import br.com.petConnect.backend.Form.LoginForm;
import br.com.petConnect.backend.Service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@CrossOrigin("http://localhost:5173")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<?> authenticate(@RequestBody @Valid LoginForm form){
        UsernamePasswordAuthenticationToken dadosLogin = form.convert();
        try {
            Authentication authentication = authManager.authenticate(dadosLogin);
            String token = tokenService.generateToken(authentication);
            return ResponseEntity.ok(new TokenDto(token, "Bearer"));
        }catch(AuthenticationException e){
            return ResponseEntity.badRequest().body("E-mail ou senha incorretos");
        }
    }
}
