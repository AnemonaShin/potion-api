package cl.potion.api.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import cl.potion.api.config.SecurityConfig;
import cl.potion.api.entity.UserEntity;
import cl.potion.api.exception.ExceptionList;
import cl.potion.api.exception.ServiceException;
import cl.potion.api.dto.request.UserRequest;
import cl.potion.api.dto.response.DefaultResponse;
import cl.potion.api.service.user.UserService;

@WebMvcTest(UserController.class)
@Import(SecurityConfig.class)
@DisplayName("UserController Tests")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    private final com.fasterxml.jackson.databind.ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();

    private UserRequest userRequest;
    private DefaultResponse successResponse;
    private UserEntity activeUser;

    @BeforeEach
    void setUp() {
        LocalDateTime now = LocalDateTime.now();

        userRequest = UserRequest.builder()
                .username("testuser")
                .password("password123")
                .email("test@example.com")
                .build();

        successResponse = DefaultResponse.builder()
                .code("200")
                .message("OK")
                .build();

        activeUser = UserEntity.builder()
                .id(BigInteger.ONE)
                .username("testuser")
                .password("hashedpassword")
                .email("test@example.com")
                .createAt(now)
                .updatedAt(now)
                .active(true)
                .build();
    }

    @Test
    @DisplayName("POST /v1/users/ should register a user")
    void testRegisterUser() throws Exception {
        doReturn(successResponse).when(userService).registerUser(any(UserRequest.class));

        mockMvc.perform(post("/v1/users/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("OK"));
    }

    @Test
    @DisplayName("GET /v1/users/{username} should return user data")
    void testSearchByUsername() throws Exception {
        DefaultResponse response = DefaultResponse.builder()
                .code("200")
                .message("USER SEARCHED")
                .response(activeUser)
                .build();

        doReturn(response).when(userService).searchByUsername("testuser");

        mockMvc.perform(get("/v1/users/testuser"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("USER SEARCHED"));
    }

    @Test
    @DisplayName("GET /v1/users/ should return paginated users")
    void testGetAllUsers() throws Exception {
        var page = new PageImpl<>(List.of(activeUser), PageRequest.of(0, 10), 1);
        doReturn(page).when(userService).getAllUsers(any(PageRequest.class));

        mockMvc.perform(get("/v1/users/")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].username").value("testuser"));
    }

    @Test
    @DisplayName("DELETE /v1/users/{user_id} should deactivate a user")
    void testDeactivateUser() throws Exception {
        doReturn(successResponse).when(userService).deactivateUser(BigInteger.ONE);

        mockMvc.perform(delete("/v1/users/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("PATCH /v1/users/{user_id}/activate should activate a user")
    void testActivateUser() throws Exception {
        doReturn(successResponse).when(userService).activateUser(BigInteger.ONE);

        mockMvc.perform(patch("/v1/users/1/activate"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("PUT /v1/users/{user_id} should update a user")
    void testUpdateUser() throws Exception {
        DefaultResponse response = DefaultResponse.builder()
                .code("200")
                .message("USER UPDATED")
                .response(activeUser)
                .build();

        doReturn(response).when(userService).updateUser(eq(BigInteger.ONE), any(UserRequest.class));

        mockMvc.perform(put("/v1/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("USER UPDATED"));
    }

    @Test
    @DisplayName("GET /v1/users/{username} should return error when user not found")
    void testSearchByUsernameNotFound() throws Exception {
        doThrow(new ServiceException(ExceptionList.UNFD)).when(userService).searchByUsername("unknown");

        mockMvc.perform(get("/v1/users/unknown"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("404"))
                .andExpect(jsonPath("$.message").value("USER NOT FOUND"));
    }
}
