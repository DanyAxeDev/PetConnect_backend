package br.com.petConnect.backend.DTO;

import br.com.petConnect.backend.Model.Pet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetDto {
    private Long id;
    private String name;
    private String species;
    private String gender;
    private LocalDate birthDate;
    private String size;
    private String castrationReceipt;
    private String vaccinationReceipt;
    private String street;
    private Integer number;
    private String neighborhood;
    private String city;
    private String state;
    private String health;
    private String about;
    private String photo1;
    private String photo2;
    private String photo3;
    private Double latitude;
    private Double longitude;
    private Double distance;
    private Long ownerId;
    private String ownerName;
    private String contactOption;
    private Boolean available;
    
    // Personalidade
    private Boolean active;
    private Boolean goodWithPets;
    private Boolean calm;
    private Boolean goodWithKids;
    private Boolean extrovert;
    private Boolean introvert;

    public static PetDto fromEntity(Pet pet) {
        PetDto dto = new PetDto();
        dto.setId(pet.getId());
        dto.setName(pet.getName());
        dto.setSpecies(pet.getSpecies());
        dto.setGender(pet.getGender());
        dto.setBirthDate(pet.getBirthDate());
        dto.setSize(pet.getSize());
        dto.setCastrationReceipt(pet.getCastrationReceipt());
        dto.setVaccinationReceipt(pet.getVaccinationReceipt());
        
        if (pet.getAddress() != null) {
            dto.setStreet(pet.getAddress().getStreet());
            dto.setNumber(pet.getAddress().getNumber());
            dto.setNeighborhood(pet.getAddress().getNeighborhood());
            dto.setCity(pet.getAddress().getCity());
            dto.setState(pet.getAddress().getState());
        }
        
        dto.setHealth(pet.getHealth());
        dto.setAbout(pet.getAbout());
        dto.setLatitude(pet.getLatitude());
        dto.setLongitude(pet.getLongitude());
        
        if (pet.getPhotos() != null) {
            dto.setPhoto1(pet.getPhotos().getPhoto1());
            dto.setPhoto2(pet.getPhotos().getPhoto2());
            dto.setPhoto3(pet.getPhotos().getPhoto3());
        }
        
        if (pet.getOwner() != null) {
            dto.setOwnerId(pet.getOwner().getId());
            dto.setOwnerName(pet.getOwner().getFirstName() + " " + pet.getOwner().getLastName());
        }
        
        dto.setContactOption(pet.getContactOption());
        dto.setAvailable(pet.getAvailable());
        
        // Personalidade
        if (pet.getPersonality() != null) {
            dto.setActive(pet.getPersonality().getActive());
            dto.setGoodWithPets(pet.getPersonality().getGoodWithPets());
            dto.setCalm(pet.getPersonality().getCalm());
            dto.setGoodWithKids(pet.getPersonality().getGoodWithKids());
            dto.setExtrovert(pet.getPersonality().getExtrovert());
            dto.setIntrovert(pet.getPersonality().getIntrovert());
        }
        
        return dto;
    }
}
