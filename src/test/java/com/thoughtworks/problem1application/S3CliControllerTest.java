package com.thoughtworks.problem1application;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class S3CliControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void deveResponderAoEndpointS3() throws Exception {
        mockMvc.perform(post("/api/s3/bucket")
                .param("bucketName", "test-bucket"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/plain;charset=UTF-8"));
    }

    @Test
    void deveRetornarErroSemParametro() throws Exception {
        mockMvc.perform(post("/api/s3/bucket"))
                .andExpect(status().isBadRequest());
    }
}
