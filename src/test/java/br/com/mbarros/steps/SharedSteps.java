package br.com.mbarros.steps;

import br.com.mbarros.ApiRequestHandler;
import br.com.mbarros.exceptions.JsonSchemaValidationException;
import br.com.mbarros.SharedApiData;
import com.microsoft.playwright.APIResponse;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.restassured.http.Method;
import org.json.JSONObject;
import org.testng.Assert;

import static br.com.mbarros.ApiHelpers.transformData;
import static br.com.mbarros.ApiHelpers.validateJSONSchema;

public class SharedSteps {
    final SharedApiData sharedApiData;
    ApiRequestHandler apiRequestHandler = new ApiRequestHandler();

    public SharedSteps(SharedApiData sharedApiData) {
        this.sharedApiData = sharedApiData;
    }

    @Quando("enviar requisicao {} para o path {word}")
    @When("send a {} request to the path {word}")
    public void sendRequest(Method method, String path) {
        APIResponse response = apiRequestHandler.doRequest(sharedApiData.prepareRequest(), method, path);
        sharedApiData.setResponse(response);
    }

    @E("defino o path param {word} com o valor do campo {word} da response anterior")
    @And("I define the path param {word} with the value of the field {word} from the previous response")
    public void setPathParamToRequest(String param, String responseBodyField) {
        JSONObject responseBody = new JSONObject(sharedApiData.getResponse().text());
        sharedApiData.addPathParam(param, responseBody.get(responseBodyField));
    }

    @E("o contrato deve estar de acordo com o {word}")
    @E("the contract should match {word}")
    public void validateResponseSchema(String jsonSchemaFile) throws JsonSchemaValidationException {
        sharedApiData.setJsonSchemaFile(jsonSchemaFile);
        validateJSONSchema(sharedApiData.getResponse(), sharedApiData.getJsonSchemaFile());
    }

    @E("preencho no payload o campo {word} com o valor {string}")
    @And("I fill in the payload the field {word} with the value {string}")
    public void fillPayloadWithAlternativeValues(String field, String value) {
        sharedApiData.getBody().put(field, transformData(value));
    }

    @Entao("deve retornar o status code {int}")
    @Then("should return the status code {int}")
    public void validateResponseStatusCode(int statusCode) {
        Assert.assertEquals(sharedApiData.getResponse().status(), statusCode);
    }
}
