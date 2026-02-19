package org.example.paginationcomparison.repository;

import org.example.paginationcomparison.entity.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

public interface KeysetOrderRepository extends JpaRepository<Order, Long> {

    @Query(value = """
        SELECT *
        FROM orders
        WHERE (created_at < :createdAt)
           OR (created_at = :createdAt AND id < :id)
        ORDER BY created_at DESC, id DESC
        LIMIT :#{#pageable.pageSize}
        """, nativeQuery = true)
    List<Order> findNextPage(
            @Param("createdAt") Instant createdAt,
            @Param("id") Long id,
            Pageable pageable
    );
}