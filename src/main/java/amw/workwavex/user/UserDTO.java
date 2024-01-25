package amw.workwavex.user;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class UserDTO {
    private Integer id;

    @NotBlank(message = "First name is mandatory")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    private String lastName;

    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Position is mandatory")
    private String position;

    @Positive(message = "Salary should be positive")
    private Double salary;

    @NotBlank(message = "Phone number is mandatory")
    private String fullPhoneNumber;

    private String role;
}