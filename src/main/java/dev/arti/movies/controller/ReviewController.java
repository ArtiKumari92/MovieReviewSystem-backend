package dev.arti.movies.controller;

import dev.arti.movies.model.Review;
import dev.arti.movies.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    @Autowired
    private ReviewService service;

    @PostMapping()
    public ResponseEntity<Review> createReview(@RequestBody Map<String, String> payload, Principal principal) {
        String email = principal.getName(); // Extracted from JWT by Spring Security

        Review createdReview = service.createReview(
                payload.get("reviewBody"),
                payload.get("imdbId"),
                email // Pass the email to service
        );

        return new ResponseEntity<>(createdReview, HttpStatus.OK);
    }
}
