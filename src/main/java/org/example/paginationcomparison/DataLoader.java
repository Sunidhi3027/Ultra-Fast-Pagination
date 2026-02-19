
package org.example.paginationcomparison;

import org.example.paginationcomparison.entity.Order;
import org.example.paginationcomparison.repository.OrderRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Random;

@Component
public class DataLoader implements CommandLineRunner {

    private final OrderRepository repository;

    public DataLoader(OrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {

        if (repository.count() > 0) return;

        Random random = new Random();

        for (int i = 0; i < 200000; i++) {
            repository.save(
                    new Order(
                            Instant.now().minusSeconds(random.nextInt(1000000)),
                            "Order " + i
                    )
            );
        }

        System.out.println("Inserted 200k test records");
    }
}