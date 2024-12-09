package EFood.controllers;

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

import EFood.config.ApiResponse;
import EFood.models.UserModel;
import EFood.services.UserService;
import EFood.utils.CloudinaryService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private CloudinaryService cloudinaryService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/admin")
    public ResponseEntity<?> registerAdmin(
            @RequestParam("logo") MultipartFile logo,
            @RequestParam("name") String name,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam("password") String password

    ) {
        try {
            var image_url = cloudinaryService.uploadFile(logo);
            var user = new UserModel();
            user.setLogoUrl(image_url);
            user.setPassword(password);
            user.setPhoneNumber(phoneNumber);
            user.setName(name);

            var result = userService.registerAdmin(user);
            return ResponseEntity.ok(new ApiResponse("Registered Successful", true,
                    result));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(new ApiResponse(e.getMessage(), false, null));
        }
    }

    // @PostMapping
    // public ResponseEntity<?> signUp(@RequestBody UserModel user) {
    // try {
    // var result = userService.signUp(user);
    // return ResponseEntity.ok(new ApiResponse("Registered Successful", true,
    // result));
    // } catch (Exception e) {
    // return ResponseEntity.status(404).body(new ApiResponse(e.getMessage(), false,
    // null));
    // }
    // }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserByID(@PathVariable Long id) {
        var result = userService.getUserByID(id);
        if (result.isPresent()) {
            return ResponseEntity.ok(new ApiResponse("user found", true, result));
        } else {
            return ResponseEntity.ok(new ApiResponse("user not found", false, null));
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<?> getAllUser() {
        var result = userService.getAllUser();
        if (!result.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse("users found", true, result));
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
                return ResponseEntity.ok(new ApiResponse("user found", true, result));
            } else {
                return ResponseEntity.ok(new ApiResponse("user not found", false, null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ApiResponse(e.getMessage(), false, null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserModel user) {
        try {
            var result = userService.updateUser(id, user);
            return ResponseEntity.ok(new ApiResponse("Updating successfull", true, result));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(new ApiResponse(e.getMessage(), false, null));

        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok(new ApiResponse("delete successfully", true, null));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(new ApiResponse(e.getMessage(), false, null));
        }

    }
}
