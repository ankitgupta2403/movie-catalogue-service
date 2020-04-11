package io.spring.moviecatalogueservice.resources;

import io.spring.moviecatalogueservice.models.CatalogueItem;
import io.spring.moviecatalogueservice.models.Movie;
import io.spring.moviecatalogueservice.models.Rating;
import io.spring.moviecatalogueservice.models.UserRating;
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

    /*
    @Autowired
    private WebClient.Builder webClientBuilder;

     */

    @GetMapping("/{userId}")
    public List<CatalogueItem> getCatalogue(@PathVariable("userId") String userId)
    {



        //get all rated movie Ids
        UserRating ratings = restTemplate.getForObject("http://movie-rating-service/ratingService/users/"+userId, UserRating.class);

        //for each movie ID call movieInfo service
        return ratings.getUserRating().stream().map(rating -> {
            Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);

            /*
                Movie movie = webClientBuilder.build()
                        .get()
                        .uri("http://localhost:8082/movies/" + rating.getMovieId())
                        .retrieve()
                        .bodyToMono(Movie.class)
                        .block();
             */
                    return new CatalogueItem(movie.getMovieName(), "Dil", rating.getRating());
                }
        ).collect(Collectors.toList());




    }
}
