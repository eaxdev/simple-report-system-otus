package io.github.eaxdev.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Entity
@Getter
@Table(name = "connection_property")
@SuppressWarnings("JpaDataSourceORMInspection")
public class ConnectionProperty {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private String value;

    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "connection_info_id")
    private ConnectionInfo connectionInfo;

}