#language:en

@AllScenarios-EN @deleteUser
Feature: Delete User

  @deleteUser
  Scenario: Delete user successfully
    Given that I create a user
    And I define the path param id with the value of the field id from the previous response
    When send a DELETE request to the path users/{id}
    Then should return the status code 204