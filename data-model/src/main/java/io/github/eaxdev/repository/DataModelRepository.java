package io.github.eaxdev.repository;

import io.github.eaxdev.model.DataModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataModelRepository extends JpaRepository<DataModel, Integer> {
}
