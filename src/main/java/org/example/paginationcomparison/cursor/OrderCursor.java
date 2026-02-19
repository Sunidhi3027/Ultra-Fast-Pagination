package org.example.paginationcomparison.cursor;

import java.time.Instant;

public record OrderCursor(Instant createdAt, Long id) {
}