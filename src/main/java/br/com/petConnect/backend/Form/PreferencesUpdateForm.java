package br.com.petConnect.backend.Form;

import br.com.petConnect.backend.Model.Preferences;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreferencesUpdateForm {
    @NotNull(message = "ID é obrigatório")
    private Long id;
    
    private String species;
    private String gender;
    private String age;
    private String size;
    private Boolean active;
    private Boolean goodWithPets;
    private Boolean calm;
    private Boolean goodWithKids;
    private Boolean extrovert;
    private Boolean introvert;
    private Integer maxDistance;

    public void updateEntity(Preferences preferences) {
        if (this.species != null) preferences.setSpecies(this.species);
        if (this.gender != null) preferences.setGender(this.gender);
        if (this.age != null) preferences.setAge(this.age);
        if (this.size != null) preferences.setSize(this.size);
        if (this.active != null) preferences.setActive(this.active);
        if (this.goodWithPets != null) preferences.setGoodWithPets(this.goodWithPets);
        if (this.calm != null) preferences.setCalm(this.calm);
        if (this.goodWithKids != null) preferences.setGoodWithKids(this.goodWithKids);
        if (this.extrovert != null) preferences.setExtrovert(this.extrovert);
        if (this.introvert != null) preferences.setIntrovert(this.introvert);
        if (this.maxDistance != null) preferences.setMaxDistance(this.maxDistance);
    }
}
