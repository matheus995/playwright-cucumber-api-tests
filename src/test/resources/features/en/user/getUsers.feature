#language:en

@AllScenarios-EN @getUsers
Feature: Retrieve Users

  @retrieveUsers
  Scenario: Retrieve users successfully
    When send a GET request to the path users
    Then should return the status code 200
    And the contract should match getUsers.json