Feature: Multiplication

  Background: A Calculator
    Given A local calculator
    And request path is "/rest/multiply"
    And request method is POST

  Scenario: Simple multiplication
    When  I multiply 3 and 5
    Then  result is 15.00
