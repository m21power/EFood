package EFood.controllers;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.language.bm.Rule.Phoneme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import EFood.Payloads.userPayload;
import EFood.config.ApiResponse;
import EFood.models.UserModel;
import EFood.services.AuthenticationService;
import EFood.services.JwtService;
import EFood.services.UserService;
import EFood.utils.CloudinaryService;
import EFood.utils.UserModelResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private CloudinaryService cloudinaryService;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private JwtService jwtService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/admin")
    public ResponseEntity<?> registerAdmin(
            @RequestParam(value = "logo") MultipartFile logo,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "phoneNumber") String phoneNumber,
            @RequestParam(value = "password") String password,
            HttpServletResponse response

    ) {
        try {
            var user = new UserModel();
            if (logo != null) {
                var image_url = cloudinaryService.uploadFile(logo);
                user.setLogoUrl(image_url);
            }
            if (password != "") {
                user.setPassword(password);
            }
            if (phoneNumber != "") {
                user.setPhoneNumber(phoneNumber);
            }
            if (name != "") {
                user.setName(name);
            }

            user.setRole("ROLE_ADMIN");

            var result = userService.registerAdmin(user);
            if (phoneNumber != "") {
                authenticationService.setCookie(result, response);
            }
            return ResponseEntity.ok(new ApiResponse("Registered Successful", true,
                    result));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(new ApiResponse(e.getMessage(), false, null));
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/admin")
    public ResponseEntity<?> updateAdmin(
            @RequestParam(value = "logo", required = false) MultipartFile logo,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
            @RequestParam(value = "password", required = false) String password,
            HttpServletResponse response,
            HttpServletRequest request) {
        Long adminId = getUserId(request);
        if (adminId == 0) {
            return ResponseEntity.status(404).body(new ApiResponse("user not found", false, null));
        }
        var user = new UserModel();
        if (logo != null) {
            var image_url = cloudinaryService.uploadFile(logo);
            user.setLogoUrl(image_url);
        }
        if (password != "") {
            user.setPassword(password);
        }
        if (phoneNumber != "") {
            user.setPhoneNumber(phoneNumber);
        }
        if (name != "") {
            user.setName(name);
        }
        user.setRole("ROLE_ADMIN");

        var result = userService.updateUser(adminId, user);
        if (phoneNumber != "") {
            authenticationService.setCookie(result, response);
        }
        return ResponseEntity.ok(new ApiResponse("user found", true, toUserModelResponse(result)));

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserByID(@PathVariable Long id) {
        var result = userService.getUserByID(id);

        if (result.isPresent()) {
            return ResponseEntity.ok(new ApiResponse("user found", true, toUserModelResponse(result.get())));
        } else {
            return ResponseEntity.status(404).body(new ApiResponse("user not found", false, null));
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<?> getAllUser() {
        var result = userService.getAllUser();

        if (!result.isEmpty()) {
            List<UserModelResponse> response = new ArrayList<>();
            for (UserModel user : result) {
                response.add(toUserModelResponse(user));
            }
            return ResponseEntity.ok(new ApiResponse("users found", true, response));
        } else {
            return ResponseEntity.ok(new ApiResponse("no user found", false, null));
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/phone")
    public ResponseEntity<?> getByPhoneNumber(@RequestParam("PhoneNumber") String phoneNumber) {
        var result = userService.findByPhoneNumber(phoneNumber);
        try {
            if (result.isPresent()) {
                return ResponseEntity.ok(new ApiResponse("user found", true, toUserModelResponse(result.get())));
            } else {
                return ResponseEntity.ok(new ApiResponse("user not found", false, null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ApiResponse(e.getMessage(), false, null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody userPayload user,
            HttpServletResponse response, HttpServletRequest request) {
        try {
            if (!isAuthenticated(request, id)) {
                return ResponseEntity.status(401)
                        .body(new ApiResponse("You are not authorized to update", false, null));

            }
            var User = toUserModel(user);
            var result = userService.updateUser(id, User);
            if (User.getPhoneNumber() != "") {
                authenticationService.setCookie(result, response);
            }
            // System.out.println(result);
            return ResponseEntity.ok(new ApiResponse("Updating successfull", true, toUserModelResponse(result)));
        }

        catch (Exception e) {
            return ResponseEntity.status(404).body(new ApiResponse(e.getMessage(), false, null));
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id, HttpServletRequest request) {
        try {
            if (!isAuthenticated(request, id)) {
                return ResponseEntity.status(401)
                        .body(new ApiResponse("You are not authorized to delete", false, null));

            }
            userService.deleteUser(id);
            return ResponseEntity.ok(new ApiResponse("delete successfully", true, null));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(new ApiResponse(e.getMessage(), false, null));
        }

    }

    public UserModel toUserModel(userPayload user) {
        UserModel u = new UserModel();
        u.setName(user.getName());
        u.setPassword(user.getPassword());
        u.setPhoneNumber(user.getPhoneNumber());
        u.setLogoUrl(user.getLogoUrl());
        return u;
    }

    public UserModelResponse toUserModelResponse(UserModel user) {
        UserModelResponse um = new UserModelResponse(user.getId(), user.getName(), user.getPhoneNumber(),
                user.getPassword(), user.getLogoUrl(), user.getRole(), user.getCreatedAt());
        return um;
    }

    public Boolean isAuthenticated(HttpServletRequest request, Long id) {
        Cookie[] cookies = request.getCookies();
        // Find the token in the cookies
        String token = null;
        for (Cookie cookie : cookies) {
            if ("auth_token".equals(cookie.getName())) {
                token = cookie.getValue();
                break;
            }
        }
        if (token == null) {
            return false;
        }

        String phoneNumber = jwtService.extractUsername(token);
        var oldUser = userService.findByPhoneNumber(phoneNumber);
        return oldUser.get().getId() == id;
    }

    public Long getUserId(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        // Find the token in the cookies
        String token = null;
        for (Cookie cookie : cookies) {
            if ("auth_token".equals(cookie.getName())) {
                token = cookie.getValue();
                break;
            }
        }
        if (token == null) {
            return 0L;
        }

        String phoneNumber = jwtService.extractUsername(token);
        var oldUser = userService.findByPhoneNumber(phoneNumber);
        return oldUser.get().getId();
    }
}
