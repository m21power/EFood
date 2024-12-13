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
import EFood.utils.UserModelResponse;
import jakarta.servlet.http.HttpServletRequest;
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
            UserModelResponse um = new UserModelResponse(result.getId(), result.getName(), result.getPhoneNumber(),
                    result.getPassword(), result.getLogoUrl(), result.getRole(), result.getCreatedAt());
            return ResponseEntity.ok(new ApiResponse("Registered Successful", true, um));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(new ApiResponse(e.getMessage(), false, null));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody loginPayload loginDto, HttpServletResponse response,
            HttpServletRequest request) {
        // throw new AccessDeniedException("here");
        authenticationService.authenticate(loginDto, response, request);
        return ResponseEntity.ok(new ApiResponse("Logged in successfully", true, null));
    }

}
