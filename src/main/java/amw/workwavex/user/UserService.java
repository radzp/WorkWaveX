package amw.workwavex.user;

import jakarta.persistence.GenerationType;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<UserDTO> getAllUserDTOs() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return convertToDTO(user);
    }

    public UserDTO getUserById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return convertToDTO(user);
    }

    public UserDTO getUserByNameAndSurname(String firstName, String lastName) {
        User user = userRepository.findByFirstNameAndLastName(firstName, lastName)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return convertToDTO(user);
    }

    public UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPosition(user.getPosition());
        userDTO.setSalary(user.getSalary());
        userDTO.setFullPhoneNumber(user.getFullPhoneNumber());
        userDTO.setRole(user.getRole().name());
        return userDTO;
    }

    @Transactional
    public UserDTO updateUser(Integer id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPosition(userDTO.getPosition());
        user.setEmail(userDTO.getEmail());
        user.setFullPhoneNumber(userDTO.getFullPhoneNumber());
        user.setSalary(userDTO.getSalary());

        User updatedUser = userRepository.save(user);

        return convertToDTO(updatedUser);
    }

    public void deleteUserById(Integer id) {
        userRepository.deleteById(id);
    }


    @Transactional
    public ResponseEntity<?> registerNewUser(User newUser) {
        if (userRepository.findByEmail(newUser.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Cannot create user. User with this email already exists.");
        }
        User registeredUser = userRepository.save(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    }
}