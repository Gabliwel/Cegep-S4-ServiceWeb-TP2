package ca.csfoy.servicesweb.camarchedoc.api;

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

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(roles = "ADMIN")
public class BadgeResourceTest {
    
    private static final String PATH_TO_TEST = "/badges";
    private static final String VALID_BADGE_ID = "1";
    private static final String INVALID_BADGE_ID = "99";
    private static final String VALID_USER_ID = "2";
    private static final String INVALID_USER_ID = "123456";
    private static final String CONTENT_TYPE = "application/json";
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    void getAllBagdesReturn200Ok() throws Exception {        
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get(PATH_TO_TEST)
                  .contentType(CONTENT_TYPE))
                  .andExpect(MockMvcResultMatchers.status().isOk())           
                  .andReturn();     

        String responseAsString = result.getResponse().getContentAsString();
        Assertions.assertTrue(responseAsString.contains("Thor God of Thunder"));
    }
    
    @Test
    void validGetByIdBagdeReturn200Ok() throws Exception {        
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get(PATH_TO_TEST + "/" + VALID_BADGE_ID)
                  .contentType(CONTENT_TYPE))
                  .andExpect(MockMvcResultMatchers.status().isOk())           
                  .andReturn();     

        String responseAsString = result.getResponse().getContentAsString();
        Assertions.assertTrue(responseAsString.contains("Thor God of Thunder"));
    }
    
    @Test
    void invalidGetByIdBadgeReturn404NotFound() throws Exception {        
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get(PATH_TO_TEST + "/" + INVALID_BADGE_ID)
                  .contentType(CONTENT_TYPE))
                  .andExpect(MockMvcResultMatchers.status().isNotFound())           
                  .andReturn();     

        String responseAsString = result.getResponse().getContentAsString();
        Assertions.assertTrue(responseAsString.contains("does not exist"));
    }
    
    @Test
    void validAddBagdeReturn204NoContent() throws Exception {        
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .put(PATH_TO_TEST + "/" + VALID_BADGE_ID + "?addTo=" + VALID_USER_ID)
                  .contentType(CONTENT_TYPE))
                  .andExpect(MockMvcResultMatchers.status().isNoContent())           
                  .andReturn();     

        String responseAsString = result.getResponse().getContentAsString();
        Assertions.assertEquals("", responseAsString);
    }
    
    @Test
    void invalidAddBagdeReturn404NotFound1() throws Exception {        
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .put(PATH_TO_TEST + "/" + INVALID_BADGE_ID + "?addTo=" + VALID_USER_ID)
                  .contentType(CONTENT_TYPE))
                  .andExpect(MockMvcResultMatchers.status().isNotFound())           
                  .andReturn();     

        String responseAsString = result.getResponse().getContentAsString();
        Assertions.assertTrue(responseAsString.contains("does not exist"));
    }
    
    @Test
    void invalidAddBagdeReturn404NotFound2() throws Exception {        
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .put(PATH_TO_TEST + "/" + VALID_BADGE_ID + "?addTo=" + INVALID_USER_ID)
                  .contentType(CONTENT_TYPE))
                  .andExpect(MockMvcResultMatchers.status().isNotFound())           
                  .andReturn();     

        String responseAsString = result.getResponse().getContentAsString();
        Assertions.assertTrue(responseAsString.contains("does not exist"));
    }

}
