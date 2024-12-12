package EFood.utils;

import java.util.List;

import EFood.models.OrderItemModel;

public class Order {
    public Order() {
    }

    public Order(List<OrderItemModel> orderItems) {
        this.orderItems = orderItems;
    }

    private List<OrderItemModel> orderItems;

    public List<OrderItemModel> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemModel> orderItems) {
        this.orderItems = orderItems;
    }

}