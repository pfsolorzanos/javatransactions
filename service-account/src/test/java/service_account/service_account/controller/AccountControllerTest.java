package service_account.service_account.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import service_account.service_account.service.AccountService;
import service_account.service_account.utils.dto.AccountTO;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
@ExtendWith(MockitoExtension.class)
class AccountControllerTest {

    public static final String CLIENT_UUID = "728555c5-e3ca-4b8a-919c-855745b1dbc2";
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AccountService accountService;

    @Autowired
    private ObjectMapper objectMapper;

    private AccountTO accountTO;

    @BeforeEach
    void setUp() {
        accountTO = AccountTO.builder()
                .type("SAVINGS")
                .number("12345")
                .state(true)
                .balance(BigDecimal.valueOf(1000))
                .clientId(CLIENT_UUID)
                .build();
    }

    @Test
    void testGetById_ShouldReturnAccount() throws Exception {
        when(accountService.getById("SAVINGS", "12345")).thenReturn(accountTO);

        mockMvc.perform(get("/api/cuentas/SAVINGS/12345"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value("SAVINGS"))
                .andExpect(jsonPath("$.number").value("12345"));
    }

    @Test
    void testGetAll_ShouldReturnListOfAccounts() throws Exception {
        when(accountService.getAll()).thenReturn(List.of(accountTO));

        mockMvc.perform(get("/api/cuentas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type").value("SAVINGS"))
                .andExpect(jsonPath("$[0].number").value("12345"));
    }

    @Test
    void testCreate_ShouldReturnCreatedAccount() throws Exception {
        when(accountService.createAccount(any())).thenReturn(accountTO);

        mockMvc.perform(post("/api/cuentas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value("SAVINGS"))
                .andExpect(jsonPath("$.number").value("12345"));
    }

    @Test
    void testUpdate_ShouldReturnUpdatedAccount() throws Exception {
        when(accountService.updateAccount(any(), eq("SAVINGS"), eq("12345"))).thenReturn(accountTO);

        mockMvc.perform(put("/api/cuentas/SAVINGS/12345")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value("SAVINGS"))
                .andExpect(jsonPath("$.number").value("12345"));
    }

    @Test
    void testDelete_ShouldReturnNoContent() throws Exception {
        doNothing().when(accountService).deleteAccount("SAVINGS", "12345");

        mockMvc.perform(delete("/api/cuentas/SAVINGS/12345"))
                .andExpect(status().isOk());
    }
}