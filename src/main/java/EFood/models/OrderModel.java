package EFood.models;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class OrderModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;
    @Column(nullable = false)
    private Long userId;
    private String status;
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // Allows serialization of the 'parent' side (the list of order items)
    private List<OrderItemModel> orderItem;

    public OrderModel() {
    }

    public OrderModel(Long userId, String status, LocalDateTime createdAt, List<OrderItemModel> orderItem) {
        this.userId = userId;
        this.status = status;
        this.createdAt = createdAt;
        this.orderItem = orderItem;
    }

    public OrderModel(Long userId, List<OrderItemModel> orderItem) {
        this.userId = userId;
        this.orderItem = orderItem;
        this.status = "Pending";
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public List<OrderItemModel> getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(List<OrderItemModel> orderItem) {
        this.orderItem = orderItem;
    }

    @Override
    public String toString() {
        return "OrderModel{" +
                "id=" + id +
                ", userId=" + userId +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                ", orderItem=" + orderItem +
                '}';
    }

}
