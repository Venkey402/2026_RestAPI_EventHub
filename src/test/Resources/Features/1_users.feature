Feature: Users functionality

  Scenario: Register a new user with email and password and login
    Given user registers to event hub
    Then login with user
    And validate user token