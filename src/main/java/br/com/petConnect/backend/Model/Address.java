package br.com.petConnect.backend.Model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Embeddable
public class Address {
    private String street;
    private Integer number;
    private String neighborhood;
    private String city;
    private String state;
}
