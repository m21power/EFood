package EFood.utils;

import java.util.Date;

public class UserModelResponse {
    public UserModelResponse(Long id, String name, String phoneNumber, String password, String logoUrl, String role,
            Date createdAt) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.logoUrl = logoUrl;
        this.role = role;
        this.createdAt = createdAt;
    }

    private Long id;

    private String name;

    private String phoneNumber;

    private String password;

    private String logoUrl = "";
    private String role;
    private Date createdAt;

    public UserModelResponse() {
    }

    public UserModelResponse(Long id, String name, String phoneNumber, String password, String logoUrl,
            Date createdAt) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.logoUrl = logoUrl;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
