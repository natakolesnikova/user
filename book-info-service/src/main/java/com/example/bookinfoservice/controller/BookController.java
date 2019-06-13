package com.example.bookinfoservice.controller;

import com.example.bookinfoservice.model.Book;
import com.example.bookinfoservice.model.BookSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/book")
public class BookController {

    @Value("${api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/{bookId}")
    public Book getMovieInfo(@PathVariable("bookId") String bookId) {
        BookSummary movieSummary = restTemplate.getForObject("https://api.themoviedb.org/3/book/" + bookId + "?api_key=" +  apiKey, BookSummary.class);
        return new Book(bookId, movieSummary.getTitle(), movieSummary.getOverview());

    }
}
