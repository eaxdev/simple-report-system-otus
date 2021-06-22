package io.github.eaxdev.service;

import io.github.eaxdev.model.Report;
import io.github.eaxdev.service.executor.Executor;
import io.github.eaxdev.service.executor.ExecutorFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportViewService {

    private final ReportService reportService;

    private final ExecutorFactory executorFactory;

    @Transactional(readOnly = true)
    public Page<Map<String, Object>> getReportData(int reportId, Pageable pageable) {
        var report = reportService.findById(reportId);

        var sorted = "";
        if (pageable.getSort().isSorted()) {
            sorted = pageable.getSort().stream()
                    .map(it -> it.getProperty() + " " + it.getDirection().name())
                    .collect(Collectors.joining(", "));
        }

        var dataQuery = report.getQuery() + " " + sorted + " LIMIT " + pageable.getPageSize() + " OFFSET " + pageable.getOffset();
        var executor = executorFactory.getExecutor(report.getModelId());
        return new PageImpl<>(executor.getData(dataQuery), pageable, executor.getCount(report.getCountQuery()));
    }

}
