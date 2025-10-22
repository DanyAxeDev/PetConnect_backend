package br.com.petConnect.backend.Form;

import br.com.petConnect.backend.Model.UserPet;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPetForm {
    @NotNull(message = "ID do usuário é obrigatório")
    private Long userId;
    
    @NotNull(message = "ID do pet é obrigatório")
    private Long petId;

    public UserPet toEntity() {
        UserPet userPet = new UserPet();
        return userPet;
    }
}
