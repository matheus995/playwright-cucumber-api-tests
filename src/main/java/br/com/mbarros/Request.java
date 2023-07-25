package br.com.mbarros;

import com.microsoft.playwright.options.RequestOptions;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * A data class representing a Request object used for making API requests.
 * This class is annotated with Lombok annotations for code generation.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Request {

    RequestOptions requestOptions;
    Map<String, Object> body;
    Map<String, Object> pathParams;
    String jsonSchemaPath;
}
