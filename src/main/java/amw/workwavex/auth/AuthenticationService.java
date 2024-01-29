package amw.workwavex.auth;

import amw.workwavex.config.JwtService;
import amw.workwavex.user.Role;
import amw.workwavex.user.User;
import amw.workwavex.user.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .position(request.getPosition())
                .salary(request.getSalary())
                .fullPhoneNumber(request.getFullPhoneNumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

public AuthenticationResponse authenticate(AuthenticationRequest request, HttpServletResponse response) {
    authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword()
            )
    );
    // użytkownik jest uwierzytelniony, więc generuję token i wysyłam go z powrotem
    var user = repository.findByEmail(request.getEmail())
            .orElseThrow(); // handle the exception later
    var jwtToken = jwtService.generateToken(user);

    // Pobierz czas wygaśnięcia JWT
    Date jwtIssuedAt = jwtService.extractClaim(jwtToken, Claims::getIssuedAt);
    Date jwtExpiration = jwtService.extractClaim(jwtToken, Claims::getExpiration);
    long jwtLifetimeInSeconds = (jwtExpiration.getTime() - jwtIssuedAt.getTime()) / 1000;

    // Utwórz nowe ciasteczko
    Cookie jwtCookie = new Cookie("jwtToken", jwtToken);
    jwtCookie.setHttpOnly(true); // Ustaw ciasteczko jako HttpOnly, aby zwiększyć bezpieczeństwo, uniemożliwiając dostęp do ciasteczka za pomocą skryptów po stronie klienta
    jwtCookie.setMaxAge((int)jwtLifetimeInSeconds); // Ustaw czas wygaśnięcia ciasteczka (ilość czasu do wygaśnięcia ciasteczka) w sekundach
    jwtCookie.setPath("/"); // Ustaw ścieżkę ciasteczka na "/", co oznacza, że ciasteczko będzie dostępne dla całej strony

    // Dodaj ciasteczko do odpowiedzi HTTP
    response.addCookie(jwtCookie);

    return AuthenticationResponse.builder()
            .token(jwtToken)
            .build();
}
}

