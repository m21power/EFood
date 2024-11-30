package EFood.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import EFood.models.OrderItemModel;
import EFood.models.OrderModel;
import EFood.services.OrderService;
import EFood.utils.OrderRequest;
import EFood.utils.UpdateOrderRequest;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest orderRequest) {
        try {
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

    @GetMapping() // based on user id
    public ResponseEntity<?> getOrderByUserId(@RequestParam Long userId) {
        try {
            var result = orderService.getOrderByUserId(userId);
            return ResponseEntity.ok(new ApiResponse("your order", true, result));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), false, null));
        }
    }

    @GetMapping("/{id}") // based on order id
    public ResponseEntity<?> getOrderById(@PathVariable Long id) {
        try {
            var result = orderService.getOrderById(id);
            return ResponseEntity.ok(new ApiResponse("your order", true, result));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), false, null));
        }
    }

    @PutMapping("/status/{id}") // order id
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestParam String status) {
        try {
            var order = orderService.updateStatus(id, status);
            return ResponseEntity.ok(new ApiResponse("status updated successfully", true, order));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), false, null));
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable Long id, @RequestBody UpdateOrderRequest updateRequest) {
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
}
