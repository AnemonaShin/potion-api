package cl.potion.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import cl.potion.api.config.SecurityConfig;

@WebMvcTest(PotionController.class)
@Import(SecurityConfig.class)
@DisplayName("PotionController Tests")
class PotionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /v1/potion should return 404 since no endpoints are defined")
    void testNoEndpointsDefined() throws Exception {
        mockMvc.perform(get("/v1/potion"))
                .andExpect(status().isNotFound());
    }
}
