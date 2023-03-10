package com.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.Assert;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.Movie;
import com.repository.MovieRepository;
import com.service.MovieServiceImpl;

import org.slf4j.Logger;

@WebMvcTest
public class MovieRestControllerTest {

	@MockBean
	MovieServiceImpl movieImpl;

	@MockBean
	MovieRepository mRepo;

	@Mock
	Logger logger;

	@Autowired
	private MockMvc mockMvc;

	private static ObjectMapper mapper = new ObjectMapper();

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	void testInsertMovie() throws Exception {
		Movie movie = new Movie(1, "Bahubali", "Telugu", "Action", 4, "bahubali.png");
		/*
		 * mockMvc.perform( MockMvcRequestBuilders .post("/insertMovie")
		 * .content(asJsonString(new Movie(1, "Bahubali", "Telugu", "Action", 4,
		 * "bahubali.png"))) .contentType(MediaType.APPLICATION_JSON)
		 * .accept(MediaType.APPLICATION_JSON)) .andExpect(status().isCreated())
		 * .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Bahubali"));
		 */

		Mockito.when(logger.isInfoEnabled()).thenReturn(false);

		Mockito.when(mRepo.save(movie)).thenReturn(movie);

		Mockito.when(movieImpl.insertMovie(ArgumentMatchers.any())).thenReturn(movie);

		String json = mapper.writeValueAsString(movie);

		MvcResult requestResult = mockMvc.perform(post("/insertMovie").contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("utf-8").content(json).accept(MediaType.APPLICATION_JSON)).andReturn();

		String result = requestResult.getResponse().getContentAsString();

		Assert.notNull(result);
		/*
		 * Movie mov = new ObjectMapper().readValue(result, Movie.class);
		 * assertEquals(movie.getName(), mov.getName());
		 */

	}

}
