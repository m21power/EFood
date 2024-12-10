package EFood.Payloads;

public class userPayload {
    public userPayload(String name, String phoneNumber, String password, String role, String logoUrl) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.logoUrl = logoUrl;
    }

    private String name;

    private String phoneNumber;

    private String password;

    private String logoUrl = "";

    public userPayload() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

}
