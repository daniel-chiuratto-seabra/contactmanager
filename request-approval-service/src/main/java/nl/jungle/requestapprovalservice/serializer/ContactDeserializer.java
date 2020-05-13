package nl.jungle.requestapprovalservice.serializer;

import com.google.gson.Gson;
import lombok.val;
import nl.jungle.requestapprovalservice.model.Contact;
import org.apache.kafka.common.serialization.Deserializer;

public class ContactDeserializer implements Deserializer<Contact> {

    private static final Gson GSON = new Gson();

    @Override
    public Contact deserialize(final String s, final byte[] bytes) {
        val content = new String(bytes);
        return GSON.fromJson(content, Contact.class);
    }

}
