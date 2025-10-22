package br.com.petConnect.backend.DTO;

import br.com.petConnect.backend.Model.Personality;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonalityDto {
    private Long id;
    private Long petId;
    private Boolean active;
    private Boolean goodWithPets;
    private Boolean calm;
    private Boolean goodWithKids;
    private Boolean extrovert;
    private Boolean introvert;

    public static PersonalityDto fromEntity(Personality personality) {
        PersonalityDto dto = new PersonalityDto();
        dto.setId(personality.getId());
        if (personality.getPet() != null) {
            dto.setPetId(personality.getPet().getId());
        }
        dto.setActive(personality.getActive());
        dto.setGoodWithPets(personality.getGoodWithPets());
        dto.setCalm(personality.getCalm());
        dto.setGoodWithKids(personality.getGoodWithKids());
        dto.setExtrovert(personality.getExtrovert());
        dto.setIntrovert(personality.getIntrovert());
        return dto;
    }
}
