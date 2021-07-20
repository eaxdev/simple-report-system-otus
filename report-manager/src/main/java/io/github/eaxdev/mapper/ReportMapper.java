package io.github.eaxdev.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.eaxdev.dto.ReportDto;
import io.github.eaxdev.dto.criteria.*;
import io.github.eaxdev.exception.ReportManagerException;
import io.github.eaxdev.model.Report;
import io.github.eaxdev.service.DataModelService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

@Mapper(componentModel = "spring")
public abstract class ReportMapper {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DataModelService dataModelService;

    @Mapping(target = "originalJson", source = "source", qualifiedByName = "toOriginalJson")
    @Mapping(target = "query", source = "source", qualifiedByName = "toQuery")
    @Mapping(target = "countQuery", source = "source", qualifiedByName = "toCountQuery")
    public abstract Report toReport(ReportDto source);

    public ReportDto toDto(Report report) {
        try {
            ReportDto reportDto = objectMapper.readValue(report.getOriginalJson(), ReportDto.class);
            reportDto.setId(report.getId());
            return reportDto;
        } catch (JsonProcessingException e) {
            throw new ReportManagerException(e);
        }
    }

    @Named("toOriginalJson")
    protected String toOriginalJson(ReportDto source) {
        try {
            return objectMapper.writeValueAsString(source);
        } catch (JsonProcessingException e) {
            throw new ReportManagerException(e);
        }
    }

    @Named("toCountQuery")
    protected String toCountQuery(ReportDto source) {
        var dataModel = dataModelService.findById(source.getModelId());
        if (dataModel.getJsonCriteria() == null) {
            if (source.getCriteria() == null) {
                if (source.getGroupBy() == null) {
                    return "SELECT count(*) FROM " + dataModel.getTableName();
                } else {
                    return "SELECT count(*) FROM " + dataModel.getTableName() + "GROUP BY " + source.getGroupBy();
                }
            } else {
                String criteriaString = new CriteriaBuilder(source.getCriteria()).build();
                if (source.getGroupBy() == null) {
                    return "SELECT count(*) FROM " + dataModel.getTableName() + criteriaString;
                } else {
                    return "SELECT count(*) FROM " + dataModel.getTableName() + criteriaString + "GROUP BY " + source.getGroupBy();
                }
            }
        } else {
            try {
                var criteria = objectMapper.readValue(dataModel.getJsonCriteria(), Criteria.class);
                String criteriaString = new CriteriaBuilder(criteria).build();

                if (source.getCriteria() == null) {
                    if (source.getGroupBy() == null) {
                        return "SELECT count(*) FROM " + dataModel.getTableName() + " WHERE " + criteriaString;
                    } else {
                        return "SELECT count(*) FROM " + dataModel.getTableName() + " WHERE " + criteriaString + " GROUP BY " + source.getGroupBy();
                    }
                } else {
                    String reportCriteriaString = new CriteriaBuilder(source.getCriteria()).build();
                    if (source.getGroupBy() == null) {
                        return "SELECT count(*) FROM " + dataModel.getTableName() + " WHERE " + criteriaString + " AND " + reportCriteriaString;
                    } else {
                        return "SELECT count(*) FROM " + dataModel.getTableName() + " WHERE " + criteriaString + " AND " + reportCriteriaString + "GROUP BY " + source.getGroupBy();
                    }
                }
            } catch (JsonProcessingException e) {
                throw new ReportManagerException(e);
            }
        }
    }

