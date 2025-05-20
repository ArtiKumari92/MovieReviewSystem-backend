package dev.arti.movies.service;

import dev.arti.movies.model.Watchlist;
import dev.arti.movies.repository.WatchlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WatchlistService {
    @Autowired
    private WatchlistRepository repository;

    public Watchlist addToWatchlist(String email, String movieId) {
        Watchlist wl = repository.findByEmail(email).orElse(new Watchlist(null, email, new java.util.ArrayList<>()));
        if (!wl.getMovieIds().contains(movieId)) {
            wl.getMovieIds().add(movieId);
        }
        return repository.save(wl);
    }

    public Watchlist getWatchlist(String email) {
        return repository.findByEmail(email).orElse(new Watchlist(null, email, new java.util.ArrayList<>()));
    }
}

