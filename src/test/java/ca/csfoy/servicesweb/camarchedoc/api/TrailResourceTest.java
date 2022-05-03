package ca.csfoy.servicesweb.camarchedoc.api;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import ca.csfoy.servicesweb.camarchedoc.api.trail.TrailDto;
import ca.csfoy.servicesweb.camarchedoc.domain.trail.TrailDifficulty;
import ca.csfoy.servicesweb.camarchedoc.domain.trail.TrailStatus;

@SpringBootTest
@AutoConfigureMockMvc
public class TrailResourceTest {
    
    private static final String PATH_TO_TEST = "/trails";
    private static final String GOOD_PATH_TO_PUBLISH = PATH_TO_TEST + "/ready" + "/" + "1" + "/";
    private static final String BAD_PATH_TO_PUBLISH = PATH_TO_TEST + "/ready" + "/" + "2" + "/";
    private static final String CONTENT_TYPE = "application/json";
    
    private TrailDto dto1 = new TrailDto("1", "bonsoir1", "premier trail", "quebec", TrailDifficulty.FAMILY, LocalDate.of(1999, 12, 31), 
            LocalDate.of(2021, 12, 31), TrailStatus.IN_PREPARATION, null);

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    @WithMockUser(roles = "ADMIN")
    void validGetAllTrailReturn200OkAndTrails() throws Exception {        
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get(PATH_TO_TEST)
                  .contentType(CONTENT_TYPE))
                  .andExpect(MockMvcResultMatchers.status().isOk())           
                  .andReturn();     

        String responseAsString = result.getResponse().getContentAsString();
        Assertions.assertTrue(responseAsString.contains("bonsoir2"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void validCreateTrailReturn201CreatedAndCreatedTrail() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post(PATH_TO_TEST)
                  .contentType(CONTENT_TYPE)
                  .content(objectMapper.writeValueAsString(new TrailDto("t3", "name3", "a", "city3", TrailDifficulty.FAMILY, 
                          LocalDate.of(2021, 12, 1), LocalDate.of(2021, 12, 1), TrailStatus.IN_PREPARATION, null))))
                  .andExpect(MockMvcResultMatchers.status().isCreated())           
                  .andReturn();     

        String responseAsString = result.getResponse().getContentAsString();
        Assertions.assertTrue(responseAsString.contains("name3"));
    }
    
    @Test
    @WithMockUser(roles = "User")
    void validCreateTrailAsUserReturn403Unauthorized() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post(PATH_TO_TEST)
                  .contentType(CONTENT_TYPE)
                  .content(objectMapper.writeValueAsString(new TrailDto("t3", "name3", "a", "city3", TrailDifficulty.FAMILY, 
                          LocalDate.of(2021, 12, 1), LocalDate.of(2021, 12, 1), TrailStatus.IN_PREPARATION, null))))
                  .andExpect(MockMvcResultMatchers.status().isForbidden())           
                  .andReturn();     

        String responseAsString = result.getResponse().getContentAsString();
        Assertions.assertTrue(responseAsString.contains("Access Denied"));
    }
    
    @Test
    @WithMockUser(roles = "ADMIN")
    void invalidCreateTrailReturn422UnprocessableEntityAndErrorMessage() throws Exception { 
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post(PATH_TO_TEST)
                  .contentType(CONTENT_TYPE)
                  .content(objectMapper.writeValueAsString(dto1)))
                  .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())           
                  .andReturn();     

        String responseAsString = result.getResponse().getContentAsString();
        Assertions.assertTrue(responseAsString.contains("already exist"));
    }
    
    @Test
    @WithMockUser(roles = "ADMIN")
    void validReadyPublishTrailReturn204NoContent() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .put(GOOD_PATH_TO_PUBLISH)
                  .contentType(CONTENT_TYPE))
                  .andExpect(MockMvcResultMatchers.status().isNoContent())           
                  .andReturn();     

        String responseAsString = result.getResponse().getContentAsString();
        Assertions.assertEquals("", responseAsString);
    }
    
    @Test
    @WithMockUser(roles = "USER")
    void validReadyPublishTrailAsUserReturn403Forbidden() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .put(GOOD_PATH_TO_PUBLISH)
                  .contentType(CONTENT_TYPE))
                  .andExpect(MockMvcResultMatchers.status().isForbidden())           
                  .andReturn();   
        
        String responseAsString = result.getResponse().getContentAsString();
        Assertions.assertTrue(responseAsString.contains("Access Denied"));
    }
    
    @Test
    @WithMockUser(roles = "ADMIN")
    void invalidPreparationPublishTrailReturn400BadRequest() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .put(BAD_PATH_TO_PUBLISH)
                  .contentType(CONTENT_TYPE))
                  .andExpect(MockMvcResultMatchers.status().isBadRequest())           
                  .andReturn();     

        String responseAsString = result.getResponse().getContentAsString();
        Assertions.assertTrue(responseAsString.contains("already set to ready"));
    }
}
