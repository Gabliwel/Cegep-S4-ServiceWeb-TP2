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

import ca.csfoy.servicesweb.camarchedoc.api.user.FullUserDto;
import ca.csfoy.servicesweb.camarchedoc.api.user.UserCredentialsDto;
import ca.csfoy.servicesweb.camarchedoc.domain.trail.TrailDifficulty;

@SpringBootTest
@AutoConfigureMockMvc
public class UserResourceTest {
    
    private static final String PATH_TO_TEST = "/users";
    private static final String PATH_TO_CREATE = PATH_TO_TEST + "/create";
    private static final String PATH_TO_LOGIN = PATH_TO_TEST + "/login";
    private static final String PATH_TO_SUGGESTED = "/suggested";
    private static final String GET_ID = "2";
    private static final String PUT_ID = "1";
    private static final String ANY_INVALID_ID = "9";
    private static final String CONTENT_TYPE = "application/json";
    
    private UserCredentialsDto credentialsDto1 = new UserCredentialsDto("Jean", "123allo");
    private FullUserDto userDto1 = new FullUserDto("10", "Jean", "Paul", "jeanpaul@gmail.com", "allo123testallo", TrailDifficulty.BEGINNER, null, null);
    private FullUserDto userDto2 = new FullUserDto("15", "Jeassn", "Passul", "jeanpasul@gmail.com", "allo123testallo", TrailDifficulty.BEGINNER, null, null);
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    void loginRouteAccessibleWithoutLogin() throws Exception {        
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post(PATH_TO_LOGIN)
                  .contentType("application/json")
                  .content(objectMapper.writeValueAsString(credentialsDto1)))
                  .andExpect(MockMvcResultMatchers.status().isForbidden())           
                  .andReturn();     

        String responseAsString = result.getResponse().getContentAsString();
        Assertions.assertTrue(responseAsString.contains("Invalid credentials"));
    }
    
    @Test
    void createRouteAccessibleWithoutLogin() throws Exception {        
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post(PATH_TO_CREATE)
                  .contentType("application/json")
                  .content(objectMapper.writeValueAsString(userDto1)))
                  .andExpect(MockMvcResultMatchers.status().isOk())           
                  .andReturn();     

        String responseAsString = result.getResponse().getContentAsString();
        Assertions.assertTrue(responseAsString.isEmpty());
    }
          
    @Test
    @WithMockUser(roles = "ADMIN")
    void getUserAccessibleWithAdmin() throws Exception {        
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                    .get(PATH_TO_TEST + "/" + GET_ID)
                      .contentType(CONTENT_TYPE))
                      .andExpect(MockMvcResultMatchers.status().isOk())           
                      .andReturn();     

            String responseAsString = result.getResponse().getContentAsString();
            Assertions.assertTrue(responseAsString.contains("Bob2"));
    }
    
    @Test
    @WithMockUser(roles = "ADMIN")
    void getUserSuggestedAccessibleWithAdmin() throws Exception {        
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                    .get(PATH_TO_TEST + "/" + GET_ID + PATH_TO_SUGGESTED)
                      .contentType(CONTENT_TYPE))
                      .andExpect(MockMvcResultMatchers.status().isOk())           
                      .andReturn();     

            String responseAsString = result.getResponse().getContentAsString();
            Assertions.assertTrue(responseAsString.contains("bonsoir2"));
    }
    
    @Test
    @WithMockUser(roles = "USER")
    void getUserSuggestedAccessibleWithUser() throws Exception {        
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                    .get(PATH_TO_TEST + "/" + GET_ID + PATH_TO_SUGGESTED)
                      .contentType(CONTENT_TYPE))
                      .andExpect(MockMvcResultMatchers.status().isOk())           
                      .andReturn();     

            String responseAsString = result.getResponse().getContentAsString();
            Assertions.assertTrue(responseAsString.contains("bonsoir2"));
    }
    
    @Test
    @WithMockUser(roles = "USER")
    void canModifyUserAsUser() throws Exception {        
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                    .put(PATH_TO_TEST + "/" + PUT_ID)
                    .contentType(CONTENT_TYPE)
                    .content(objectMapper.writeValueAsString(userDto2)))
                    .andExpect(MockMvcResultMatchers.status().isOk())           
                    .andReturn();      

            String responseAsString = result.getResponse().getContentAsString();
            Assertions.assertTrue(responseAsString.contains("Bob2"));
    }
    
    @Test
    @WithMockUser(roles = "ADMIN")
    void cannotModifyUserAsAdmin() throws Exception {        
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                    .put(PATH_TO_TEST + "/" + PUT_ID)
                      .contentType(CONTENT_TYPE)
                      .content(objectMapper.writeValueAsString(userDto2)))
                      .andExpect(MockMvcResultMatchers.status().isForbidden())           
                      .andReturn();     
    }

}
