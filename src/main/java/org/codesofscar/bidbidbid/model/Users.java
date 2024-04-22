package org.codesofscar.bidbidbid.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    @JsonIgnore
    private Long id;

    @Size(min = 3, message = "First name must be at least 3 characters")
    @Column(unique = true, nullable = false)
    private String name;

    @Email(message = "Please enter a valid email address")
    @NotEmpty(message = "Email cannot be empty")
    @Column(unique = true)
    private String email;

    @OneToOne
    private Bids bid;
}
