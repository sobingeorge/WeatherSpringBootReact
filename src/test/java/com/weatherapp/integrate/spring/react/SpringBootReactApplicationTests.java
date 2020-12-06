package com.weatherapp.integrate.spring.react;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weatherapp.integrate.spring.react.requestobject.LoginRequest;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest
@ComponentScan({ "com.weatherapp.integrate.spring.react.controller" })
public class SpringBootReactApplicationTests {

	
	
	protected MockMvc mvc;

	@Autowired
	WebApplicationContext webApplicationContext;

	@Before
	public void setUp() {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	protected String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}

	protected <T> T mapFromJson(String json, Class<T> clazz)
			throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, clazz);
	}

	@Test
	public void contextLoads() {

	}

	
	@Test
	public void testSignIn() throws Exception {
		String uri = "/api/auth/signin";
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setUsername("test@gmail.com");
		loginRequest.setPassword("test@gmail.com");
		String inputJson;
		inputJson = mapToJson(loginRequest);
		RequestBuilder reqBuilder = MockMvcRequestBuilders.post(uri).
				contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson);
		MvcResult mvcResult = mvc.perform(reqBuilder).andReturn();
		int status = mvcResult.getResponse().getStatus();

		String content = mvcResult.getResponse().getContentAsString();
		 System.out.println(content);
		
	}
}
