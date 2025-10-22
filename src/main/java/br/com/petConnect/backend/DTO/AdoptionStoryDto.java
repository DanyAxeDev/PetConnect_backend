package br.com.petConnect.backend.DTO;

import br.com.petConnect.backend.Model.AdoptionStory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdoptionStoryDto {
    private Long id;
    private Long userId;
    private String userName;
    private String human;
    private String pet;
    private String story;
    private String photo;
    private Boolean approved;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static AdoptionStoryDto fromEntity(AdoptionStory story) {
        AdoptionStoryDto dto = new AdoptionStoryDto();
        dto.setId(story.getId());
        if (story.getUser() != null) {
            dto.setUserId(story.getUser().getId());
            dto.setUserName(story.getUser().getFirstName() + " " + story.getUser().getLastName());
        }
        dto.setHuman(story.getHuman());
        dto.setPet(story.getPet());
        dto.setStory(story.getStory());
        dto.setPhoto(story.getPhoto());
        dto.setApproved(story.getApproved());
        dto.setCreatedAt(story.getCreatedAt());
        dto.setUpdatedAt(story.getUpdatedAt());
        return dto;
    }
}
