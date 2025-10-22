package br.com.petConnect.backend.Form;

import br.com.petConnect.backend.Model.AdoptionStory;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdoptionStoryUpdateForm {
    @NotNull(message = "ID é obrigatório")
    private Long id;
    
    private String human;
    private String pet;
    private String story;
    private String photo;
    private Boolean approved;

    public void updateEntity(AdoptionStory story) {
        if (this.human != null) story.setHuman(this.human);
        if (this.pet != null) story.setPet(this.pet);
        if (this.story != null) story.setStory(this.story);
        if (this.photo != null) story.setPhoto(this.photo);
        if (this.approved != null) story.setApproved(this.approved);
    }
}
