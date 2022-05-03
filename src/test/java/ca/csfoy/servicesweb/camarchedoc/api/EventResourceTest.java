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

import ca.csfoy.servicesweb.camarchedoc.api.event.EventDto;
import ca.csfoy.servicesweb.camarchedoc.api.trail.TrailDto;
import ca.csfoy.servicesweb.camarchedoc.domain.trail.TrailDifficulty;
import ca.csfoy.servicesweb.camarchedoc.domain.trail.TrailStatus;

@SpringBootTest
@AutoConfigureMockMvc
public class EventResourceTest {
    
    private static final String PATH_TO_TEST = "/events";
    private static final String UPDATE_ID = "2";
    private static final String GET_ID = "2";
    private static final String DELETE_ID = "1";
    private static final String ANY_INVALID_ID = "9";
    private static final String CONTENT_TYPE = "application/json";
    
    private TrailDto trailDto = new TrailDto("1", "bonsoir1", "premier trail", "quebec", 
            TrailDifficulty.FAMILY, LocalDate.of(1999, 12, 31), LocalDate.of(2021, 12, 31), TrailStatus.READY, null);
    private EventDto dto1 = new EventDto(UPDATE_ID, "event de bob1", "un endroit magnifiiiiique", LocalDate.now().plusDays(1), trailDto, "bob1");
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    @WithMockUser(roles = "ADMIN")
    void validGetByIdEventReturn200Ok() throws Exception {        
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get(PATH_TO_TEST + "/" + GET_ID)
                  .contentType(CONTENT_TYPE))
                  .andExpect(MockMvcResultMatchers.status().isOk())           
                  .andReturn();     

        String responseAsString = result.getResponse().getContentAsString();
        Assertions.assertTrue(responseAsString.contains("event"));
    }
    
    @Test
    @WithMockUser(roles = "ADMIN")
    void validGetByIdEventReturn404NotFound() throws Exception {        
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get(PATH_TO_TEST + "/" + ANY_INVALID_ID)
                  .contentType(CONTENT_TYPE))
                  .andExpect(MockMvcResultMatchers.status().isNotFound())           
                  .andReturn();     

        String responseAsString = result.getResponse().getContentAsString();
        Assertions.assertTrue(responseAsString.contains("does not exist"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void validUpdateEventReturn204NoContent() throws Exception {        
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .put(PATH_TO_TEST + "/" + UPDATE_ID)
                  .contentType(CONTENT_TYPE)
                  .content(objectMapper.writeValueAsString(dto1)))
                  .andExpect(MockMvcResultMatchers.status().isNoContent())           
                  .andReturn();     

        String responseAsString = result.getResponse().getContentAsString();
        Assertions.assertEquals("", responseAsString);
    }
    
    @Test
    @WithMockUser(roles = "USER")
    void validUpdateEventAsUserReturn403Forbidden() throws Exception {        
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .put(PATH_TO_TEST + "/" + UPDATE_ID)
                  .contentType(CONTENT_TYPE)
                  .content(objectMapper.writeValueAsString(dto1)))
                  .andExpect(MockMvcResultMatchers.status().isForbidden())           
                  .andReturn();     

        String responseAsString = result.getResponse().getContentAsString();
        Assertions.assertTrue(responseAsString.contains("Access Denied"));
    }
    
    @Test
    @WithMockUser(roles = "ADMIN")
    void invalidUpdateEventReturn404NotFoundAndErrorMessage() throws Exception {        
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .put(PATH_TO_TEST + "/" + ANY_INVALID_ID)
                  .contentType(CONTENT_TYPE)
                  .content(objectMapper.writeValueAsString(new EventDto(ANY_INVALID_ID, "event de bob1", "un endroit magnifiiiiique", 
                          LocalDate.now().plusDays(1), trailDto, "bob1"))))
                  .andExpect(MockMvcResultMatchers.status().isNotFound())           
                  .andReturn();     
        
        String responseAsString = result.getResponse().getContentAsString();
        Assertions.assertTrue(responseAsString.contains("does not exist"));
    }
    
    @Test
    @WithMockUser(roles = "ADMIN")
    void validDeleteEventReturn204NoContent() throws Exception {        
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .delete(PATH_TO_TEST + "/" + DELETE_ID)
                  .contentType(CONTENT_TYPE))
                  .andExpect(MockMvcResultMatchers.status().isNoContent())           
                  .andReturn();     

        String responseAsString = result.getResponse().getContentAsString();
        Assertions.assertEquals("", responseAsString);
    }
    
    @Test
    @WithMockUser(roles = "USER")
    void validDeleteEventAsUserReturn403Unauthorized() throws Exception {        
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .delete(PATH_TO_TEST + "/" + DELETE_ID)
                  .contentType(CONTENT_TYPE))
                  .andExpect(MockMvcResultMatchers.status().isForbidden())           
                  .andReturn();     

        String responseAsString = result.getResponse().getContentAsString();
        Assertions.assertTrue(responseAsString.contains("Access Denied"));
    }
    
    @Test
    @WithMockUser(roles = "ADMIN")
    void invalidDeleteEventReturn404NotFoundAndErrorMessage() throws Exception {        
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .delete(PATH_TO_TEST + "/" + ANY_INVALID_ID)
                  .contentType(CONTENT_TYPE))
                  .andExpect(MockMvcResultMatchers.status().isNotFound())           
                  .andReturn();     

        String responseAsString = result.getResponse().getContentAsString();
        Assertions.assertTrue(responseAsString.contains("does not exist"));
    }
}
