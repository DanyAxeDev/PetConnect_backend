package br.com.petConnect.backend.Form;

import br.com.petConnect.backend.Model.Personality;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonalityForm {
    @NotNull(message = "ID do pet é obrigatório")
    private Long petId;
    
    private Boolean active;
    private Boolean goodWithPets;
    private Boolean calm;
    private Boolean goodWithKids;
    private Boolean extrovert;
    private Boolean introvert;

    public Personality toEntity() {
        Personality personality = new Personality();
        personality.setActive(this.active);
        personality.setGoodWithPets(this.goodWithPets);
        personality.setCalm(this.calm);
        personality.setGoodWithKids(this.goodWithKids);
        personality.setExtrovert(this.extrovert);
        personality.setIntrovert(this.introvert);
        return personality;
    }
}
