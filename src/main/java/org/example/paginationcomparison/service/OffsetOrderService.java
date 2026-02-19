package org.example.paginationcomparison.service;


import org.example.paginationcomparison.entity.Order;
import org.example.paginationcomparison.repository.OrderRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class OffsetOrderService {

    private final OrderRepository repository;

    public OffsetOrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public Page<Order> getOrders(int page, int size) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "createdAt")
                        .and(Sort.by(Sort.Direction.DESC, "id"))
        );

        return repository.findAll(pageable);
    }
}