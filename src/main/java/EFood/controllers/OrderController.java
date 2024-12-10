package EFood.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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

import EFood.config.ApiResponse;
import EFood.services.JwtService;
import EFood.services.OrderService;
import EFood.services.UserService;
import EFood.utils.OrderRequest;
import EFood.utils.UpdateOrderRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest orderRequest, HttpServletRequest request) {
        try {
            if (!isAuthenticated(request, orderRequest.getUserId())) {
                return ResponseEntity.status(401).body(new ApiResponse("Authentication required", false, null));
            }
            var result = orderService.createOrder(orderRequest.getUserId(), orderRequest.getOrderItems());
            return ResponseEntity.ok(new ApiResponse("Ordered successfully", true, result));
        } catch (IllegalArgumentException e) {
            // Handle bad requests
            return ResponseEntity.status(400).body(new ApiResponse(e.getMessage(), false, null));
        } catch (Exception e) {
            // Catch unexpected errors and return Internal Server Error
            return ResponseEntity.status(500).body(new ApiResponse("Internal server error occurred", false, null));
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping() // based on user id
    public ResponseEntity<?> getOrderByUserId(@RequestParam Long userId) {
        try {
            var result = orderService.getOrderByUserId(userId);
            return ResponseEntity.ok(new ApiResponse("your order", true, result));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), false, null));
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}") // based on order id
    public ResponseEntity<?> getOrderById(@PathVariable Long id) {
        try {
            var result = orderService.getOrderById(id);
            return ResponseEntity.ok(new ApiResponse("your order", true, result));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), false, null));
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/status/{id}") // order id
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestParam String status) {
        try {
            var order = orderService.updateStatus(id, status);
            // Broadcast the update to the subscribed clients
            messagingTemplate.convertAndSend("/topic/orders/" + id, status);
            return ResponseEntity.ok(new ApiResponse("status updated successfully", true, order));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), false, null));
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable Long id, @RequestBody UpdateOrderRequest updateRequest,
            HttpServletRequest request) {
        try {
            var result = orderService.updateOrder(id, updateRequest);
            return ResponseEntity.ok(new ApiResponse("updated successfully", true,
                    result));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(),
                    false, null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
        try {
            orderService.deleteOrder(id);
            return ResponseEntity.ok(new ApiResponse("delted successfully", true, null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), false, null));
        }

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
