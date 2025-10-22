package br.com.petConnect.backend.DTO;

import br.com.petConnect.backend.Model.UserPet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPetDto {
    private Long userId;
    private String userName;
    private Long petId;
    private String petName;
    private LocalDateTime createdAt;

    public static UserPetDto fromEntity(UserPet userPet) {
        UserPetDto dto = new UserPetDto();
        if (userPet.getUser() != null) {
            dto.setUserId(userPet.getUser().getId());
            dto.setUserName(userPet.getUser().getFirstName() + " " + userPet.getUser().getLastName());
        }
        if (userPet.getPet() != null) {
            dto.setPetId(userPet.getPet().getId());
            dto.setPetName(userPet.getPet().getName());
        }
        dto.setCreatedAt(userPet.getCreatedAt());
        return dto;
    }
}
