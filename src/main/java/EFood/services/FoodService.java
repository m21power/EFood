package EFood.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import EFood.models.FoodModel;
import EFood.repositories.FoodRespository;

@Service
public class FoodService {
    @Autowired
    private FoodRespository foodRespository;

    public FoodModel createFood(FoodModel food) {
        return foodRespository.save(food);
    }

    public Optional<FoodModel> getFoodByID(Long id) {
        return foodRespository.findById(id);
    }

    public List<FoodModel> getAllFoods() {
        return foodRespository.findAll();
    }

    public FoodModel updateFood(Long id, FoodModel food) {
        FoodModel oldFood = foodRespository.findById(id).orElseThrow(() -> new RuntimeException("food not found"));

        if (food.getName() != null) {
            oldFood.setName(food.getName());
        }
        if (food.getDescription() != null) {
            oldFood.setDescription(food.getDescription());
        }
        if (food.getImageUrl() != null) {
            oldFood.setImageUrl(food.getImageUrl());
        }
        if (food.getIsAvailable() != null) {
            oldFood.setIsAvailable(food.getIsAvailable());
        }
        if (food.getPrice() != 0) {
            oldFood.setPrice(food.getPrice());
        }
        if (food.getQuantity() != 0) {
            oldFood.setQuantity(food.getQuantity());
        }
        var result = foodRespository.save(oldFood);
        return result;
    }

    public void deleteFood(Long id) {
        foodRespository.deleteById(id);
    }
}
