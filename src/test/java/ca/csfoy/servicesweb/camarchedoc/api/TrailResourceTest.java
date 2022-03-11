package ca.csfoy.servicesweb.camarchedoc.api;

import java.time.LocalDate;


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

import ca.csfoy.servicesweb.camarchedoc.domain.TrailDifficulty;
import ca.csfoy.servicesweb.camarchedoc.domain.TrailStatus;

@SpringBootTest
@AutoConfigureMockMvc
public class TrailResourceTest {
    
    private static final String PATH_TO_TEST = "/trails";
    
    private TrailDto dto1 = new TrailDto("1", "bonsoir1", "premier trail", "quebec", TrailDifficulty.FAMILY, LocalDate.of(1999, 12, 31), LocalDate.of(2021, 12, 31), TrailStatus.READY);

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    void validGetAllTrailReturn200OkAndTrails() throws Exception {        
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get(PATH_TO_TEST)
                  .contentType("application/json"))
                  .andExpect(MockMvcResultMatchers.status().isOk())           
                  .andReturn();     

        String responseAsString = result.getResponse().getContentAsString();
        Assertions.assertTrue(responseAsString.contains("bonsoir1"));
    }

    @Test
    void validCreateTrailReturn201CreatedAndCreatedTrail() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post(PATH_TO_TEST)
                  .contentType("application/json")
                  .content(objectMapper.writeValueAsString(new TrailDto("t3", "name3", "a", "city3", TrailDifficulty.FAMILY, LocalDate.of(2021, 12, 1), LocalDate.of(2021, 12, 1), TrailStatus.READY))))
                  .andExpect(MockMvcResultMatchers.status().isCreated())           
                  .andReturn();     

        String responseAsString = result.getResponse().getContentAsString();
        Assertions.assertTrue(responseAsString.contains("name3"));
    }
    
    @Test
    void invalidCreateTrailReturn422UnprocessableEntityAndErrorMessage() throws Exception { 
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post(PATH_TO_TEST)
                  .contentType("application/json")
                  .content(objectMapper.writeValueAsString(dto1)))
                  .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())           
                  .andReturn();     

        String responseAsString = result.getResponse().getContentAsString();
        Assertions.assertTrue(responseAsString.contains("already exist"));
    }
}
