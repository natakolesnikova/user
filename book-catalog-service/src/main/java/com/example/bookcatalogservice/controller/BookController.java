package com.example.bookcatalogservice.controller;

import com.example.bookcatalogservice.model.Book;
import com.example.bookcatalogservice.model.CatalogItem;
import com.example.bookcatalogservice.model.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class BookController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

        UserRating userRating = restTemplate.getForObject("http://ratings-data-service/ratingsdata/user/" + userId, UserRating.class);

        return userRating.getRatings().stream()
                .map(rating -> {
                    Book book = restTemplate.getForObject("http://book-info-service/book/" + rating.getMovieId(), Book.class);
                    return new CatalogItem(book.getName(), book.getDescription(), rating.getRating());
                })
                .collect(Collectors.toList());

    }

}
