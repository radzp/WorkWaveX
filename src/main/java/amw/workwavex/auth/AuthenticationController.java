package amw.workwavex.auth;

// class that will allow me to login and register new account and authenticate an existing user

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jdk.jfr.Registered;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;


    //endpoint for registering a new user
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register (
            @RequestBody RegisterRequest request
            ) {
        return ResponseEntity.ok(service.register(request)); //returning a response entity with a body of the authentication response
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request,
            HttpServletResponse response
    ) {
        AuthenticationResponse authResponse = service.authenticate(request);

        // Utwórz ciasteczko z tokenem JWT
        Cookie jwtCookie = new Cookie("jwtToken", authResponse.getToken());
        jwtCookie.setHttpOnly(true);
        jwtCookie.setPath("/");
        //jwtCookie.setSecure(true); // Ustaw na true, jeśli używasz HTTPS

        // Dodaj ciasteczko do odpowiedzi
        response.addCookie(jwtCookie);

        return ResponseEntity.ok(authResponse);
    }

}

