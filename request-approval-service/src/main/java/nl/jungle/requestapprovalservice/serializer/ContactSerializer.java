package nl.jungle.requestapprovalservice.serializer;

import com.google.gson.Gson;
import nl.jungle.requestapprovalservice.model.Contact;
import org.apache.kafka.common.serialization.Serializer;

public class ContactSerializer implements Serializer<Contact> {

    private static final Gson GSON = new Gson();

    @Override
    public byte[] serialize(final String s, final Contact contact) {
        return GSON.toJson(contact).getBytes();
    }

}
