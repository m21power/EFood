package EFood.utils;

import EFood.models.FoodModel;

public class OrderItemResponse {
    private FoodModel food;
    private Integer quantity;

    public OrderItemResponse() {
    }

    public OrderItemResponse(FoodModel food, Integer quantity) {
        this.food = food;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderResponse{" +
                "food=" + food +
                ", quantity=" + quantity +
                '}';
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public FoodModel getFood() {
        return food;
    }

    public void setFood(FoodModel food) {
        this.food = food;
    }
}
