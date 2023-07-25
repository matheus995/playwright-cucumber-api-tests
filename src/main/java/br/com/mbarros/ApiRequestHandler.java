package br.com.mbarros;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import io.qameta.allure.Allure;
import io.qameta.allure.attachment.DefaultAttachmentProcessor;
import io.qameta.allure.attachment.FreemarkerAttachmentRenderer;
import io.qameta.allure.attachment.http.HttpRequestAttachment;
import io.qameta.allure.attachment.http.HttpResponseAttachment;
import io.restassured.http.Method;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static br.com.mbarros.ApiConstants.BASE_URI;
import static br.com.mbarros.ApiHelpers.replacePathParams;

/**
 * The ApiRequestHandler class handles API requests and responses.
 * It contains methods to send API requests and attach request/response information to Allure reports.
 *
 */
@Slf4j
public class ApiRequestHandler {

    Boolean isToReport;
    Map<String, String> headers = new HashMap<>();
    Map<String, Object> body = new HashMap<>();
    Map<String, String> cookies = new HashMap<>();

    public ApiRequestHandler() {
        isToReport = true;
    }

    public ApiRequestHandler(Boolean isToReport) {
        this.isToReport = isToReport;
    }

    /**
     * Sends an API request based on the provided request object, method, and endpoint.
     *
     * @param request  The Request object representing the API request details.
     * @param method   The HTTP method (e.g., GET, POST, PUT, DELETE) used for the API request.
     * @param endpoint The API endpoint to which the request is sent.
     * @return An APIResponse object representing the API response.
     */
    public APIResponse doRequest(Request request, Method method, String endpoint) {
        headers.put("Content-Type", "application/json");

        body = request.getBody();

        APIRequestContext requestContext = PlaywrightManager.getPlaywright().request().newContext(new APIRequest.NewContextOptions()
                .setBaseURL(BASE_URI)
                .setExtraHTTPHeaders(headers));

        PlaywrightManager.apiRequestContext.set(requestContext);

        if (method.equals(Method.PATCH) || method.equals(Method.DELETE)) {
            endpoint = replacePathParams(endpoint, request.getPathParams());
        }

        APIResponse response;

        long startTime = System.currentTimeMillis();
        switch (method) {
            case GET -> response = requestContext.get(endpoint, request.getRequestOptions());
            case POST -> response = requestContext.post(endpoint, request.getRequestOptions());
            case PUT -> response = requestContext.put(endpoint, request.getRequestOptions());
            case PATCH -> response = requestContext.patch(endpoint, request.getRequestOptions());
            case DELETE -> response = requestContext.delete(endpoint, request.getRequestOptions());
            default -> throw new RuntimeException("Method [" + method.name() + "] not implemented");
        }
        long endTime = System.currentTimeMillis();

        long duration = endTime - startTime;
        int hour = (int) (duration / 3600000) % 24;
        int minute = (int) (duration / 60000) % 60;
        int seconds = (int) (duration / 1000) % 60;
        int milliseconds = (int) (duration % 1000);

//        TODO: Console Log request / response and format body in Allure request attachment

//        JSONObject responseBody = new JSONObject(response.text());
//        log.info("\n"+responseBody.toString(4));

//        Gson responseBody = new GsonBuilder().setPrettyPrinting().create();
//        log.info(responseBody.toJson(JsonParser.parseString(response.text())));

        String responseTimeLog = "Response time: " + hour + "h " + minute + "m " + seconds + "s " + milliseconds + "ms";
        log.info(responseTimeLog);

        attachRequest(response.url(), method);
        attachResponse(response);
        Allure.addAttachment("Response time", hour + "h " + minute + "m " + seconds + "s " + milliseconds + "ms");

        return response;
    }

    /**
     * Attaches the API request information to the Allure report.
     *
     * @param url    The API URL to which the request was sent.
     * @param method The HTTP method used for the API request.
     */
    public void attachRequest(String url, Method method) {
        HttpRequestAttachment.Builder requestAttachmentBuilder = HttpRequestAttachment.Builder
                .create("Request", url)
                .setMethod(method.name())
                .setHeaders(headers)
                .setCookies(cookies);

        if (Objects.nonNull(body)) {
            requestAttachmentBuilder.setBody(body.toString());
        }

        HttpRequestAttachment requestAttachment = requestAttachmentBuilder.build();
        (new DefaultAttachmentProcessor()).addAttachment(requestAttachment, new FreemarkerAttachmentRenderer("http-request.ftl"));
    }

    /**
     * Attaches the API response information to the Allure report.
     *
     * @param response An APIResponse object representing the API response.
     */
    public void attachResponse(APIResponse response) {
        String responseBody = null;
        if (response.text().contains("{")) {
            JSONObject body = new JSONObject(response.text());
            responseBody = body.toString(4);
        }

        HttpResponseAttachment responseAttachment = HttpResponseAttachment.Builder
                .create("Response")
                .setResponseCode(response.status())
                .setHeaders(response.headers())
                .setBody(Objects.nonNull(responseBody) ? responseBody : "")
                .build();

        (new DefaultAttachmentProcessor()).addAttachment(responseAttachment, new FreemarkerAttachmentRenderer("http-response.ftl"));
    }
}