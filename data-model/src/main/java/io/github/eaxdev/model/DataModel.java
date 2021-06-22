package io.github.eaxdev.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@Entity
@ToString
@Table(name = "data_model")
public class DataModel {

    @Id
    @GeneratedValue
    @Column(name = "model_id")
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "table_name", nullable = false)
    private String tableName;

    @Column(name = "connection_id", nullable = false)
    private int connectionId;

    @Column(name = "json_criteria")
    private String jsonCriteria;

}
