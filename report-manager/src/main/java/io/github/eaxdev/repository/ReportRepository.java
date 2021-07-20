package io.github.eaxdev.repository;

import io.github.eaxdev.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Integer> {
}
