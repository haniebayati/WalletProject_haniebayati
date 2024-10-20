package com.example.demo.test;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.WalletApplication;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.web.AuthenticationController;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(classes = WalletApplication.class)
@AutoConfigureMockMvc
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    
    @Test
    public void testUserAuthentication() throws Exception {
        // Test data
        User user = new User();
        user.setMobile("09120000000");

        // Mock behavior of userRepository
        Mockito.when(userRepository.save(any(User.class))).thenReturn(user);

        // Convert user object to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(user);

        // Send POST request
        mockMvc.perform(post("/walletAuthentication/users_authentication")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk());
    }

    
    @Test
    public void testVerifyAuthentication() throws Exception {
        // Test data
        User user = new User();
        user.setMobile("09120000000");
        user.setOtpCode(123456);
        user.setOtpTime(LocalDateTime.now());

        // Mock behavior of userRepository
        Mockito.when(userRepository.findbyMobile("09120000000")).thenReturn(Optional.of(user));

        // Request data with valid OTP code
        User requestUser = new User();
        requestUser.setMobile("09120000000");
        requestUser.setOtpCode(123456);

        // Convert requestUser object to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(requestUser);

        // Send POST request for verification
        mockMvc.perform(post("/walletAuthentication/verify_authentication")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Authentication successful"))
                .andExpect(jsonPath("$.token").exists());
    }

    
    @Test
    public void testVerifyAuthenticationInvalidOtp() throws Exception {
        // Test data
        User user = new User();
        user.setMobile("09120000000");
        user.setOtpCode(123456); // correct OTP
        user.setOtpTime(LocalDateTime.now());

        // Mock behavior of userRepository
        Mockito.when(userRepository.findbyMobile("09120000000")).thenReturn(Optional.of(user));

        // Mock generateOtp method in AuthenticationController
        AuthenticationController authController = Mockito.spy(new AuthenticationController());
        Mockito.doReturn(123456).when(authController).generateOtp();  // Generate a fixed OTP

        // Sending request with invalid OTP
        User requestUser = new User();
        requestUser.setMobile("09120000000");
        requestUser.setOtpCode(654321); // incorrect OTP

        // Convert requestUser to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(requestUser);

        // Send POST request for verification
        mockMvc.perform(post("/walletAuthentication/verify_authentication")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().is4xxClientError()); // Should return an error
    }


    
    @Test
    public void testVerifyAuthenticationOtpExpired() throws Exception {
        // Test data
        User user = new User();
        user.setMobile("09120000000");
        user.setOtpCode(123456);
        user.setOtpTime(LocalDateTime.now().minusMinutes(2)); // Expired OTP (2 minutes old)

        // Mock behavior of userRepository
        Mockito.when(userRepository.findbyMobile("09120000000")).thenReturn(Optional.of(user));

        // Request data with valid OTP code
        User requestUser = new User();
        requestUser.setMobile("09120000000");
        requestUser.setOtpCode(123456);

        // Convert requestUser object to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(requestUser);

        // Send POST request for verification
        mockMvc.perform(post("/walletAuthentication/verify_authentication")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().is4xxClientError()); // Should return an error for expired OTP
    }

 
    @Test
    public void testGenerateAndVerifyJwtToken() throws Exception {
        // Test JWT generation
        AuthenticationController authController = new AuthenticationController();
        String jwtToken = authController.generateToken("09120000000");

        // Verify JWT
        DecodedJWT decodedJWT = AuthenticationController.verifyToken(jwtToken);

        // Check that the subject of the JWT is correct (mobile number)
        assert(decodedJWT.getSubject().equals("09120000000"));
    }
}
