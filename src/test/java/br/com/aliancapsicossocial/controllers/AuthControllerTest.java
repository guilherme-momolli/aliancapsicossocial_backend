//package br.com.aliancapsicossocial.controllers;
//
//import br.com.aliancapsicossocial.dtos.auth.*;
//import br.com.aliancapsicossocial.services.AuthService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(AuthController.class)
//@AutoConfigureMockMvc(addFilters = false)
//class AuthControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @MockitoBean
//    private AuthService authService;
//
//    @Test
//    void deveFazerLoginComSucesso() throws Exception {
//        var response = new TokenResponseDTO("access-token", "refresh-token", false);
//        var request = new LoginRequestDTO("user@example.com", "password");
//
//        when(authService.login(any(LoginRequestDTO.class))).thenReturn(response);
//
//        mockMvc.perform(post("/api/v1/auth/login")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.accessToken").value("access-token"))
//                .andExpect(jsonPath("$.refreshToken").value("refresh-token"))
//                .andExpect(jsonPath("$.requires2FA").value(false));
//    }
//
//    @Test
//    void deveSolicitar2FA() throws Exception {
//        var response = new TokenResponseDTO(null, null, true);
//        var request = new LoginRequestDTO("user@example.com", "password");
//
//        when(authService.login(any(LoginRequestDTO.class))).thenReturn(response);
//
//        mockMvc.perform(post("/api/v1/auth/login")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.requires2FA").value(true));
//    }
//
//    @Test
//    void deveVerificar2FA() throws Exception {
//        var response = new TokenResponseDTO("access-token", "refresh-token", false);
//        var request = new TwoFactorVerifyDTO("user@example.com", "123456");
//
//        when(authService.verify2FA(any(TwoFactorVerifyDTO.class))).thenReturn(response);
//
//        mockMvc.perform(post("/api/v1/auth/verify-2fa")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.accessToken").value("access-token"));
//    }
//}
