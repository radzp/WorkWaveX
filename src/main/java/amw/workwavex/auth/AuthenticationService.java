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

    //injecting the user repository
    private final UserRepository repository;
    //injecting the password encoder
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
                .role(Role.valueOf(request.getRole()))
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
    //user is authenticated, so I generate a token and send it back
    var user = repository.findByEmail(request.getEmail())
            .orElseThrow(); // handle the exception later
    var jwtToken = jwtService.generateToken(user);

    // Get JWT expiration time
    Date jwtIssuedAt = jwtService.extractClaim(jwtToken, Claims::getIssuedAt);
    Date jwtExpiration = jwtService.extractClaim(jwtToken, Claims::getExpiration);
    long jwtLifetimeInSeconds = (jwtExpiration.getTime() - jwtIssuedAt.getTime()) / 1000;

    // Create new cookie
    Cookie jwtCookie = new Cookie("jwtToken", jwtToken);
    jwtCookie.setHttpOnly(true); // Set the cookie as HttpOnly to increase security, preventing access to the cookie via client-side scripts
    jwtCookie.setMaxAge((int)jwtLifetimeInSeconds); // Set the cookie expiration time (the amount of time until the cookie expires) in seconds
    jwtCookie.setPath("/"); // Set the cookie's path to "/", meaning the cookie will be available for the entire site

    // Add the cookie to the HTTP response
    response.addCookie(jwtCookie);

    return AuthenticationResponse.builder()
            .token(jwtToken)
            .build();
}
}

