package it.macgood.jsonplaceholdervk.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.macgood.jsonplaceholdervk.audit.AuditRepository;
import it.macgood.jsonplaceholdervk.users.entity.UserRequest;
import it.macgood.jsonplaceholdervk.users.entity.UserResponse;
import it.macgood.jsonplaceholdervk.users.entity.fields.Address;
import it.macgood.jsonplaceholdervk.users.entity.fields.Company;
import it.macgood.jsonplaceholdervk.users.entity.fields.Geolocation;
import it.macgood.jsonplaceholdervk.users.service.UsersService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UsersRestController.class)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@WithMockUser(username = "user", roles = {"ADMIN"})
public class UsersRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UsersService service;

    @MockBean
    private JdbcTemplate jdbcTemplate;

    @MockBean
    private RestTemplate restTemplate;

    @MockBean
    private AuditRepository auditRepository;


    @Test
    public void getAllUsersTest_shouldReturnOk() throws Exception {
        List<UserResponse> mockData = getUserResponses();

        ResponseEntity<String> mockResponse = new ResponseEntity<>(
                objectMapper.writeValueAsString(mockData),
                HttpStatus.OK
        );

        when(restTemplate.getForEntity(anyString(), eq(String.class)))
                .thenReturn(mockResponse);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/users")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON);
        ResultActions perform = mockMvc.perform(requestBuilder);
        System.out.println(perform.andReturn().getResponse().getContentAsString());
    }

    @Test
    public void postUsersTest_shouldReturnOk() throws Exception {

        UserRequest userResponse = new UserRequest(
                "name",
                "username",
                "email",
                new Address("street", "suite", "city", "123423",
                        new Geolocation("58.83", "18.23")), "phone", "website",
                new Company(
                        "OOO Roga and Copyta", "Copy It, ah?", "pay-per-use"
                )
        );
        String userResponseJson = objectMapper.writeValueAsString(userResponse);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/users")
                .with(csrf())
                .content(userResponseJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        MockHttpServletResponse response = result.getResponse();

        System.out.println(response.getContentAsString());

    }

    @Test
    public void patchUsersTest_shouldReturnOk() throws Exception {

        UserPatchRequest request = new UserPatchRequest("1234", "sdfajkl");
        String userResponseJson = objectMapper.writeValueAsString(request);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.patch("/api/v1/users/1")
                .with(csrf())
                .content(userResponseJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        MockHttpServletResponse response = result.getResponse();

        System.out.println(response.getContentAsString());

    }
    @Test
    public void putUsersTest_shouldReturnOk() throws Exception {

        UserPatchRequest request = new UserPatchRequest("1234", "sdfajkl");
        String userResponseJson = objectMapper.writeValueAsString(request);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.patch("/api/v1/users/1")
                .with(csrf())
                .content(userResponseJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        MockHttpServletResponse response = result.getResponse();

        System.out.println(response.getContentAsString());

    }

    @Test
    public void deleteUsersTest_shouldReturnOk() throws Exception {

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/v1/users/1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        MockHttpServletResponse response = result.getResponse();

        System.out.println(response.getContentAsString());

    }

    private static List<UserResponse> getUserResponses() {
        List<UserResponse> mockData = new ArrayList<>();

        UserResponse userResponse = new UserResponse(
                1,
                "name",
                "username",
                "email",
                new Address("street", "suite", "city", "123423",
                        new Geolocation("58.83", "18.23")), "phone", "website",
                new Company(
                        "OOO Roga and Copyta", "Copy It, ah?", "pay-per-use"
                )
        );
        mockData.add(userResponse);
        return mockData;
    }

}


record UserPatchRequest(String name, String username) {

}