package io.github.eaxdev.mapper;

import io.github.eaxdev.dto.ConnectionInfoRequest;
import io.github.eaxdev.dto.ConnectionInfoResponse;
import io.github.eaxdev.dto.ConnectionPropertyRequest;
import io.github.eaxdev.model.ConnectionInfo;
import io.github.eaxdev.model.ConnectionProperty;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public abstract class ConnectionInfoMapper {

    @Mapping(target = "description", source = "description")
    @Mapping(target = "connectionId", source = "id")
    @Mapping(target = "connectionName", source = "name")
    public abstract ConnectionInfoResponse toConnectionInfoResponse(ConnectionInfo source);

    @Mapping(target = "id", source = "connectionId")
    @Mapping(target = "name", source = "connectionName")
    @Mapping(target = "user", source = "userName")
    @Mapping(target = "password", source = "plainPassword")
    @Mapping(target = "active", expression = "java(true)")
    @Mapping(target = "createDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "connectionProperties", source = "connectionProperties")
    protected abstract ConnectionInfo toConnectionInfo(ConnectionInfoRequest source, @Context CustomContext context);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "value", source = "value")
    @Mapping(target = "connectionInfo", ignore = true)
    protected abstract ConnectionProperty toConnectionProperty(ConnectionPropertyRequest source, @Context CustomContext context);

    public ConnectionInfo toConnectionInfo(ConnectionInfoRequest request) {

        return toConnectionInfo(request, new CustomContext());
    }

    public static class CustomContext {

        private ConnectionInfo connectionInfo;

        @BeforeMapping
        public void beforeContext(@MappingTarget ConnectionInfo info) {
            this.connectionInfo = info;
        }

        @AfterMapping
        public void setConnectionInfo(@MappingTarget ConnectionProperty connectionProperty) {
            connectionProperty.setConnectionInfo(connectionInfo);
        }

    }

}
