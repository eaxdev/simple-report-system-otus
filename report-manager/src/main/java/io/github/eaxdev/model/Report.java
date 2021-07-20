package io.github.eaxdev.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@Entity
@ToString
@Table(name = "report")
@SuppressWarnings("JpaDataSourceORMInspection")
public class Report {

    @Id
    @GeneratedValue
    @Column(name = "report_id")
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "model_id", nullable = false)
    private int modelId;

    @Column(name = "query")
    private String query;

    @Column(name = "count_query")
    private String countQuery;

    @Column(name = "original_json")
    private String originalJson;

}
