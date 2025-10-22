package br.com.petConnect.backend.Form;

import br.com.petConnect.backend.Model.Address;
import br.com.petConnect.backend.Model.Pet;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetForm {
    @NotBlank(message = "Nome é obrigatório")
    private String name;
    
    @NotBlank(message = "Espécie é obrigatória")
    private String species;
    
    @NotBlank(message = "Gênero é obrigatório")
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
    
    @NotNull(message = "ID do dono é obrigatório")
    private Long ownerId;
    
    private String contactOption;
    private Boolean available = true;

    public Pet toEntity() {
        Pet pet = new Pet();
        pet.setName(this.name);
        pet.setSpecies(this.species);
        pet.setGender(this.gender);
        pet.setBirthDate(this.birthDate);
        pet.setSize(this.size);
        pet.setCastrationReceipt(this.castrationReceipt);
        pet.setVaccinationReceipt(this.vaccinationReceipt);
        
        if (this.street != null || this.number != null || this.neighborhood != null || 
            this.city != null || this.state != null) {
            Address address = new Address();
            address.setStreet(this.street);
            address.setNumber(this.number);
            address.setNeighborhood(this.neighborhood);
            address.setCity(this.city);
            address.setState(this.state);
            pet.setAddress(address);
        }
        
        pet.setHealth(this.health);
        pet.setAbout(this.about);
        
        if (this.photo1 != null || this.photo2 != null || this.photo3 != null) {
            Pet.Photos photos = new Pet.Photos();
            photos.setPhoto1(this.photo1);
            photos.setPhoto2(this.photo2);
            photos.setPhoto3(this.photo3);
            pet.setPhotos(photos);
        }
        
        pet.setContactOption(this.contactOption);
        pet.setAvailable(this.available);
        return pet;
    }
}
