package EFood.controllers;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import EFood.Response.FoodReport;
import EFood.Response.OrderReportResponse;
import EFood.config.ApiResponse;
import EFood.services.OrderService;
import EFood.utils.OrderItemResponse;
import EFood.utils.OrderResponse;

@RestController
@RequestMapping("/api/report")
public class ReportController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/daily")
    public ResponseEntity<?> getDailyReport() {
        try {
            var result = orderService.getOrders();
            double totalPrice = 0;
            Map<String, FoodReport> res = new HashMap<>();
            List<FoodReport> answer = new ArrayList<>();
            LocalDate today = LocalDate.now();
            for (OrderResponse order : result) {
                if (ChronoUnit.DAYS.between(order.getCreatedAt().toLocalDate(), today) == 0) {
                    List<OrderItemResponse> item = order.getItems();
                    for (OrderItemResponse it : item) {
                        double price = it.getFood().getPrice() * it.getQuantity();
                        String fName = it.getFood().getName();
                        if (!res.containsKey(fName)) {
                            res.put(fName, new FoodReport(fName, it.getQuantity(), price));
                        } else {
                            var prev = res.get(fName);
                            prev.setQuantity(it.getQuantity() + prev.getQuantity());
                            prev.setPrice(price + prev.getPrice());
                            res.put(fName, prev);
                        }
                        totalPrice = totalPrice + price;

                    }
                }
            }
            for (FoodReport report : res.values()) {
                answer.add(report);
            }
            return ResponseEntity
                    .ok(new ApiResponse("daily order report", true, new OrderReportResponse(totalPrice, answer)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), false, null));
        }
    }

    @GetMapping("/weekly")
    public ResponseEntity<?> getWeeklyReport() {
        try {
            var result = orderService.getOrders();
            double totalPrice = 0;
            Map<String, FoodReport> res = new HashMap<>();
            List<FoodReport> answer = new ArrayList<>();
            LocalDate today = LocalDate.now();
            for (OrderResponse order : result) {
                if (ChronoUnit.DAYS.between(order.getCreatedAt().toLocalDate(), today) <= 7) {
                    List<OrderItemResponse> item = order.getItems();
                    for (OrderItemResponse it : item) {
                        double price = it.getFood().getPrice() * it.getQuantity();
                        String fName = it.getFood().getName();
                        if (!res.containsKey(fName)) {
                            res.put(fName, new FoodReport(fName, it.getQuantity(), price));
                        } else {
                            var prev = res.get(fName);
                            prev.setQuantity(it.getQuantity() + prev.getQuantity());
                            prev.setPrice(price + prev.getPrice());
                            res.put(fName, prev);
                        }
                        totalPrice = totalPrice + price;

                    }
                }
            }
            for (FoodReport report : res.values()) {
                answer.add(report);
            }
            return ResponseEntity
                    .ok(new ApiResponse("weekly order report", true, new OrderReportResponse(totalPrice, answer)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), false, null));
        }
    }

    @GetMapping("/monthly")
    public ResponseEntity<?> getMonthlyReport() {
        try {
            var result = orderService.getOrders();
            double totalPrice = 0;
            Map<String, FoodReport> res = new HashMap<>();
            List<FoodReport> answer = new ArrayList<>();
            LocalDate today = LocalDate.now();
            for (OrderResponse order : result) {
                if (ChronoUnit.MONTHS.between(order.getCreatedAt().toLocalDate(), today) == 0) {
                    List<OrderItemResponse> item = order.getItems();
                    for (OrderItemResponse it : item) {
                        double price = it.getFood().getPrice() * it.getQuantity();
                        String fName = it.getFood().getName();
                        if (!res.containsKey(fName)) {
                            res.put(fName, new FoodReport(fName, it.getQuantity(), price));
                        } else {
                            var prev = res.get(fName);
                            prev.setQuantity(it.getQuantity() + prev.getQuantity());
                            prev.setPrice(price + prev.getPrice());
                            res.put(fName, prev);
                        }
                        totalPrice = totalPrice + price;

                    }
                }
            }
            for (FoodReport report : res.values()) {
                answer.add(report);
            }
            return ResponseEntity
                    .ok(new ApiResponse("monthly order report", true, new OrderReportResponse(totalPrice, answer)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), false, null));
        }
    }

}
