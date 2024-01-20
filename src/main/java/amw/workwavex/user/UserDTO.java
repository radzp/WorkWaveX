package amw.workwavex.user;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class UserDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String position;
    private Double salary;
    private String fullPhoneNumber;
}
