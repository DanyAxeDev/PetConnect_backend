package br.com.petConnect.backend.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_pet")
@IdClass(UserPetId.class)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"user", "pet"})
@Getter
@Setter
public class UserPet {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
