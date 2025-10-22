package br.com.petConnect.backend.Form;

import br.com.petConnect.backend.Model.AdoptionApplication;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdoptionApplicationUpdateForm {
    @NotNull(message = "ID é obrigatório")
    private Long id;
    
    private String userName;
    private String userContact;
    private String message;
    private String status;

    public void updateEntity(AdoptionApplication application) {
        if (this.userName != null) application.setUserName(this.userName);
        if (this.userContact != null) application.setUserContact(this.userContact);
        if (this.message != null) application.setMessage(this.message);
        if (this.status != null) application.setStatus(this.status);
    }
}
