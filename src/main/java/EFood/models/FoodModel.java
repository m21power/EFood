package EFood.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class FoodModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;
    @Column(nullable = false)
    private String imageUrl;
    @Column(nullable = false)
    private double price;
    private Boolean isAvailable = true;

    public FoodModel(String name, String description, String imageUrl, double price) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.price = price;
        this.isAvailable = true;
    }

    public FoodModel() {

    }

    public FoodModel(String name, String description, String imageUrl, double price, Boolean isAvailable) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.price = price;
        this.isAvailable = isAvailable;
    }

    public void setName(String name) {
        this.name = name;

    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageUrl(String image_url) {
        this.imageUrl = image_url;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public double getPrice() {
        return this.price;
    }

    public Boolean getIsAvailable() {
        return this.isAvailable;
    }

    @Override
    public String toString() {
        return "FoodModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", price=" + price +
                ", isAvailable=" + isAvailable +
                '}';
    }
}
