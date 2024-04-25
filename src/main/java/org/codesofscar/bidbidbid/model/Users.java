package org.codesofscar.bidbidbid.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.codesofscar.bidbidbid.enums.Roles;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class Users implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    @JsonIgnore
    private Long id;

    @Size(min = 3, message = "First and Last Name")
    @Column(unique = true, nullable = false)
    private String username;

    @Email(message = "Please enter a valid email address")
    @NotEmpty(message = "Email cannot be empty")
    @Column(unique = true)
    private String email;

    @JsonIgnore
    private String bidId;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$", message = "Password must be at least 8 characters and include one uppercase letter, one lowercase letter, one digit, and one special character")
    private String password;

    @JsonIgnore
    private Roles userRole;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }



    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    /* POSSIBLE CSV CONFIG
id,name,email,password,bid_id
1,John Doe,johndoe@example.com,password123,"1:1:150.00:1:ACCEPTED,2:1:200.00:1:PENDING"
2,Jane Smith,janesmith@example.com,password456,"3:2:180.00:2:REJECTED,4:2:220.00:2:ACCEPTED"
*/
}
