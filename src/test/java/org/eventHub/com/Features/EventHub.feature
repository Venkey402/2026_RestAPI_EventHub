Feature: Event Hub api testing

  Scenario: Register a new user with email and password
    Given user registers to event hub
    Then login with user
    And validate user token
    Then create a booking to an event
    And get list of all bookings
    Then get a single booking details
    And delete a booking