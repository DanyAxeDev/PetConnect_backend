package br.com.petConnect.backend.DTO;

import br.com.petConnect.backend.Model.AdoptionApplication;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdoptionApplicationDto {
    private Long id;
    private Long userId;
    private String userName;
    private String userContact;
    private Long petId;
    private String petName;
    private String message;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static AdoptionApplicationDto fromEntity(AdoptionApplication application) {
        AdoptionApplicationDto dto = new AdoptionApplicationDto();
        dto.setId(application.getId());
        if (application.getUser() != null) {
            dto.setUserId(application.getUser().getId());
        }
        dto.setUserName(application.getUserName());
        dto.setUserContact(application.getUserContact());
        if (application.getPet() != null) {
            dto.setPetId(application.getPet().getId());
            dto.setPetName(application.getPet().getName());
        }
        dto.setMessage(application.getMessage());
        dto.setStatus(application.getStatus());
        dto.setCreatedAt(application.getCreatedAt());
        dto.setUpdatedAt(application.getUpdatedAt());
        return dto;
    }
}
