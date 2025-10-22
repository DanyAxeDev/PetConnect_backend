package br.com.petConnect.backend.Form;

import br.com.petConnect.backend.Model.Personality;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonalityUpdateForm {
    @NotNull(message = "ID é obrigatório")
    private Long id;
    
    private Boolean active;
    private Boolean goodWithPets;
    private Boolean calm;
    private Boolean goodWithKids;
    private Boolean extrovert;
    private Boolean introvert;

    public void updateEntity(Personality personality) {
        if (this.active != null) personality.setActive(this.active);
        if (this.goodWithPets != null) personality.setGoodWithPets(this.goodWithPets);
        if (this.calm != null) personality.setCalm(this.calm);
        if (this.goodWithKids != null) personality.setGoodWithKids(this.goodWithKids);
        if (this.extrovert != null) personality.setExtrovert(this.extrovert);
        if (this.introvert != null) personality.setIntrovert(this.introvert);
    }
}
