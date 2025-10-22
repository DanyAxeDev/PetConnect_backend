package br.com.petConnect.backend.Form;

import br.com.petConnect.backend.Model.AdoptionStory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdoptionStoryForm {
    @NotNull(message = "ID do usuário é obrigatório")
    private Long userId;
    
    @NotBlank(message = "Nome do humano é obrigatório")
    private String human;
    
    @NotBlank(message = "Nome do pet é obrigatório")
    private String pet;
    
    @NotBlank(message = "História é obrigatória")
    private String story;
    
    private String photo;

    public AdoptionStory toEntity() {
        AdoptionStory story = new AdoptionStory();
        story.setHuman(this.human);
        story.setPet(this.pet);
        story.setStory(this.story);
        story.setPhoto(this.photo);
        story.setApproved(false);
        return story;
    }
}
