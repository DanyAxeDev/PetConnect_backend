package br.com.petConnect.backend.Security;

import br.com.petConnect.backend.Model.User;
import br.com.petConnect.backend.Repository.UserRepository;
import br.com.petConnect.backend.Service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class AuthenticationViaTokenFilter extends OncePerRequestFilter {

    private TokenService tokenService;
    private UserRepository repository;

    public AuthenticationViaTokenFilter(TokenService tokenService, UserRepository repository){
        this.tokenService = tokenService;
        this.repository = repository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = recoverToken(request);
        boolean valid = tokenService.isTokenValid(token);
        if(valid){
            authenticateClient(token);
        }

        filterChain.doFilter(request, response);
    }

    private void authenticateClient(String token) {
        Long idUser = tokenService.getIdUser(token);
        User user = repository.findById(idUser).get();
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String recoverToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if(token == null || !token.startsWith("Bearer ")){
            return null;
        }

        return token.substring(7);
    }
}
