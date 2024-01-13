package amw.workwavex.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

// Adnotacja @Component mówi Springowi, że ta klasa jest komponentem Springa
// i powinna być automatycznie zainstancjonowana jako bean
@Component
// Adnotacja @RequiredArgsConstructor generuje konstruktor z wymaganymi argumentami
// dla wszystkich finalnych pól (w tym przypadku JwtService i UserDetailsService)
@RequiredArgsConstructor
// Klasa JwtAuthenticationFilter rozszerza OncePerRequestFilter, co oznacza, że jest to filtr,
// który jest wywoływany raz na żądanie
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // Wstrzyknięcie zależności JwtService i UserDetailsService
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    // Metoda doFilterInternal jest wywoływana dla każdego żądania
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        // Pobierz ciasteczko z tokenem JWT
        Cookie jwtCookie = Arrays.stream(request.getCookies())
                .filter(cookie -> "jwtToken".equals(cookie.getName()))
                .findFirst()
                .orElse(null);


        String jwt = null;
        String userEmail = null;

        // Jeśli ciasteczko "jwtToken" istnieje, kod odczytuje token JWT i nazwę użytkownika z tokena
        if (jwtCookie != null) {
            jwt = jwtCookie.getValue();
            userEmail = jwtService.extractUsername(jwt);
        }

        // Jeśli nazwa użytkownika istnieje i użytkownik nie jest jeszcze uwierzytelniony,
        // kod ładuje szczegóły użytkownika, weryfikuje token JWT i ustawia kontekst bezpieczeństwa
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Na końcu, filtr przekazuje żądanie dalej do następnego filtra w łańcuchu filtrów
        filterChain.doFilter(request, response);
    }
}