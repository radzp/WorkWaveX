package amw.workwavex.user;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    void getAllUserDTOs() {
        //given
        User user1 = new User();
        user1.setId(1);
        user1.setFirstName("Jan");
        user1.setLastName("Kowalski");
        user1.setRole(Role.ADMIN);

        User user2 = new User();
        user2.setId(2);
        user2.setFirstName("Adam");
        user2.setLastName("Nowak");
        user2.setRole(Role.USER);

        List<User> allUser = List.of(user1, user2);
        when(userRepository.findAll()).thenReturn(allUser);

        //when
        List<UserDTO> result = userService.getAllUserDTOs();

        //then
        assertEquals(2, result.size());
        assertEquals("Jan", result.get(0).getFirstName());
        assertEquals("Kowalski", result.get(0).getLastName());

    }

    @Test
    void getUserByEmail() {
        User user1 = new User();
        user1.setId(1);
        user1.setFirstName("Jan");
        user1.setLastName("Kowalski");
        user1.setRole(Role.ADMIN);
        String email = "janKowalski@gmail.com";
        user1.setEmail(email);

        when(userRepository.findByEmail(email)).thenReturn(java.util.Optional.of(user1));

        //when
        UserDTO result = userService.getUserByEmail(email);

        //then
        assertEquals(email, result.getEmail());
    }

    @Test
    void getUserById() {
        User user1 = new User();
        user1.setId(1);
        user1.setFirstName("Jan");
        user1.setLastName("Kowalski");
        user1.setRole(Role.ADMIN);

        when(userRepository.findById(1)).thenReturn(java.util.Optional.of(user1));

        //when
        UserDTO result = userService.getUserById(1);

        //then
        assertEquals(1, result.getId());
    }
}