package org.example.paginationcomparison.service;

import org.example.paginationcomparison.cursor.CursorUtil;
import org.example.paginationcomparison.cursor.OrderCursor;
import org.example.paginationcomparison.entity.Order;
import org.example.paginationcomparison.repository.KeysetOrderRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KeysetOrderService {

    private final KeysetOrderRepository repository;

    public KeysetOrderService(KeysetOrderRepository repository) {
        this.repository = repository;
    }

    public Slice<Order> getOrders(String cursor, int size) {

        Pageable pageable = PageRequest.of(0, size);

        // 🔹 FIRST PAGE (no cursor)
        if (cursor == null) {

            Page<Order> page = repository.findAll(
                    PageRequest.of(
                            0,
                            size,
                            Sort.by(Sort.Direction.DESC, "createdAt")
                                    .and(Sort.by(Sort.Direction.DESC, "id"))
                    )
            );

            return new SliceImpl<>(
                    page.getContent(),
                    page.getPageable(),
                    page.hasNext()
            );
        }

        // 🔹 NEXT PAGES
        OrderCursor decoded = CursorUtil.decode(cursor);

        List<Order> orders = repository.findNextPage(
                decoded.createdAt(),
                decoded.id(),
                pageable
        );

        boolean hasNext = orders.size() == size;

        return new SliceImpl<>(orders, pageable, hasNext);
    }
}