package com.oscar.movie.controller;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OscarAwardControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private final String API = "/api/oscarAwards";

    private final String token =  "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY3NDA3OTUwOCwiaWF0IjoxNjY" +
            "3NTA5NTA4fQ.FNJkzUDZE9iRKemfnQ6V5f9pTHW3PVn015r2oF5oJzZ_8J-fJcSMeOP7pp68g6V4B4M0dFsXyn7I3Rb69piexA";

    @Test
    public void test01GetAllOscarAward() throws Exception {
        this.mockMvc.perform( get(this.API).header("Authorization", "Bearer " + token) )
                .andExpect( status().isOk() )
                .andExpect( content().contentType(MediaType.APPLICATION_JSON) )
                .andExpect( jsonPath("$", hasSize(10137)) )
                .andExpect( jsonPath("$[0].category", is("Acting (other)")) )
                .andExpect( jsonPath("$[0].nominee", is("To Vincent Winter for his outstanding juvenile " +
                        "performance in The Little Kidnappers.")) )
                .andReturn();
    }

    @Test
    public void test02GetIfMovieWonBestPictureByTitle() throws Exception {
        String movieTitle = "The Hurt Locker";
        this.mockMvc.perform( get(this.API + "/bestPictures/" + movieTitle).header("Authorization", "Bearer " + token) )
                .andExpect( status().isOk() )
                .andExpect( content().contentType(MediaType.APPLICATION_JSON) )
                .andExpect( jsonPath("$.oscarYear", is("2009 (82nd)")) )
                .andExpect( jsonPath("$.wonAward", is(true)) )
                .andReturn();
    }

    @Test
    public void test03GetOscarMovieNotFound() throws Exception {
        String movieTitle = "Test";
        this.mockMvc.perform( get(this.API + "/bestPictures/" + movieTitle).header("Authorization", "Bearer " + token) )
                .andExpect( status().isNotFound() )
                .andExpect( jsonPath("$.message", is("Movie not won best picture award :: " + movieTitle)) )
                .andReturn();
    }
}
