package EFood.utils;

import java.util.List;

import EFood.models.OrderItemModel;

public class UpdateOrderRequest {
    private List<OrderItemModel> items;

    public UpdateOrderRequest(List<OrderItemModel> items) {
        this.items = items;
    }

    public UpdateOrderRequest() {
    }

    public List<OrderItemModel> getItems() {
        return items;
    }

    public void setItems(List<OrderItemModel> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "UpdateOrderRequest{" +
                "items=" + items +
                '}';
    }
}
