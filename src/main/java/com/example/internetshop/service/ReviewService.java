package com.example.internetshop.service;

import com.example.internetshop.model.Review;
import java.util.List;
import java.util.Optional;

public interface ReviewService {
    List<Review> getAllReviews();

    Optional<Review> getReviewById(Long id);

    Review createReview(Review review);

    Review updateReview(Long id, Review updatedReview);

    void deleteReview(Long id);
}