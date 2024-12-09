package EFood.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import EFood.Payloads.loginPayload;
import EFood.Payloads.signUpPayload;
import EFood.config.ApiResponse;
import EFood.services.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;

@RequestMapping("/auth")
@RestController
public class AuthController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody signUpPayload user) {
        try {
            var result = authenticationService.signUp(user);
            return ResponseEntity.ok(new ApiResponse("Registered Successful", true, result));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(new ApiResponse(e.getMessage(), false, null));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody loginPayload loginDto, HttpServletResponse response) {
        // throw new AccessDeniedException("here");
        authenticationService.authenticate(loginDto, response);
        return ResponseEntity.ok(new ApiResponse("Logged in successfully", true, null));
    }

}
