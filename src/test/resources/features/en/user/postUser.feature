#language:en

@AllScenarios-EN @postUser
Feature: User Registration

  @addUser
  Scenario: Register user successfully
    Given that I have a user
    When send a POST request to the path users
    Then should return the status code 201
    And the contract should match postUser.json

  @validatePayloadFillForAddUser
  Scenario Outline: Validate payload fill with alternative values for POST /users
    Given that I have a user
    And I fill in the payload the field <field> with the value "<value>"
    When send a POST request to the path users
    Then should return the status code <code>

    Examples:
      | field | value             | code |
      | name  |                   | 201  |
      | name  | 0                 | 201  |
      | name  | 123               | 201  |
      | name  | aaaa              | 201  |
      | name  | null              | 201  |
      | name  | true              | 201  |
      | name  | false             | 201  |
      | name  | decimalNumber     | 201  |
      | name  | negativeNumber    | 201  |
      | name  | 15.numbers        | 201  |
      | name  | 100.stringNumbers | 201  |
      | name  | 25.specialString  | 201  |
      | job   |                   | 201  |
      | job   | 0                 | 201  |
      | job   | 123               | 201  |
      | job   | aaaa              | 201  |
      | job   | null              | 201  |
      | job   | true              | 201  |
      | job   | false             | 201  |
      | job   | decimalNumber     | 201  |
      | job   | negativeNumber    | 201  |
      | job   | 15.numbers        | 201  |
      | job   | 100.stringNumbers | 201  |
      | job   | 25.specialString  | 201  |