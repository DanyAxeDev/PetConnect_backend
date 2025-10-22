package br.com.petConnect.backend.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPetId implements Serializable {
    private Long user;
    private Long pet;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPetId userPetId = (UserPetId) o;
        return Objects.equals(user, userPetId.user) && Objects.equals(pet, userPetId.pet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, pet);
    }
}
