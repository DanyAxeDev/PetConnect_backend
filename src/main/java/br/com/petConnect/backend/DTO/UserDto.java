package br.com.petConnect.backend.DTO;

import br.com.petConnect.backend.Model.Address;
import br.com.petConnect.backend.Model.Profile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String phone;
    private Address address;
    private String email;
    private List<Profile> perfis;
}
