package EFood.config;

public class ApiResponse {
    private String message;
    private boolean success;
    private Object data;

    public ApiResponse(String message, boolean success, Object data) {
        this.message = message;
        this.data = data;
        this.success = success;
    }

    public ApiResponse() {
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return this.message;
    }

    public boolean getSuccess() {
        return this.success;
    }

    public Object getData() {
        return this.data;
    }
}
