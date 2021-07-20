package io.github.eaxdev;

import com.github.database.rider.core.replacers.Replacer;
import io.github.eaxdev.controller.ReportViewControllerTest;
import org.dbunit.dataset.ReplacementDataSet;

public class DataSetValueReplacer implements Replacer {

    @Override
    public void addReplacements(ReplacementDataSet dataSet) {
        dataSet.addReplacementSubstring("{clickhouse-host}", ReportViewControllerTest.getContainerIpAddress());
        dataSet.addReplacementSubstring("{clickhouse-port}", String.valueOf(ReportViewControllerTest.getContainerPort()));
    }

}
