package dev.arti.movies.repository;


import dev.arti.movies.model.Watchlist;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface WatchlistRepository extends MongoRepository<Watchlist, String> {
    Optional<Watchlist> findByEmail(String email);
}
