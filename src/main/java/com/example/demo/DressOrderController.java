package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class DressOrderController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @PostMapping("/submitOrder")
    public String submitOrder(@RequestParam String customerName,
                              @RequestParam String dressType,
                              @RequestParam String dressSize) {
        try {
            jdbcTemplate.update("INSERT INTO orders (customer_name, dress_type, dress_size) VALUES (?, ?, ?)",
                    customerName, dressType, dressSize);
            return "redirect:/success";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error";
        }
    }

    @GetMapping("/success")
    public String showSuccessPage(Model model) {
        List<Map<String, Object>> orders = jdbcTemplate.queryForList("SELECT * FROM orders");
        model.addAttribute("orders", orders);
        return "success";
    }

    @GetMapping("/error")
    public String showErrorPage() {
        return "error";
    }
}
