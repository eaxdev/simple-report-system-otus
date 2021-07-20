package io.github.eaxdev.service;

import io.github.eaxdev.dto.ConnectionInfoResponse;
import io.github.eaxdev.exception.ConnectionNotFound;
import io.github.eaxdev.model.ConnectionInfo;
import io.github.eaxdev.repository.ConnectionInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConnectionService {

    private final ConnectionInfoRepository connectionInfoRepository;

    @Transactional(readOnly = true)
    public Page<ConnectionInfoResponse> getAllConnections(Pageable pageable) {
        Page<ConnectionInfo> connections = connectionInfoRepository.findAll(pageable);
        log.debug("All connections loaded. Count: {}", connections.getContent().size());
        return connections.map(it -> new ConnectionInfoResponse(
                it.getId(),
                it.getName(),
                it.getDatabase(),
                it.getDescription(),
                it.isActive()));
    }

    @Transactional
    public ConnectionInfo createNewConnection(ConnectionInfo connectionInfo) {
        log.debug("Create connection by connection info: {}", connectionInfo);
        var saved = connectionInfoRepository.save(connectionInfo);
        log.debug("Connection created: {}", connectionInfo);
        return saved;
    }

    @Transactional(readOnly = true)
    public ConnectionInfo findById(int connectionId) {
        return connectionInfoRepository.findById(connectionId).orElseThrow(() -> new ConnectionNotFound(connectionId));
    }

}
