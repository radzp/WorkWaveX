package amw.workwavex.config;

import amw.workwavex.user.Role;
import amw.workwavex.user.User;
import amw.workwavex.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            if (userRepository.findByEmail("admin@admin.com").isEmpty()) {
                User admin = User.builder()
                        .firstName("Admin")
                        .lastName("Admin")
                        .email("admin@admin.com")
                        .password(passwordEncoder.encode("admin"))
                        .position("Administrator")
                        .fullPhoneNumber("123456789")
                        .role(Role.ADMIN)
                        .build();
                userRepository.save(admin);
            }
        };
    }
}
