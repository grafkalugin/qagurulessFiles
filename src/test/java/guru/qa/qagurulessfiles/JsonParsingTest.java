package guru.qa.qagurulessfiles;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonParsingTest {

    private ClassLoader cl = JsonParsingTest.class.getClassLoader();

@Test
    public void jsonTest() throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream is = cl.getResourceAsStream("json/delivery.json")) {
            Delivery delivery = objectMapper.readValue(is, Delivery.class);
            assertEquals("Masha", delivery.courier);
            assertEquals(12, delivery.id);
            assertEquals(2, delivery.products.size());
            List<String> MessActual = Arrays.asList("code_123", "silent_mode");
            assertEquals(delivery.address.messages, MessActual);
            assertEquals("banana", delivery.products.get(1).goods);
            assertEquals(1.2, delivery.products.get(0).weight);
        }
    }
}
