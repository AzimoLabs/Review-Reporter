package com.azimo.tool.publisher.mapper;

import com.azimo.tool.publisher.model.AppReview;
import com.google.api.services.androidpublisher.model.Review;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by F1sherKK on 15/12/16.
 */
public class ReviewMapper {

    public List<AppReview> toAppReviewList(List<Review> reviewList) {
        return reviewList.stream().map(AppReview::new).collect(Collectors.toList());
    }
}
