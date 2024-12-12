package EFood.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import EFood.models.FoodModel;
import EFood.models.OrderItemModel;
import EFood.models.OrderModel;
import EFood.repositories.FoodRespository;
import EFood.repositories.OrderItemRepository;
import EFood.repositories.OrderRepository;
import EFood.repositories.UserRepository;
import EFood.utils.OrderItemResponse;
import EFood.utils.OrderResponse;
import EFood.utils.UpdateOrderRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FoodRespository foodRespository;
    @Autowired
    private FoodService foodService;

    @Transactional
    public OrderResponse createOrder(Long userID, List<OrderItemModel> items) {
        // [foodid,quantity]
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Order must have at least one item.");
        }

        // Validate user existence
        userRepository.findById(userID)
                .orElseThrow(() -> new IllegalArgumentException("No user with this ID exists."));

        // Validate food items and availability
        for (OrderItemModel item : items) {
            var food = foodRespository.findById(item.getFoodId())
                    .orElseThrow(() -> new IllegalArgumentException("No food with this ID exists."));
            if (!food.getIsAvailable()) {
                throw new IllegalArgumentException("Food item is not available right now: " + food.getName());
            }
        }

        // Create order and link items
        OrderModel order = new OrderModel(userID, items);

        // Link items to the order
        for (OrderItemModel item : items) {
            var fid = item.getFoodId();
            var fquantity = item.getQuantity();
            var oldFood = foodService.getFoodByID(fid);
            FoodModel f = new FoodModel();
            f.setQuantity(oldFood.get().getQuantity() - fquantity);
            foodService.updateFood(fid, f);
            item.setOrder(order);
        }

        // Save order (and items with cascade)
        var savedOrder = orderRepository.save(order);
        double totalPrice = 0;
        List<OrderItemResponse> orderResp = new ArrayList<>();
        for (OrderItemModel item : items) {
            var foodId = item.getFoodId();
            var fd = foodRespository.findById(foodId);
            var food = fd.orElseThrow(() -> new IllegalArgumentException("it is empty"));
            var quantity = item.getQuantity();
            totalPrice += (quantity * food.getPrice());
            OrderItemResponse ord = new OrderItemResponse(food, quantity);
            orderResp.add(ord);
        }
        return new OrderResponse(savedOrder.getId(), savedOrder.getStatus(), savedOrder.getCreatedAt(), totalPrice,
                orderResp);

    }

    public OrderResponse getOrderByUserId(Long userId) {
        var order = orderRepository.findByUserId(userId);
        var items = orderItemRepository.findByOrderId(order.getId());
        List<OrderItemResponse> orderResp = new ArrayList<>();
        double totalPrice = 0;
        for (OrderItemModel item : items) {
            var foodId = item.getFoodId();
            var fd = foodRespository.findById(foodId);
            var food = fd.orElseThrow(() -> new IllegalArgumentException("it is empty"));
            var quantity = item.getQuantity();
            totalPrice += (quantity * food.getPrice());
            OrderItemResponse ord = new OrderItemResponse(food, quantity);
            orderResp.add(ord);
        }

        return new OrderResponse(order.getId(), order.getStatus(), order.getCreatedAt(), totalPrice, orderResp);
    }

    public OrderResponse getOrderById(Long id) {
        var order = orderRepository.findById(id).get();
        var items = orderItemRepository.findByOrderId(id);
        if (items.isEmpty()) {
            throw new IllegalArgumentException("no order exist");
        }
        double totalPrice = 0;
        List<OrderItemResponse> orderResp = new ArrayList<>();
        for (OrderItemModel item : items) {
            var foodId = item.getFoodId();
            var fd = foodRespository.findById(foodId);
            var food = fd.orElseThrow(() -> new IllegalArgumentException("it is empty"));
            var quantity = item.getQuantity();
            totalPrice += (quantity * food.getPrice());
            OrderItemResponse ord = new OrderItemResponse(food, quantity);
            orderResp.add(ord);
        }
        return new OrderResponse(id, order.getStatus(), order.getCreatedAt(), totalPrice, orderResp);
    }

    public List<OrderResponse> getOrders() {
        var orders = orderRepository.findAll();
        List<OrderResponse> result = new ArrayList<>();
        for (OrderModel order : orders) {
            result.add(getOrderById(order.getId()));
        }
        return result;
    }

    public OrderModel updateStatus(Long id, String status) {

        var order = orderRepository.findById(id);
        if (!order.isPresent()) {
            throw new IllegalArgumentException("no order with this id");
        }
        var tempOrder = order.get();
        tempOrder.setStatus(status);
        return orderRepository.save(tempOrder);

    }

    @Transactional
    public OrderResponse updateOrder(Long id, UpdateOrderRequest updateRequest) {
        // Find the existing order by ID
        var order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No order exists with this ID."));

        // Validate the provided items
        List<OrderItemModel> updatedItems = updateRequest.getItems();
        if (updatedItems == null || updatedItems.isEmpty()) {
            throw new IllegalArgumentException("Updated order must have at least one item.");
        }

        for (OrderItemModel item : updatedItems) {
            var food = foodRespository.findById(item.getFoodId())
                    .orElseThrow(
                            () -> new IllegalArgumentException("No food with this ID exists: " + item.getFoodId()));
            if (!food.getIsAvailable()) {
                throw new IllegalArgumentException("Food item is not available: " + food.getName());
            }
        }

        // Update the order's items
        var existingItems = orderItemRepository.findByOrderId(order.getId());
        orderItemRepository.deleteAll(existingItems); // Clear old items

        // Link new items to the order
        for (OrderItemModel item : updatedItems) {
            item.setOrder(order);
        }

        // Save updated items and order
        orderItemRepository.saveAll(updatedItems);
        var savedOrder = orderRepository.save(order);

        double totalPrice = 0;
        List<OrderItemResponse> orderResp = new ArrayList<>();
        for (OrderItemModel item : updatedItems) {
            var food = foodRespository.findById(item.getFoodId())
                    .orElseThrow(() -> new IllegalArgumentException("Food data is missing."));
            var quantity = item.getQuantity();
            totalPrice += (quantity * food.getPrice());
            orderResp.add(new OrderItemResponse(food, quantity));
        }

        return new OrderResponse(savedOrder.getId(), savedOrder.getStatus(), savedOrder.getCreatedAt(), totalPrice,
                orderResp);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
