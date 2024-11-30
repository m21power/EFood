package EFood.utils;

import java.time.LocalDateTime;
import java.util.List;

public class OrderResponse {
    private Long id;
    private String status;
    private double totalPrice;
    private List<OrderItemResponse> items;
    private LocalDateTime createdAt;

    public OrderResponse(Long id, String status, LocalDateTime createdAt, double totalPrice,
            List<OrderItemResponse> items) {
        this.id = id;
        this.status = status;
        this.createdAt = createdAt;
        this.items = items;
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "OrderResponse{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", totalPrice=" + totalPrice +
                ", items=" + items +
                ", createdAt=" + createdAt +
                '}';
    }

    public void setTotalPrice(double price) {
        this.totalPrice = price;
    }

    public double getTotalPrice() {
        return this.totalPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<OrderItemResponse> getItems() {
        return items;
    }

    public void setItems(List<OrderItemResponse> items) {
        this.items = items;
    }

    public OrderResponse() {
    }
}
