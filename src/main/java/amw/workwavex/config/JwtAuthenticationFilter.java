package amw.workwavex.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor //it will create a constructor with all required fields for final fields

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization"); //get the header from the request, that is the header that contains the JWT token or bearer token
        final String jwt;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) { //if the header is null or does not start with Bearer, then we will not do anything
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(7); //if the header is not null and starts with Bearer, then we will extract the JWT token from the header
        userEmail = jwtService.extractUsername(jwt); //we will extract the username from the JWT token
        //TODO: 1:13:10

    }
}
