package io.spring.moviecatalogueservice.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
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
public class MovieInfo {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallbackCatalogueItem")
    public CatalogueItem getCatalogueItem(Rating rating)
    {
        Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
        return new CatalogueItem(movie.getMovieName(), "Dil", rating.getRating());
    }


    public CatalogueItem getFallbackCatalogueItem(Rating rating)
    {
        return new CatalogueItem("MovieNane", "Not Found", 0);
    }

}
