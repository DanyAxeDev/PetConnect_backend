package br.com.petConnect.backend.DTO;

import br.com.petConnect.backend.Model.Preferences;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreferencesDto {
    private Long id;
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

    public static PreferencesDto fromEntity(Preferences preferences) {
        PreferencesDto dto = new PreferencesDto();
        dto.setId(preferences.getId());
        if (preferences.getUser() != null) {
            dto.setUserId(preferences.getUser().getId());
        }
        dto.setSpecies(preferences.getSpecies());
        dto.setGender(preferences.getGender());
        dto.setAge(preferences.getAge());
        dto.setSize(preferences.getSize());
        dto.setActive(preferences.getActive());
        dto.setGoodWithPets(preferences.getGoodWithPets());
        dto.setCalm(preferences.getCalm());
        dto.setGoodWithKids(preferences.getGoodWithKids());
        dto.setExtrovert(preferences.getExtrovert());
        dto.setIntrovert(preferences.getIntrovert());
        dto.setMaxDistance(preferences.getMaxDistance());
        return dto;
    }
}
