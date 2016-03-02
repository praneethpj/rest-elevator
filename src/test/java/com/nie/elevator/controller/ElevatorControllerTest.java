package com.nie.elevator.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.ParseException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.nie.elevator.Application;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = Application.class)
@IntegrationTest({ "server.port=0", "management.port=0" })
@ActiveProfiles("test")
public class ElevatorControllerTest {

	@Autowired
	WebApplicationContext context;

	@Value("${server.port}")
	private int port;

	private MockMvc mvc;

	private String baseUrl = "";

	public ElevatorControllerTest() {
		baseUrl = "http://localhost:" + this.port + "/elevators/";
	}

	@Before
	public void initTests() throws ParseException {
		MockitoAnnotations.initMocks(this);
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	@DirtiesContext
	public void testRequestAnElevator() throws Exception {
		String[] expectedElevatorIds = {"A", "B", "C", "D"};
		String queryString = "floorNo/3/toFloorNo/6/noOfPeople/10";
		mvc.perform(get(baseUrl + queryString).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(ElevatorMatch(expectedElevatorIds));
	}
	
	@Test
	@DirtiesContext
	public void testRequestAnElevatorFourTimes() throws Exception {
		String[] expectedElevatorIds = {"A", "B", "C", "D"};
		
		String queryString = "floorNo/3/toFloorNo/6/noOfPeople/10";
		mvc.perform(get(baseUrl + queryString).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(ElevatorMatch(expectedElevatorIds));
		
		queryString = "floorNo/5/toFloorNo/8/noOfPeople/3";
		mvc.perform(get(baseUrl + queryString).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(ElevatorMatch(expectedElevatorIds));
		
		queryString = "floorNo/1/toFloorNo/4/noOfPeople/8";
		mvc.perform(get(baseUrl + queryString).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(ElevatorMatch(expectedElevatorIds));
		
		queryString = "floorNo/8/toFloorNo/2/noOfPeople/10";
		mvc.perform(get(baseUrl + queryString).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(ElevatorMatch(expectedElevatorIds));
	}
	
	@Test
	@DirtiesContext
	public void testRequestAnElevatorFailsTheFifthTime() throws Exception {
		String[] expectedElevatorIds = {"A", "B", "C", "D"};
		
		String queryString = "floorNo/3/toFloorNo/6/noOfPeople/10";
		mvc.perform(get(baseUrl + queryString).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(ElevatorMatch(expectedElevatorIds));
		
		queryString = "floorNo/5/toFloorNo/8/noOfPeople/3";
		mvc.perform(get(baseUrl + queryString).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(ElevatorMatch(expectedElevatorIds));
		
		queryString = "floorNo/1/toFloorNo/4/noOfPeople/8";
		mvc.perform(get(baseUrl + queryString).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(ElevatorMatch(expectedElevatorIds));
		
		queryString = "floorNo/8/toFloorNo/2/noOfPeople/10";
		mvc.perform(get(baseUrl + queryString).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(ElevatorMatch(expectedElevatorIds));
		
		//The fifth request fails
		queryString = "floorNo/5/toFloorNo/3/noOfPeople/10";
		mvc.perform(get(baseUrl + queryString).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound());
	}
	
	@Test
	@DirtiesContext
	public void testRequestAnElevatorFailsWhenNoOfOpepleOver20() throws Exception {
		
		String queryString = "floorNo/5/toFloorNo/3/noOfPeople/21";
		mvc.perform(get(baseUrl + queryString).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	@DirtiesContext
	public void testRequestAnElevatorFailsWhenNoOfOpepleIsZero() throws Exception {
		
		String queryString = "floorNo/5/toFloorNo/3/noOfPeople/0";
		mvc.perform(get(baseUrl + queryString).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	@DirtiesContext
	public void testRequestAnElevatorFailsWhenFloorNoIsZero() throws Exception {
		
		String queryString = "floorNo/0/toFloorNo/3/noOfPeople/3";
		mvc.perform(get(baseUrl + queryString).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	@DirtiesContext
	public void testRequestAnElevatorFailsWhenFloorNoIsOverTen() throws Exception {
		
		String queryString = "floorNo/11/toFloorNo/3/noOfPeople/3";
		mvc.perform(get(baseUrl + queryString).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	@DirtiesContext
	public void testRequestAnElevatorFailsWhenFromFloorNoSameAsToFloorNo() throws Exception {
		
		String queryString = "floorNo/5/toFloorNo/5/noOfPeople/3";
		mvc.perform(get(baseUrl + queryString).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest());
	}

	private static ResultMatcher ElevatorMatch(final String[] expectedElevatorIds) {
		return new ResultMatcher() {
			public void match(MvcResult result) throws Exception {
				jsonPath("$.id", isIn(expectedElevatorIds)).match(result);
			}
		};
	}

}
