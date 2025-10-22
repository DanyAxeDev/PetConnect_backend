package br.com.petConnect.backend.Form;

import br.com.petConnect.backend.Model.Preferences;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreferencesForm {
    @NotNull(message = "ID do usuário é obrigatório")
    private Long userId;
    
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

    public Preferences toEntity() {
        Preferences preferences = new Preferences();
        preferences.setSpecies(this.species);
        preferences.setGender(this.gender);
        preferences.setAge(this.age);
        preferences.setSize(this.size);
        preferences.setActive(this.active);
        preferences.setGoodWithPets(this.goodWithPets);
        preferences.setCalm(this.calm);
        preferences.setGoodWithKids(this.goodWithKids);
        preferences.setExtrovert(this.extrovert);
        preferences.setIntrovert(this.introvert);
        preferences.setMaxDistance(this.maxDistance);
        return preferences;
    }
}
