package amw.workwavex.auth;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.aspectj.bridge.MessageUtil.fail;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthenticationServiceIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void authenticate() {
        // given
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstName("John");
        registerRequest.setLastName("Doe");
        registerRequest.setEmail("john.doe@example.com");
        registerRequest.setPosition("Developer");
        registerRequest.setSalary(5000.0);
        registerRequest.setFullPhoneNumber("123456789");
        registerRequest.setPassword("password");
        registerRequest.setRole("USER");

        ResponseEntity<AuthenticationResponse> registerResponse = restTemplate.postForEntity("http://localhost:" + port + "/register", registerRequest, AuthenticationResponse.class);

        // Get the cookie from the register response
        String jwtToken = registerResponse.getHeaders().getFirst("Set-Cookie");

        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail("john.doe@example.com");
        request.setPassword("password");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", jwtToken);

        HttpEntity<AuthenticationRequest> entity = new HttpEntity<>(request, headers);

        // when
        ResponseEntity<AuthenticationResponse> response = restTemplate.postForEntity("http://localhost:" + port + "/authenticate", entity, AuthenticationResponse.class);

        // then
        if (response.getStatusCode() != HttpStatus.OK) {
            fail("Expected 200 OK but got " + response.getStatusCode());
        }
        if (response.getBody() != null) {
            assertNotNull(response.getBody().getToken());
        } else {
            fail("Response body is null");
        }
    }

    @Test
    void register() {
        // given
        RegisterRequest request = new RegisterRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setEmail("john.doe@example.com");
        request.setPosition("Developer");
        request.setSalary(5000.0);
        request.setFullPhoneNumber("123456789");
        request.setPassword("password");
        request.setRole("USER");

        // when
        ResponseEntity<AuthenticationResponse> response = restTemplate.postForEntity("http://localhost:" + port + "/register", request, AuthenticationResponse.class);

        // then
        if (response.getStatusCode() != HttpStatus.OK) {
            fail("Expected 200 OK but got " + response.getStatusCode());
        }
        if (response.getBody() != null) {
            assertNotNull(response.getBody().getToken());
        } else {
            fail("Response body is null");
        }
    }

}