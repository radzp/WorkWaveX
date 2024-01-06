package amw.workwavex.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data   // Lombok annotation to create all the getters, setters, equals, hash, and toString methods, based on the fields
@Builder // Lombok annotation to create builder API for the class
@NoArgsConstructor // Lombok annotation to create constructor without parameters
@AllArgsConstructor // Lombok annotation to create constructor with all of the parameters
@Entity // JPA annotation to make this object ready for storage in a JPA-based data store
@Table(name= "_user") // JPA annotation to specify the table name (if not specified, the class name will be used as the table name
public class User implements UserDetails {


    @Id   // JPA annotation to specify the primary key of an entity
    @GeneratedValue // JPA annotation to specify the primary key generation strategy to use
    private Integer id;
    private String firstName;
    private String secondName;
    private String lastName;
    private String email;
    private String position;
    private String hours;
    private String salary;
    private String password;

    @Enumerated(EnumType.STRING) // JPA annotation to specify the type of the enum
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name())); // Spring Security interface to represent an authority granted to an Authentication object
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email; //logowanie za pomoca emaila
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
}
