package EFood.utils;

import java.util.List;

import EFood.models.OrderItemModel;

public class OrderRequest {
    private Long userId;
    private List<OrderItemModel> orderItems;

    public OrderRequest(Long userId, List<OrderItemModel> orderItems) {
        this.userId = userId;
        this.orderItems = orderItems;
    }

    public OrderRequest() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<OrderItemModel> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemModel> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public String toString() {
        return "OrderRequest{" +
                "userId=" + userId +
                ", orderItems=" + orderItems +
                '}';
    }
}
