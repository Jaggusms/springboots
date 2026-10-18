import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Set;

public class JsonSchemaValidator {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void validateJson(String jsonString, String schemaFilePath)
            throws Exception {

        // 1. Convert JSON string into JsonNode
        JsonNode json = objectMapper.readTree(jsonString);

        // 2. Load the JSON Schema file
        try (InputStream inputStream = new FileInputStream(schemaFilePath)) {

            // 3. Create the JSON Schema factory (Draft-04)
            JsonSchemaFactory schemaFactory =
                    JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V4);

            // 4. Load the schema
            JsonSchema jsonSchema = schemaFactory.getSchema(inputStream);

            // 5. Validate the JSON against the schema
            Set<ValidationMessage> validationResult =
                    jsonSchema.validate(json);

            // 6. Check validation results
            if (validationResult.isEmpty()) {
                System.out.println("JSON Schema validation successful.");
            } else {
                System.out.println("JSON Schema validation failed:");

                validationResult.forEach(error ->
                        System.out.println(error.getMessage())
                );
            }
        }
    }

    public static void main(String[] args) throws Exception {

        JsonSchemaValidator validator = new JsonSchemaValidator();

        String json = """
            {
                "name": "Jagadeesh",
                "age": 26
            }
            """;

        String schemaFilePath = "src/main/resources/user-schema.json";

        validator.validateJson(json, schemaFilePath);
    }
}