package io.github.eaxdev.controller;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import io.github.eaxdev.ConnectionManagerConfig;
import io.github.eaxdev.TestConfig;
import io.github.eaxdev.repository.ConnectionInfoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;

import static io.github.eaxdev.TestUtil.readFileAsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DBRider
@SpringJUnitConfig
@AutoConfigureMockMvc
@SpringBootTest(classes = {TestConfig.class, ConnectionManagerConfig.class})
class ConnectionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ConnectionInfoRepository connectionInfoRepository;

    @AfterEach
    void tearDown() {
        connectionInfoRepository.deleteAll();
    }

    @Test
    @DataSet(value = "datasets/ConnectionInfoDataset.yml")
    void shouldGetAllConnection() throws Exception {
        var actual = mockMvc.perform(get("/connections")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expected = readFileAsString("testData/GetAllConnectionsResponse.json");
        JSONAssert.assertEquals(expected, actual, true);
    }

    @Test
    void shouldCreateNewConnection() throws Exception {
        String request = readFileAsString("testData/CreateNewConnectionRequest.json");
        var actual = mockMvc.perform(post("/connections")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expected = readFileAsString("testData/CreateConnectionResponse.json");
        JSONAssert.assertEquals(expected, actual, new CustomComparator(JSONCompareMode.STRICT,
                new Customization("connectionId", (o1, o2) -> true)));
    }

}
