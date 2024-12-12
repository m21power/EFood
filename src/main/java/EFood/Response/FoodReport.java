package EFood.Response;

public class FoodReport {
    private String foodName;
    private Integer quantity;
    private double price;

    public FoodReport(String foodName, Integer quantity, double price) {
        this.foodName = foodName;
        this.quantity = quantity;
        this.price = price;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
