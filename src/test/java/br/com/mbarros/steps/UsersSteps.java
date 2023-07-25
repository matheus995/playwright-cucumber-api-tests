package br.com.mbarros.steps;

import br.com.mbarros.SharedApiData;
import br.com.mbarros.payloads.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.restassured.http.Method;
import org.apache.http.HttpStatus;

import java.util.Map;

public class UsersSteps {

    final SharedSteps sharedSteps;
    final SharedApiData sharedApiData;
    final ObjectMapper objectMapper = new ObjectMapper();

    public UsersSteps(SharedApiData sharedApiData, SharedSteps sharedSteps) {
        this.sharedApiData = sharedApiData;
        this.sharedSteps = sharedSteps;
    }

    @Dado("que tenho um usuario")
    @Given("that I have a user")
    public void setUserInRequestBody() {
        sharedApiData.setBody(objectMapper.convertValue(new User(), Map.class));
    }

    @Dado("que realizo o cadastro de um usuario")
    @Given("that I create a user")
    public void doRequestToCreateUser() {
        setUserInRequestBody();
        sharedSteps.sendRequest(Method.POST, "users");
        sharedSteps.validateResponseStatusCode(HttpStatus.SC_CREATED);
    }

    @E("desejo alterar o usuario cadastrado")
    @And("want to update the registered user")
    public void changeUserInRequestBody() {
        sharedApiData.setBody(objectMapper.convertValue(new User(), Map.class));
    }
}
