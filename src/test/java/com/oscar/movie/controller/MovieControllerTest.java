package com.oscar.movie.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oscar.movie.model.Movie;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MovieControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    private final String API = "/api/movies";

    private final String token =  "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY3NDA3OTUwOCwiaWF0IjoxNjY" +
            "3NTA5NTA4fQ.FNJkzUDZE9iRKemfnQ6V5f9pTHW3PVn015r2oF5oJzZ_8J-fJcSMeOP7pp68g6V4B4M0dFsXyn7I3Rb69piexA";

    public MovieControllerTest() {
    }

    @Test
    public void test01GetAllMovies() throws Exception {
        this.mockMvc.perform( get(this.API).header("Authorization", "Bearer " + token) )
                .andExpect( status().isOk() )
                .andExpect( content().contentType(MediaType.APPLICATION_JSON) )
                .andExpect( jsonPath("$", hasSize(3779)) )
                .andExpect( jsonPath("$[0].title", is("$1,000 a Minute")) )
                .andExpect( jsonPath("$[1].title", is("'38'")) )
                .andReturn();
    }

    @Test
    public void test02CreateMovie() throws Exception {
        this.mockMvc.perform( post("/api/movies").header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(this.newMovie() )) )
                .andExpect( status().isOk() )
                .andExpect( jsonPath("$.id", is(3780)) )
                .andExpect( jsonPath("$.title", is("Grayton")) )
                .andReturn();

    }

    @Test
    public void test03GetMovieByTitle() throws Exception {
        String movieTitle = "Grayton";
        this.mockMvc.perform( get(this.API + "/" + movieTitle).header("Authorization", "Bearer " + token) )
                .andExpect( status().isOk() )
                .andExpect( content().contentType(MediaType.APPLICATION_JSON) )
                .andExpect( jsonPath("$.title", is(movieTitle)) )
                .andExpect( jsonPath("$.genre", is("Action")) )
                .andExpect( jsonPath("$.releaseYear", is(2021)) )
                .andExpect( jsonPath("$.ratingDescription", is("Good")) )
                .andExpect( jsonPath("$.totalRating", is(9.5)) )
                .andExpect( jsonPath("$.boxOfficeValue", is(0)) )
                .andReturn();
    }

    @Test
    public void test04GetMovieNotFound() throws Exception {
        String movieTitle = "Test";
        this.mockMvc.perform( get(this.API + "/" + movieTitle).header("Authorization", "Bearer " + token) )
                .andExpect( status().isNotFound() )
                .andExpect( jsonPath("$.message", is("Movie not found :: " + movieTitle)) )
                .andReturn();
    }

    @Test
    public void test05UpdateMovie() throws Exception {
        String movieTitle = "Your National Gallery";
        Movie movie = getMovie( apiGet("/" + movieTitle).getResponse() );
        movie.setRatingDescription("Very Good");
        movie.setTotalRating(9.6);
        movie.setBoxOfficeValue(10000);
        long id = movie.getId();

        this.mockMvc.perform( put(this.API + "/" + id).header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString( movie )) )
                .andExpect( status().isOk() )
                .andExpect( jsonPath("$.title", is("Your National Gallery")) )
                .andExpect( jsonPath("$.ratingDescription", is("Very Good")) )
                .andExpect( jsonPath("$.totalRating", is(9.6)) )
                .andExpect( jsonPath("$.boxOfficeValue", is(10000)) )
                .andReturn();
    }

    @Test
    public void test06UpdateMovieNotFound() throws Exception {
        int id = 3781;
        this.mockMvc.perform( put(this.API + "/" + id).header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content( asJsonString( this.newMovie() )) )
                .andExpect( status().isNotFound() )
                .andExpect( jsonPath("$.message", is("Movie not found :: " + id)) )
                .andReturn();
    }

    @Test
    public void test07GetTopRatedMovies() throws Exception {
        int totalRecords = 10;
        this.mockMvc.perform( get(this.API + "/topRatedMovies/" + totalRecords).header("Authorization", "Bearer " + token) )
                .andExpect( status().isOk() )
                .andExpect( content().contentType(MediaType.APPLICATION_JSON) )
                .andExpect( jsonPath("$", hasSize(10)) )
                .andExpect( jsonPath("$[0].title", is("Your National Gallery")) )
                .andExpect( jsonPath("$[0].totalRating", is(9.6)) )
                .andReturn();
    }

    // Helper function
    public Movie newMovie() {
        return new Movie("Grayton", "Action", 2021,
                "Good", 9.5, 0 );
    }

    public String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Movie getMovie(MockHttpServletResponse res) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(res.getContentAsString(), Movie.class);
    }

    public MvcResult apiGet(String uri) throws Exception {
        return this.mockMvc.perform(get(this.API + uri).header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn();
    }
}
