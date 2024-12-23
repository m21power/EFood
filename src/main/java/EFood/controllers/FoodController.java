package EFood.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import EFood.config.ApiResponse;
import EFood.models.FoodModel;
import EFood.services.FoodService;
import EFood.utils.CloudinaryService;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/foods")
public class FoodController {
    @Autowired
    private FoodService foodService;
    @Autowired
    private CloudinaryService cloudinaryService;

    @Operation(description = "for posting food, all of the field are required , only admin can do this")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<?> createFood(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("image") MultipartFile image,
            @RequestParam("price") double price,
            @RequestParam("quantity") Integer quantity

    ) {
        var image_url = cloudinaryService.uploadFile(image);
        System.out.println(image_url);
        var food = new FoodModel();
        food.setDescription(description);
        food.setName(name);
        food.setPrice(price);
        food.setImageUrl(image_url);
        food.setQuantity(quantity);
        var result = foodService.createFood(food);
        return ResponseEntity.ok(new ApiResponse("food posted successfully", true, result));
    }

    @Operation(description = "to get all the food in the database")
    @GetMapping
    public ResponseEntity<?> getAllFoods() {
        var result = foodService.getAllFoods();
        if (!result.isEmpty())
            return ResponseEntity.ok(new ApiResponse("fetched successfully", true, result));
        else
            return ResponseEntity.ok(new ApiResponse("List is empty", false, null));

    }

    @Operation(description = "to get food by id")
    @GetMapping("/{id}")
    public ResponseEntity<?> getFoodById(@PathVariable Long id) {
        var result = foodService.getFoodByID(id);
        if (result.isPresent()) {
            return ResponseEntity.ok(new ApiResponse("fetched successfull", true, result));
        } else {
            return ResponseEntity.status(404).body(new ApiResponse("no food found", false, null));
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(description = "to update content of the food based on food's id, including availability of the food")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateFood(@PathVariable Long id, @RequestBody FoodModel food) {
        try {
            var result = foodService.updateFood(id, food);
            return ResponseEntity.ok(new ApiResponse("updated successfully", true, result));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ApiResponse(e.getMessage(), false, null));
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(description = "to delete the food based on the food's id")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFood(@PathVariable Long id) {
        try {
            foodService.deleteFood(id);
            return ResponseEntity.ok(new ApiResponse("Delete successfully", true, null));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ApiResponse(e.getMessage(), false, null));
        }

    }
}
