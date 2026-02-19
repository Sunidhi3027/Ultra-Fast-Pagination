package org.example.paginationcomparison.controller;

import org.example.paginationcomparison.cursor.CursorUtil;
import org.example.paginationcomparison.cursor.OrderCursor;
import org.example.paginationcomparison.entity.Order;
import org.example.paginationcomparison.service.KeysetOrderService;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/keyset/orders")
public class KeysetOrderController {

    private final KeysetOrderService service;

    public KeysetOrderController(KeysetOrderService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getOrders(
            @RequestParam(required = false) String cursor,
            @RequestParam(defaultValue = "20") int size
    ) {

        long start = System.currentTimeMillis();

        Slice<Order> slice = service.getOrders(cursor, size);

        String nextCursor = null;

        if (!slice.getContent().isEmpty() && slice.hasNext()) {
            Order last = slice.getContent()
                    .get(slice.getContent().size() - 1);

            nextCursor = CursorUtil.encode(
                    new OrderCursor(
                            last.getCreatedAt(),
                            last.getId()
                    )
            );
        }

        Map<String, Object> response = new HashMap<>();
        response.put("data", slice.getContent());
        response.put("nextCursor", nextCursor);

        long end = System.currentTimeMillis();
        long executionTime = end - start;

        return ResponseEntity.ok()
                .header("X-Execution-Time", executionTime + " ms")
                .body(response);
    }
}