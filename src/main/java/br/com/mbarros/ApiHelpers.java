package br.com.mbarros;

import br.com.mbarros.exceptions.JsonSchemaValidationException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIResponse;
import com.networknt.schema.*;
import net.datafaker.Faker;
import org.testng.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.Map;
import java.util.Set;

/**
 * The ApiHelpers class provides helper methods for API-related operations.
 */
public class ApiHelpers {

    /**
     * Transforms the given value into an appropriate object based on predefined rules.
     *
     * @param value The value to be transformed.
     * @return The transformed object.
     */
    public static Object transformData(String value) {
        Faker faker = new Faker();
        int number = 0;

        if (value.contains(".")) {
            String[] parts = value.split("\\.");
            number = Integer.parseInt(parts[0]);
            value = parts[1];
        }

        return switch (value) {
            case "" -> " ";
            case "0" -> 0;
            case "null" -> null;
            case "negativeNumber" -> faker.number().negative();
            case "decimalNumber" -> faker.number().randomDouble(faker.number().numberBetween(1, 4), 1, 9999);
            case "numbers" -> Long.valueOf(faker.number().digits(number));
            case "stringNumbers" -> faker.number().digits(number);
            case "specialString" -> generateStringWithSpecialCharacters(number);
            case "false" -> false;
            case "true" -> true;
            default -> value;
        };
    }

    /**
     * Generates a string with special characters, numbers, letters and accented letters of the specified length.
     *
     * @param length The length of the string to be generated.
     * @return A string consisting of special characters, numbers, letters and accented letters with the specified length.
     */
    public static String generateStringWithSpecialCharacters(int length) {
        String specialCharacters = "!@#$%¨&*()-_=+[]{}^~´`<>,.;:/?|\"'";
        String letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String numbers = "0123456789";
        String accentedLetters = "áàãâéêíóôõúüçÁÀÃÂÉÊÍÓÔÕÚÜÇ";

        String caracteres = specialCharacters + letters + numbers + accentedLetters;
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(caracteres.length());
            sb.append(caracteres.charAt(index));
        }

        return sb.toString();
    }

    /**
     * Replaces path parameters in the provided endpoint with their corresponding values from the given pathParams map.
     *
     * @param endPoint   The API endpoint containing path parameters in the format "{param}" to be replaced.
     * @param pathParams A map of path parameters and their values to be used for replacement.
     * @return The updated endpoint with replaced path parameters.
     */
    public static String replacePathParams(String endPoint, Map<String, Object> pathParams) {
        for (Map.Entry<String, Object> entry : pathParams.entrySet()) {
            String paramName = entry.getKey();
            String paramValue = (String) entry.getValue();

            endPoint = endPoint.replace("{" + paramName + "}", paramValue);
        }
        return endPoint;
    }

    /**
     * Validates the JSON response against a given JSON schema.
     *
     * @param response       The APIResponse object representing the JSON response.
     * @param jsonSchemaPath The path to the JSON schema file used for validation.
     * @throws JsonSchemaValidationException If there is an error while validating the JSON against the schema.
     * @throws IllegalArgumentException      If the response or jsonSchemaPath is null.
     */
    public static void validateJSONSchema(APIResponse response, String jsonSchemaPath) throws JsonSchemaValidationException {
        if (response == null || jsonSchemaPath == null) {
            throw new IllegalArgumentException("response and jsonSchemaPath must be not null");
        }

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.text());

            try (InputStream schemaStream = JsonValidator.class.getClassLoader().getResourceAsStream(jsonSchemaPath)) {
                if (schemaStream == null) {
                    throw new IllegalArgumentException("JSON schema file not found: " + jsonSchemaPath);
                }

                JsonNode schemaNode = objectMapper.readTree(schemaStream);

                JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7);
                JsonSchema schema = schemaFactory.getSchema(schemaNode);

                Set<ValidationMessage> validationResult = schema.validate(jsonNode);

                if (!validationResult.isEmpty()) {
                    StringBuilder errorMessage = new StringBuilder("JSON is invalid according to the JSON Schema: " + jsonSchemaPath + "\n");
                    for (ValidationMessage message : validationResult) {
                        errorMessage.append(message.getMessage()).append("\n");
                    }
                    Assert.fail(errorMessage.toString());
                }
            }
        } catch (IOException e) {
            throw new JsonSchemaValidationException("Error validating JSON against JSON Schema", e);
        }
    }
}
