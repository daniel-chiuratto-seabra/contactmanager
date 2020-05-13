package nl.jungle.emailservice.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.apache.kafka.common.serialization.Deserializer;

public class JsonNodeDeserializer implements Deserializer<JsonNode> {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    public JsonNode deserialize(final String s, final byte[] bytes) {
        val content = new String(bytes);
        try {
            return OBJECT_MAPPER.readValue(content, JsonNode.class);
        } catch (final JsonProcessingException e) {
            throw new RuntimeException("An error occurred in deserializing a message", e);
        }
    }

}
