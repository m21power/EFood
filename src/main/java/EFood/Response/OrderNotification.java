package EFood.Response;

public class OrderNotification {
    private String message;
    private Long order_id;
    private String status;
    public OrderNotification(String message, Long order_id, String status) {
        this.message = message;
        this.order_id = order_id;
        this.status = status;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Long getOrder_id() {
        return order_id;
    }
    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

}
