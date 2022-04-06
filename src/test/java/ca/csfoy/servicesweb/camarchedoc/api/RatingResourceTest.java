package ca.csfoy.servicesweb.camarchedoc.api;

import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import ca.csfoy.servicesweb.camarchedoc.api.rating.RatingDto;
import ca.csfoy.servicesweb.camarchedoc.api.trail.TrailDto;
import ca.csfoy.servicesweb.camarchedoc.api.user.UserDto;
import ca.csfoy.servicesweb.camarchedoc.domain.trail.TrailDifficulty;

@SpringBootTest
@AutoConfigureMockMvc
public class RatingResourceTest {
    
    private static final String PATH_TO_TEST = "/ratings";
    private static final String SEARCH_PATH = "/trailSearch";
    private static final String GET_ID_RATING = "1";
    private static final String GET_INVALID_ID_RATING = "999";
    private static final String GET_BAD_ID_RATING = "A";
 
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
 
    @Test
    void validGetByRatingIdEventReturn200Ok() throws Exception {        
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get(PATH_TO_TEST + "/" + GET_ID_RATING)
                  .contentType("application/json"))
                  .andExpect(MockMvcResultMatchers.status().isOk())           
                  .andReturn();     

        String responseAsString = result.getResponse().getContentAsString();
        Assertions.assertTrue(responseAsString.contains("ark!"));
    }
    
    @Test
    void invalidGetByRatingIdEventReturn404NotFound() throws Exception {        
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get(PATH_TO_TEST + "/" + GET_INVALID_ID_RATING)
                  .contentType("application/json"))
                  .andExpect(MockMvcResultMatchers.status().isNotFound())           
                  .andReturn();     

        String responseAsString = result.getResponse().getContentAsString();
        Assertions.assertTrue(responseAsString.contains("does not exist"));
    }
    
    @Test
    void cantPassValidationGetByRatingIdEventReturn422UnprocessableEntity() throws Exception {        
        mockMvc.perform(MockMvcRequestBuilders
                .get(PATH_TO_TEST + "/" + GET_BAD_ID_RATING)
                  .contentType("application/json"))
                  .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())           
                  .andReturn();
    }
    
    @Test
    void validGetByTrailIdEventReturn200Ok() throws Exception {        
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get(PATH_TO_TEST + SEARCH_PATH + "/" + GET_ID_RATING)
                  .contentType("application/json"))
                  .andExpect(MockMvcResultMatchers.status().isOk())           
                  .andReturn();     

        String responseAsString = result.getResponse().getContentAsString();
        Assertions.assertTrue(responseAsString.contains("ark!"));
    }
    
    @Test
    void invalidGetByTrailIdEventReturn404NotFound() throws Exception {        
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get(PATH_TO_TEST + SEARCH_PATH + "/" + GET_INVALID_ID_RATING)
                  .contentType("application/json"))
                  .andExpect(MockMvcResultMatchers.status().isNotFound())           
                  .andReturn();     

        String responseAsString = result.getResponse().getContentAsString();
        Assertions.assertTrue(responseAsString.contains("does not exist"));
    }
    
    @Test
    void cantPassValidationGetByTrailIdEventReturn422UnprocessableEntity() throws Exception {        
        mockMvc.perform(MockMvcRequestBuilders
                .get(PATH_TO_TEST + SEARCH_PATH + "/" + GET_BAD_ID_RATING)
                  .contentType("application/json"))
                  .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())           
                  .andReturn();
    }
    
    @Test
    void cantPassValidationCreateRatingEventReturn422UnprocessableEntity() throws Exception {        
        mockMvc.perform(MockMvcRequestBuilders
                .post(PATH_TO_TEST)
                 .contentType("application/json")
                 .content(objectMapper.writeValueAsString(new RatingDto("", null, null, 5.0, ""))))
                  .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())           
                  .andReturn();
    }
    
    @Test
    void validCreateRatingEventReturn201Created() throws Exception {        
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post(PATH_TO_TEST)
                 .contentType("application/json")
                 .content(objectMapper.writeValueAsString(new RatingDto("1", new UserDto("1", "Bob", "JSP", TrailDifficulty.NOT_RATED_YET, Set.of(), Set.of()), 
                         new TrailDto("1", "bonsoir1", null, null, null, null, null, null, null), 4.0, "WoooooooooooW"))))
                  .andExpect(MockMvcResultMatchers.status().isCreated())           
                  .andReturn();
        
        String responseAsString = result.getResponse().getContentAsString();
        Assertions.assertTrue(responseAsString.contains("WoooooooooooW"));
    }
}
