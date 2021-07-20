package io.github.eaxdev.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "connection_info")
@ToString(exclude = {"password"})
@SuppressWarnings("JpaDataSourceORMInspection")
public class ConnectionInfo {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "host")
    private String host;

    @Column(name = "port")
    private Integer port;

    @Column(name = "database_name")
    private String database;

    @Column(name = "user")
    private String user;

    @Column(name = "password")
    private String password;

    @Column(name = "is_active")
    private boolean active;

    @Column(name = "create_date")
    private Instant createDate;

    @OneToMany(mappedBy = "connectionInfo", cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
    private List<ConnectionProperty> connectionProperties;

}