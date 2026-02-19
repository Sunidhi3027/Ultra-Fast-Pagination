package org.example.paginationcomparison.controller;

import org.example.paginationcomparison.entity.Order;
import org.example.paginationcomparison.service.OffsetOrderService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/offset/orders")
public class OffsetOrderController {

    private final OffsetOrderService service;

    public OffsetOrderController(OffsetOrderService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<Order>> getOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {

        long start = System.currentTimeMillis();

        Page<Order> result = service.getOrders(page, size);

        long end = System.currentTimeMillis();
        long executionTime = end - start;

        return ResponseEntity.ok()
                .header("X-Execution-Time", executionTime + " ms")
                .body(result);
    }
}