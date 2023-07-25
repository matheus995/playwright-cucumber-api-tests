#language:en

@AllScenarios-EN @patchUsers
Feature: Update User

  @updateUser
  Scenario: Update user successfully
    Given that I create a user
    And want to update the registered user
    And I define the path param id with the value of the field id from the previous response
    When send a PATCH request to the path users/{id}
    Then should return the status code 200

  @validatePayloadFillForUpdateUser
  Scenario Outline: Validate payload fill with alternative values for PATCH /users{id}
    Given that I create a user
    And I define the path param id with the value of the field id from the previous response
    And I fill in the payload the field <field> with the value "<value>"
    When send a PATCH request to the path users/{id}
    Then should return the status code <code>

    Examples:
      | field | value             | code |
      | name  |                   | 200  |
      | name  | 0                 | 200  |
      | name  | 123               | 200  |
      | name  | aaaa              | 200  |
      | name  | null              | 200  |
      | name  | true              | 200  |
      | name  | false             | 200  |
      | name  | decimalNumber     | 200  |
      | name  | negativeNumber    | 200  |
      | name  | 15.numbers        | 200  |
      | name  | 100.stringNumbers | 200  |
      | name  | 25.specialString  | 200  |
      | job   |                   | 200  |
      | job   | 0                 | 200  |
      | job   | 123               | 200  |
      | job   | aaaa              | 200  |
      | job   | null              | 200  |
      | job   | true              | 200  |
      | job   | false             | 200  |
      | job   | decimalNumber     | 200  |
      | job   | negativeNumber    | 200  |
      | job   | 15.numbers        | 200  |
      | job   | 100.stringNumbers | 200  |
      | job   | 25.specialString  | 200  |