import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;

import java.io.InputStream;
import java.util.Set;

public class JsonSchemaValidatorExample {

    public static void main(String[] args) throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        // Load schema
        InputStream inputStream =
                JsonSchemaValidatorExample.class
                        .getResourceAsStream("/user-schema.json");

        JsonSchemaFactory factory =
                JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7);

        JsonSchema schema = factory.getSchema(inputStream);

        // Input JSON
        String json = """
                {
                    "name": "Jagadeesh",
                    "age": 26,
                    "email": "jagadeesh@example.com",
                    "salary": 50000
                }
                """;

        // Convert JSON string into JsonNode
        JsonNode jsonNode = mapper.readTree(json);

        // Validate
        Set<ValidationMessage> errors = schema.validate(jsonNode);

        if (errors.isEmpty()) {
            System.out.println("JSON is valid!");
        } else {
            System.out.println("JSON is invalid!");

            for (ValidationMessage error : errors) {
                System.out.println(error.getMessage());
            }
        }
    }
}



