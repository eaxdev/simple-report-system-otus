package io.github.eaxdev.controller;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import io.github.eaxdev.ReportViewConfig;
import io.github.eaxdev.TestConfig;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.ClickHouseContainer;
import org.testcontainers.utility.MountableFile;

import static io.github.eaxdev.TestUtil.readFileAsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DBRider
@SpringJUnitConfig
@AutoConfigureMockMvc
@SpringBootTest(classes = {TestConfig.class, ReportViewConfig.class})
public class ReportViewControllerTest {

    public static final ClickHouseContainer clickhouse = (ClickHouseContainer) new ClickHouseContainer("yandex/clickhouse-server")
            .withCopyFileToContainer(MountableFile.forClasspathResource("ontime.sql"), "/docker-entrypoint-initdb.d/ontime.sql");

    @BeforeAll
    static void beforeAll() {
        clickhouse.start();
    }

    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    @DisplayName("Get report data")
    @DataSet(value = {
            "datasets/ConnectionInfoDataset.yml",
            "datasets/DataModelDataSet.yml",
            "datasets/ReportDataSet.yml"
    })
    void tryToGetData() {
        var actual = mockMvc.perform(get("/data/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String expected = readFileAsString("testData/ReportDataResponse.json");
        JSONAssert.assertEquals(expected, actual, true);
    }

    public static String getContainerIpAddress() {
        return clickhouse.getContainerIpAddress();
    }

    public static Integer getContainerPort() {
        return clickhouse.getFirstMappedPort();
    }
}
