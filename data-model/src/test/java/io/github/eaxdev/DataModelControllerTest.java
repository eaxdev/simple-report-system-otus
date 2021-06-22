package io.github.eaxdev;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import io.github.eaxdev.repository.DataModelRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DBRider
@SpringJUnitConfig
@AutoConfigureMockMvc
@SpringBootTest(classes = {TestConfig.class, DataModelConfig.class})
class DataModelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DataModelRepository dataModelRepository;

    @AfterEach
    void tearDown() {
        dataModelRepository.deleteAll();
    }

    @Test
    @DisplayName("Get all data models")
    @DataSet(value = "datasets/DataModelDataSet.yml")
    void getAll() throws Exception {
        var actual = mockMvc.perform(get("/models")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expected = readFileAsString("testData/GetAllDataModelsResponse.json");
        JSONAssert.assertEquals(expected, actual, true);
    }

    @Test
    @DisplayName("Get data model by id")
    @DataSet(value = "datasets/DataModelDataSet.yml")
    void getById() throws Exception {
        var actual = mockMvc.perform(get("/models/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expected = readFileAsString("testData/GetDataModelByIdResponse.json");
        JSONAssert.assertEquals(expected, actual, true);
    }

    @Test
    @DisplayName("Delete data model by id")
    @DataSet(value = "datasets/DataModelDataSet.yml")
    void deleteById() throws Exception {
        mockMvc.perform(delete("/models/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Create new data model")
    void create() throws Exception {
        String request = readFileAsString("testData/CreateNewDataModelRequest.json");
        var actual = mockMvc.perform(post("/models")
                .content(request)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expected = readFileAsString("testData/CreateDataModelResponse.json");
        JSONAssert.assertEquals(expected, actual, new CustomComparator(JSONCompareMode.STRICT,
                new Customization("id", (o1, o2) -> true)));
    }

}
