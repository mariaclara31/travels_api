package com.api.travels_api.integrationtest;

import com.api.travels_api.enumeration.TravelTypeEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, MockitoTestExecutionListener.class })
public class TravelApiIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    public void contextLoad() {
        assertNotNull(mockMvc);
    }

    @Test
    @Order(2)
    public void sholudRetunrCreateTravel() throws Exception {
        JSONObject mapToCreate = setObjectToCreate();
        this.mockMvc.perform(post("/travels_api/travels").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(mapToCreate))).andExpect(status().isCreated());
    }
    @Test
    @Order(3)
    public void shouldReturnUpdateTravel() throws Exception {

        JSONObject mapToUpdate = setObjectToUpdate();
        this.mockMvc.perform(put("/travels_api/travels/1").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(mapToUpdate))).andExpect(status().isOk());
    }

    @Test
    @Order(4)
    public void shouldReturnGetAllTravels() throws Exception {
        this.mockMvc.perform(get("/travels_api/travels")).andExpect(status().isOk());
    }

    @Test
    @Order(5)
    public void shouldReturnRemoveAllTravels() throws Exception {
        this.mockMvc.perform(delete("/travels_api/travels")).andExpect(status().isNoContent());
    }

    @SuppressWarnings("unchecked")
    private JSONObject setObjectToCreate() {
        String startDate = "2021-01-10T08:00:00.312Z";
        String endDate = "2021-12-31T23:59:59.202Z";

        JSONObject map = new JSONObject();
        map.put("id", 1);
        map.put("orderNumber", "220788");
        map.put("amount", "22.88");
        map.put("type", TravelTypeEnum.RETURN.getValue());
        map.put("startDate", startDate);
        map.put("endDate", endDate);

        return map;
    }
    @SuppressWarnings("unchecked")
    private JSONObject setObjectToUpdate() {
        String startDate = "2021-01-10T08:00:00.312Z";

        JSONObject map = new JSONObject();
        map.put("id", 1L);
        map.put("orderNumber", "220788");
        map.put("amount", "22.88");
        map.put("type", TravelTypeEnum.RETURN.getValue());
        map.put("startDate", startDate);

        return map;
    }
}
