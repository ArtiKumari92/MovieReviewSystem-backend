package dev.arti.movies.controller;

import dev.arti.movies.model.Movie;
import dev.arti.movies.model.Watchlist;
import dev.arti.movies.repository.MovieRepository;
import dev.arti.movies.service.WatchlistService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import dev.arti.movies.repository.WatchlistRepository;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/watchlist")
public class WatchlistController {

    @Autowired
    private WatchlistService service;

    @Autowired
    private WatchlistRepository watchlistRepository;

    @Autowired
    private MovieRepository movieRepository;

    @PostMapping("/{movieId}")
    public Watchlist addToWatchlist(@PathVariable String movieId, Principal principal) {
        return service.addToWatchlist(principal.getName(), movieId);
    }

    @GetMapping
    public ResponseEntity<?> getWatchlist(Principal principal) {
        String email = principal.getName();
        Optional<Watchlist> optionalWatchlist = watchlistRepository.findByEmail(email);

        if (optionalWatchlist.isPresent()) {
            Watchlist watchlist = optionalWatchlist.get();

            List<Movie> movies = movieRepository.findAllByImdbIdIn(watchlist.getMovieIds());
            return ResponseEntity.ok(movies);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No watchlist found.");
    }



    @PostMapping
    public ResponseEntity<?> addToWatchlist(@RequestBody Map<String, String> request, Principal principal) {
        String email = principal.getName();
        String imdbId = request.get("imdbId");

        Optional<Watchlist> optionalWatchlist = watchlistRepository.findByEmail(email);
        Watchlist watchlist = optionalWatchlist.orElse(new Watchlist(new ObjectId(), email, new ArrayList<>()));

        if (!watchlist.getMovieIds().contains(imdbId)) {
            watchlist.getMovieIds().add(imdbId);
            watchlistRepository.save(watchlist);
            return ResponseEntity.ok("Added to watchlist.");
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).body("Movie already in watchlist.");
    }

    @DeleteMapping("/{imdbId}")
    public ResponseEntity<?> removeFromWatchlist(@PathVariable String imdbId, Principal principal) {
        String email = principal.getName();

        Optional<Watchlist> optionalWatchlist = watchlistRepository.findByEmail(email);
        if (optionalWatchlist.isPresent()) {
            Watchlist watchlist = optionalWatchlist.get();
            if (watchlist.getMovieIds().remove(imdbId)) {
                watchlistRepository.save(watchlist);
                return ResponseEntity.ok("Removed from watchlist.");
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie not found in watchlist.");
    }


}

