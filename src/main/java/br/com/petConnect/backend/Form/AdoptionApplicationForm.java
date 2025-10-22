package br.com.petConnect.backend.Form;

import br.com.petConnect.backend.Model.AdoptionApplication;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdoptionApplicationForm {
    @NotNull(message = "ID do usuário é obrigatório")
    private Long userId;
    
    @NotBlank(message = "Nome do usuário é obrigatório")
    private String userName;
    
    @NotBlank(message = "Contato do usuário é obrigatório")
    private String userContact;
    
    @NotNull(message = "ID do pet é obrigatório")
    private Long petId;
    
    private String message;

    public AdoptionApplication toEntity() {
        AdoptionApplication application = new AdoptionApplication();
        application.setUserName(this.userName);
        application.setUserContact(this.userContact);
        application.setMessage(this.message);
        application.setStatus("PENDING");
        return application;
    }
}
