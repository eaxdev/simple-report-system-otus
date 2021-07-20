package io.github.eaxdev.repository;

import io.github.eaxdev.model.ConnectionInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ConnectionInfoRepository extends JpaRepository<ConnectionInfo, Integer> {

    @Override
    @Query("SELECT ci FROM ConnectionInfo ci LEFT JOIN FETCH ci.connectionProperties WHERE ci.id = :id")
    Optional<ConnectionInfo> findById(@Param("id") Integer id);
}
