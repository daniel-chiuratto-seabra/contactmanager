package nl.jungle.databaseservice.serializer;

import com.google.gson.Gson;
import nl.jungle.databaseservice.model.Contact;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Collection;

public class CollectionContactSerializer implements Serializer<Collection<Contact>> {

    private static final Gson GSON = new Gson();

    @Override
    public byte[] serialize(final String topic, final Collection<Contact> data) {
        return GSON.toJson(data).getBytes();
    }
}
