package br.com.petConnect.backend.Form;

import br.com.petConnect.backend.Model.Address;
import br.com.petConnect.backend.Model.Pet;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetUpdateForm {
    @NotNull(message = "ID é obrigatório")
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
    private String contactOption;
    private Boolean available;

    public void updateEntity(Pet pet) {
        if (this.name != null) pet.setName(this.name);
        if (this.species != null) pet.setSpecies(this.species);
        if (this.gender != null) pet.setGender(this.gender);
        if (this.birthDate != null) pet.setBirthDate(this.birthDate);
        if (this.size != null) pet.setSize(this.size);
        if (this.castrationReceipt != null) pet.setCastrationReceipt(this.castrationReceipt);
        if (this.vaccinationReceipt != null) pet.setVaccinationReceipt(this.vaccinationReceipt);
        
        if (pet.getAddress() == null && (this.street != null || this.number != null || 
            this.neighborhood != null || this.city != null || this.state != null)) {
            pet.setAddress(new Address());
        }
        
        if (pet.getAddress() != null) {
            if (this.street != null) pet.getAddress().setStreet(this.street);
            if (this.number != null) pet.getAddress().setNumber(this.number);
            if (this.neighborhood != null) pet.getAddress().setNeighborhood(this.neighborhood);
            if (this.city != null) pet.getAddress().setCity(this.city);
            if (this.state != null) pet.getAddress().setState(this.state);
        }
        
        if (this.health != null) pet.setHealth(this.health);
        if (this.about != null) pet.setAbout(this.about);
        if (this.latitude != null) pet.setLatitude(this.latitude);
        if (this.longitude != null) pet.setLongitude(this.longitude);
        
        if (pet.getPhotos() == null && (this.photo1 != null || this.photo2 != null || this.photo3 != null)) {
            pet.setPhotos(new Pet.Photos());
        }
        
        if (pet.getPhotos() != null) {
            if (this.photo1 != null) pet.getPhotos().setPhoto1(this.photo1);
            if (this.photo2 != null) pet.getPhotos().setPhoto2(this.photo2);
            if (this.photo3 != null) pet.getPhotos().setPhoto3(this.photo3);
        }
        
        if (this.contactOption != null) pet.setContactOption(this.contactOption);
        if (this.available != null) pet.setAvailable(this.available);
    }
}
