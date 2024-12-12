package EFood.Response;

import java.util.List;

public class OrderReportResponse {
    private double totalPrice;
    List<FoodReport> foodReport;

    public OrderReportResponse(double totalPrice, List<FoodReport> foodReport) {
        this.totalPrice = totalPrice;
        this.foodReport = foodReport;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<FoodReport> getFoodReport() {
        return foodReport;
    }

    public void setFoodReport(List<FoodReport> foodReport) {
        this.foodReport = foodReport;
    }

}
