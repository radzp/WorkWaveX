package amw.workwavex.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String position;
    private Double salary;
    private String fullPhoneNumber;
}
