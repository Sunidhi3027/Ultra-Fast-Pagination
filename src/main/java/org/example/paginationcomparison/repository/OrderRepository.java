package org.example.paginationcomparison.repository;

import org.example.paginationcomparison.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}