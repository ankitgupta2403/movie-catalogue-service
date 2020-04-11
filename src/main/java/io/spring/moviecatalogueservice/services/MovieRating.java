package io.spring.moviecatalogueservice.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.spring.moviecatalogueservice.models.CatalogueItem;
import io.spring.moviecatalogueservice.models.Movie;
import io.spring.moviecatalogueservice.models.Rating;
import io.spring.moviecatalogueservice.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class MovieRating {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallbackUserRating")
    public UserRating getUserRating(@PathVariable("userId") String userId) {
        return restTemplate.getForObject("http://movie-rating-service/ratingService/users/" + userId, UserRating.class);
    }

    public UserRating getFallbackUserRating(@PathVariable("userId") String userId) {
        UserRating userRating = new UserRating();
        userRating.setUserRating(Arrays.asList(
                new Rating("D101",5)
        ));
        return userRating;
    }
}
