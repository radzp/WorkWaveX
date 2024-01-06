package amw.workwavex.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor //it will create a constructor with all required fields for final fields

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService; // Spring Security interface to retrieve user-related data

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
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) //when the authentication is null that means that the user is not yet authenticated, the user is not connected yet
        {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail); //we will load the user details from the database using the username (email)
            if(jwtService.isTokenValid(jwt, userDetails)) //we will check if the JWT token is valid
            {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken( //we will create the authentication token
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken); //we will set the authentication token in the security context
            }
        }

        filterChain.doFilter(request, response); //always calling the filterChain.doFilter(request, response) method to continue the filter chain

    }
}