    @Named("toQuery")
    protected String toQuery(ReportDto source) {
        var dataModel = dataModelService.findById(source.getModelId());
        var selectFields = String.join(", ", source.getOutputs());

        if (dataModel.getJsonCriteria() == null) {
            if (source.getCriteria() == null) {
                if (source.getGroupBy() == null) {
                    return "SELECT " + selectFields + " FROM " + dataModel.getTableName();
                } else {
                    return "SELECT " + selectFields + " FROM " + dataModel.getTableName() + " GROUP BY " + source.getGroupBy();
                }
            } else {
                String criteriaString = new CriteriaBuilder(source.getCriteria()).build();
                if (source.getGroupBy() == null) {
                    return "SELECT " + selectFields + " FROM " + dataModel.getTableName() + " WHERE " + criteriaString;
                } else {
                    return "SELECT " + selectFields + " FROM " + dataModel.getTableName() + " WHERE " + criteriaString + "GROUP BY " + source.getGroupBy();
                }
            }
        } else {
            try {
                var criteria = objectMapper.readValue(dataModel.getJsonCriteria(), Criteria.class);
                String criteriaString = new CriteriaBuilder(criteria).build();

                if (source.getCriteria() == null) {
                    if (source.getGroupBy() == null) {
                        return "SELECT " + selectFields + " FROM " + dataModel.getTableName() + StringUtils.SPACE + criteriaString;
                    } else {
                        return "SELECT " + selectFields + " FROM " + dataModel.getTableName() + StringUtils.SPACE + criteriaString + " GROUP BY " + source.getGroupBy();
                    }
                } else {
                    String reportCriteriaString = new CriteriaBuilder(source.getCriteria()).build();
                    if (source.getGroupBy() == null) {
                        return "SELECT " + selectFields + " FROM " + dataModel.getTableName() + criteriaString + " AND " + reportCriteriaString;
                    } else {
                        return "SELECT " + selectFields + " FROM " + dataModel.getTableName() + criteriaString + " AND " + reportCriteriaString + "GROUP BY " + source.getGroupBy();
                    }
                }
            } catch (JsonProcessingException e) {
                throw new ReportManagerException(e);
            }
        }
    }

    @RequiredArgsConstructor
    private static class CriteriaBuilder {
        private final Criteria criteria;

        public String build() {
            if (Objects.isNull(criteria)) {
                return "";
            }
            return constructExpression(criteria);
        }

        private String constructExpression(Criteria criteria) {
            return switch (criteria.getCriteriaType()) {
                case GROUP -> constructByGroupCriteria((GroupCriteria) criteria);
                case SIMPLE -> constructBySimpleCriteria((SimpleCriteria) criteria);
            };
        }

        private String constructBySimpleCriteria(SimpleCriteria criteria) {
            return switch (criteria.getSimpleConditionalOperator()) {
                case EQUALS_TO -> criteria.getFieldName() + SimpleConditionalOperator.EQUALS_TO.getQueryView() + criteria.getValue();
                case NOT_EQUALS_TO -> criteria.getFieldName() + SimpleConditionalOperator.NOT_EQUALS_TO.getQueryView() + criteria.getValue();
                case GREATER_THAN -> criteria.getFieldName() + SimpleConditionalOperator.GREATER_THAN.getQueryView() + criteria.getValue();
                case GREATER_THAN_EQUALS -> criteria.getFieldName() + SimpleConditionalOperator.GREATER_THAN_EQUALS.getQueryView() + criteria.getValue();
                case LESS_THAN -> criteria.getFieldName() + SimpleConditionalOperator.LESS_THAN.getQueryView() + criteria.getValue();
                case LESS_THAN_EQUALS -> criteria.getFieldName() + SimpleConditionalOperator.LESS_THAN_EQUALS.getQueryView() + criteria.getValue();
                case CONTAINS -> criteria.getFieldName() + SimpleConditionalOperator.CONTAINS.getQueryView() + criteria.getValue();
                case NOT_CONTAINS -> criteria.getFieldName() + SimpleConditionalOperator.NOT_CONTAINS.getQueryView() + criteria.getValue();
            };
        }

        private String constructByGroupCriteria(GroupCriteria criteria) {
            if (criteria.getGroupConditionalOperator() == GroupConditionalOperator.AND) {
                String leftExpression = constructExpression(criteria.getCriteria().get(0));
                String rightExpression = constructExpression(criteria.getCriteria().get(1));
                return "(" + leftExpression + GroupConditionalOperator.AND.getQueryView() + rightExpression + ")";
            } else {
                String leftExpression = constructExpression(criteria.getCriteria().get(0));
                String rightExpression = constructExpression(criteria.getCriteria().get(1));
                return "(" + leftExpression + GroupConditionalOperator.OR.getQueryView() + rightExpression + ")";
            }
        }
    }
}
