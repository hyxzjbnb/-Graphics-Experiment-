//package com.example.ex3_2_back.controller;
//
//import com.example.ex3_2_back.domain.Result;
//import com.example.ex3_2_back.service.AdminAuthService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockHttpServletResponse;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
//
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@ExtendWith(MockitoExtension.class)
//public class AdminAuthControllerTests {
//
//    @Mock
//    private AdminAuthService adminAuthService;
//
//    @InjectMocks
//    private AdminAuthController adminAuthController;
//
//    private MockMvc mockMvc;
//
//    @BeforeEach
//    void setUp() {
//        mockMvc = MockMvcBuilders.standaloneSetup(adminAuthController)
//                .setSingleView(new MappingJackson2JsonView())
//                .build();
//    }
//
//    @Test
//    void testLogin() throws Exception {
//        Result expectedResult = Result.success("Logged in successfully");
//        when(adminAuthService.login(anyString(), anyString(), any(MockHttpServletResponse.class))).thenReturn(expectedResult);
//
//        mockMvc.perform(post("/admin/login")
//                        .param("username", "admin")
//                        .param("password", "adminpass"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.message").value("Logged in successfully"));
//    }
//
//    @Test
//    void testValidateAndUpdateToken() throws Exception {
//        Result expectedResult = Result.success("Token is valid and updated");
//        when(adminAuthService.validateAndUpdateToken(any(), any())).thenReturn(expectedResult);
//
//        mockMvc.perform(get("/admin/validate-token"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.message").value("Token is valid and updated"));
//    }
//}
