package br.com.petConnect.backend.Form;

import br.com.petConnect.backend.Model.Address;
import br.com.petConnect.backend.Model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class UserForm {
    
    @NotBlank(message = "Nome é obrigatório")
    private String firstName;
    
    @NotBlank(message = "Sobrenome é obrigatório")
    private String lastName;
    
    @NotNull(message = "Data de nascimento é obrigatória")
    private LocalDate birthDate;
    
    private String phone;
    
    private Address address;
    
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ter um formato válido")
    private String email;
    
    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, message = "Senha deve ter pelo menos 6 caracteres")
    private String password;
    
    public User convert() {
        User user = new User();
        user.setFirstName(this.firstName);
        user.setLastName(this.lastName);
        user.setBirthDate(this.birthDate);
        user.setPhone(this.phone);
        user.setAddress(this.address);
        user.setEmail(this.email);
        user.setPassword(this.password);
        return user;
    }
}
