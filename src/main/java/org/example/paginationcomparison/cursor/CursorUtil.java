package org.example.paginationcomparison.cursor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.Base64;

public class CursorUtil {

    private static final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    public static String encode(OrderCursor cursor) {
        try {
            byte[] json = mapper.writeValueAsBytes(cursor);
            return Base64.getUrlEncoder().encodeToString(json);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to encode cursor", e);
        }
    }

    public static OrderCursor decode(String encoded) {
        try {
            byte[] decoded = Base64.getUrlDecoder().decode(encoded);
            return mapper.readValue(decoded, OrderCursor.class);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to decode cursor", e);
        }
    }
}