package com.example.internetshop.service.impl;

import com.example.internetshop.model.Review;
import com.example.internetshop.repository.ReviewRepository;
import com.example.internetshop.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    @Override
    public Optional<Review> getReviewById(Long id) {
        return reviewRepository.findById(id);
    }

    @Override
    public Review createReview(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public Review updateReview(Long id, Review updatedReview) {
        return reviewRepository.findById(id)
                .map(review -> {
                    review.setUser(updatedReview.getUser());
                    review.setProduct(updatedReview.getProduct());
                    review.setRating(updatedReview.getRating());
                    review.setComment(updatedReview.getComment());
                    return reviewRepository.save(review);
                })
                .orElse(null); // Или выбросить исключение
    }

    @Override
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }
}