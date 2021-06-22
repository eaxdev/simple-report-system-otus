package io.github.eaxdev.dto.criteria;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

import java.io.IOException;

import static com.fasterxml.jackson.core.JsonToken.START_ARRAY;

public class GroupCriteriaSerializer extends JsonSerializer<GroupCriteria> {

    @Override
    public void serialize(GroupCriteria groupCriteria, JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {
        for (Criteria criteria : groupCriteria.getCriteria()) {
            jsonGenerator.writeObject(criteria);
        }
    }

    @Override
    public void serializeWithType(GroupCriteria value, JsonGenerator gen,
                                  SerializerProvider serializers, TypeSerializer typeSer) throws IOException {
        WritableTypeId typeIdStart = typeSer.typeId(value, START_ARRAY);
        typeSer.writeTypePrefix(gen, typeIdStart);

        serialize(value, gen, serializers);

        gen.writeEndArray();
        gen.writeEndObject();
    }

}
