package io.spring.moviecatalogueservice.resources;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.spring.moviecatalogueservice.models.CatalogueItem;
import io.spring.moviecatalogueservice.models.Movie;
import io.spring.moviecatalogueservice.models.Rating;
import io.spring.moviecatalogueservice.models.UserRating;
import io.spring.moviecatalogueservice.services.MovieInfo;
import io.spring.moviecatalogueservice.services.MovieRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
// import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalogue")
public class MovieCatalogueResource {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    MovieInfo movieInfo;

    @Autowired
    MovieRating movieRating;

    @GetMapping("/{userId}")
    public List<CatalogueItem> getCatalogue(@PathVariable("userId") String userId) {

        //get all rated movie Ids
        UserRating ratings = movieRating.getUserRating(userId);
        return ratings.getUserRating().stream().map(rating -> {
            return movieInfo.getCatalogueItem(rating);
                }
        ).collect(Collectors.toList());
    }

}

