package EFood.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import EFood.Payloads.loginPayload;
import EFood.Payloads.signUpPayload;
import EFood.models.UserModel;
import EFood.repositories.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;

    public UserModel signUp(signUpPayload payload) {
        var rawPassword = payload.getPassword();
        var hashedPassword = passwordEncoder.encode(rawPassword);
        payload.setPassword(hashedPassword);
        UserModel user = new UserModel();
        user.setName(payload.getName());
        user.setPassword(payload.getPassword());
        user.setPhoneNumber(payload.getPhoneNumber());
        return userRepository.save(user);
    }

    public void authenticate(loginPayload input, HttpServletResponse response) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(input.getPhoneNumber(), input.getPassword()));

        UserModel user = userRepository.findByPhoneNumber(input.getPhoneNumber())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Generate the JWT
        String token = jwtService.generateToken(user, user.getRole());
        // Create a cookie with the token
        Cookie jwtCookie = new Cookie("auth_token", token);
        jwtCookie.setHttpOnly(true); // Prevent access via JavaScript
        jwtCookie.setSecure(true); // Use only over HTTPS
        jwtCookie.setPath("/"); // Cookie accessible to all endpoints
        jwtCookie.setMaxAge(30 * 24 * 60 * 60); // Expiry in seconds (30 days)

        // Add the cookie to the response
        response.addCookie(jwtCookie);
    }
}