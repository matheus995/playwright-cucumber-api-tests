package br.com.mbarros;

import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * The SharedApiData class represents shared data and operations for API requests and responses.
 */
public class SharedApiData {

    private final String schemaFolder = "schemas/";

    @Getter
    @Setter
    private APIResponse response;

    @Getter
    @Setter
    private Request request = new Request();

    private Map<String, Object> queryParams = new HashMap<>();
    private Map<String, Object> pathParams = new HashMap<>();
    private Map<String, Object> headers = new HashMap<>();
    private Map<String, Object> body = null;
    private String jsonSchemaFile = "";

    /**
     * Prepares an API request by assembling the Request object with the provided data.
     *
     * @return The prepared Request object containing request options, body, and path parameters.
     */
    public Request prepareRequest() {
        RequestOptions requestOptions = RequestOptions.create();

        // Set query parameters in the RequestOptions object.
        queryParams.forEach((key, value) -> requestOptions.setQueryParam(key, (String) value));

        // Set the request body in the RequestOptions object, if it exists.
        if (body != null) {
            requestOptions.setData(body);
        }

        // Build and return the Request object with the prepared data.
        return Request.builder()
                .requestOptions(requestOptions)
                .body(body)
                .pathParams(pathParams)
                .build();
    }

    public void setQueryParams(Map<String, Object> queryParams) {
        this.queryParams = queryParams;
    }

    public void addQueryParam(String field, Object value) {
        queryParams.put(field, value);
    }

    public Map<String, Object> getPathParams() {
        return pathParams;
    }

    public void addPathParam(String field, Object value) {
        pathParams.put(field, value);
    }

    public Map<String, Object> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, Object> headersParams) {
        this.headers = headersParams;
    }

    public void addHeader(String field, Object value) {
        headers.put(field, value);
    }

    public Map<String, Object> getBody() {
        return body;
    }

    public void setBody(Map<String, Object> body) {
        this.body = body;
    }

    public void addBodyParam(String field, Object value) {
        body.put(field, value);
    }

    public String getJsonSchemaFile() {
        return jsonSchemaFile;
    }

    public void setJsonSchemaFile(String schema) {
        jsonSchemaFile = schemaFolder + schema;
    }
}
