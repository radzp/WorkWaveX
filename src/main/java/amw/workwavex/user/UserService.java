package amw.workwavex.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public List<UserDTO> getAllUserDTOs() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> {
                    UserDTO UserDTO = new UserDTO();
                    UserDTO.setId(user.getId());
                    UserDTO.setFirstName(user.getFirstName());
                    UserDTO.setLastName(user.getLastName());
                    UserDTO.setEmail(user.getEmail());
                    UserDTO.setPosition(user.getPosition());
                    UserDTO.setSalary(user.getSalary());
                    UserDTO.setFullPhoneNumber(user.getFullPhoneNumber());
                    return UserDTO;
                })
                .collect(Collectors.toList());
    }

}
