package io.github.eaxdev.service;

import io.github.eaxdev.exception.ReportNotFound;
import io.github.eaxdev.model.Report;
import io.github.eaxdev.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;

    @Transactional
    public Report createOrUpdate(Report report) {
        return reportRepository.save(report);
    }

    @Transactional(readOnly = true)
    public Page<Report> findAll(Pageable pageable) {
        return reportRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Report findById(int id) {
        return reportRepository.findById(id).orElseThrow(() -> new ReportNotFound(id));
    }

    @Transactional
    public void deleteById(int reportId) {
        reportRepository.deleteById(reportId);
    }
}
