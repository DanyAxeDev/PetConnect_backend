package br.com.petConnect.backend.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "pet")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
public class Pet {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String species;
    
    @Column(nullable = false)
    private String gender;
    
    private LocalDate birthDate;
    private String size;
    private String castrationReceipt;
    private String vaccinationReceipt;
    
    @Embedded
    private Address address;
    
    private String health;
    private String about;
    
    @Embedded
    private Photos photos;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;
    
    @OneToOne(mappedBy = "pet", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Personality personality;
    
    private String contactOption;
    private Boolean available = true;

    @Embeddable
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class Photos {
        private String photo1;
        private String photo2;
        private String photo3;
    }
}
