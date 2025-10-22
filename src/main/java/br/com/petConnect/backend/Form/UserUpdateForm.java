package br.com.petConnect.backend.Form;

import br.com.petConnect.backend.Model.Address;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class UserUpdateForm {
    
    private String firstName;
    
    private String lastName;
    
    private LocalDate birthDate;
    
    private String phone;
    
    private Address address;
    
    @Email(message = "Email deve ter um formato válido")
    private String email;
    
    @Size(min = 6, message = "Senha deve ter pelo menos 6 caracteres")
    private String password;
}
