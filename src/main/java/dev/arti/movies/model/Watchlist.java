package dev.arti.movies.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.bson.types.ObjectId;

import java.util.List;

@Document(collection = "watchlists")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Watchlist {
    private ObjectId id;
    private String email;
    private List<String> movieIds;
}

