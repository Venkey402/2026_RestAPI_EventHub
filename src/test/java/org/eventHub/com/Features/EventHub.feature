Feature: Event Hub api testing

  Scenario: Register a new user with email and password
    Given user registers to event hub
    Then login with user
    And validate user token