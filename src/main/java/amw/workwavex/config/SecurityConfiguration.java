package amw.workwavex.config;

import amw.workwavex.user.Role;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity   // Spring annotation to indicate that the class contains the Spring Security configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;  //automatically injected
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeRequests(authorize -> authorize
                        .requestMatchers("/api/v1/auth/**")
                        .permitAll()
                        .requestMatchers("/", "","/css/**", "/js/**", "/images/**")
                        .permitAll()
                        //.requestMatchers("employees","newLogin").hasRole(Role.ADMIN.name())
                        .anyRequest()
                        .permitAll()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .logout((logout) -> logout
                        .logoutSuccessUrl("/")
                        .permitAll())
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
