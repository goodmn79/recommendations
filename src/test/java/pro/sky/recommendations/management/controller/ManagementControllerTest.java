package pro.sky.recommendations.management.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import pro.sky.recommendations.management.dto.InfoManager;
import pro.sky.recommendations.management.service.ManagementService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ManagementController.class)
class ManagementControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private ManagementService managementService;

    private final String url = "http://localhost:8080/management/";

    @Test
    void clearCacheTest() throws Exception {
        doNothing().when(managementService).clearCache();

        mockMvc.perform(post(url + "clear-cache"))
                .andExpect(status().isOk());

        verify(managementService, times(1)).clearCache();
    }

    @Test
    void infoTest() throws Exception {
        String name = "test_api";
        String version = "test_api_version";
        InfoManager testInfo = new InfoManager()
                .setName(name)
                .setVersion("test_api_version");
        when(managementService.getInfo()).thenReturn(testInfo);

        mockMvc.perform(get(url + "info"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.version").value(version));

        verify(managementService, times(1)).getInfo();

    }
}