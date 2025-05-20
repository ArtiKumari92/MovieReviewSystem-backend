package dev.arti.movies.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "reviews")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    private ObjectId id;
    private String body;
    private String email;
    private LocalDateTime created;
    private LocalDateTime updated;

    public Review(String body, String email, LocalDateTime created, LocalDateTime updated) {
        this.body = body;
        this.email = email;
        this.created = created;
        this.updated = updated;
    }
}
