Feature: Event Hub api testing

  Scenario: Register a new user with email and password
    Given user registers to event hub
    Then login with user
    And validate user token
    Then create a booking to an event
    And get list of all bookings
    Then get a single booking details
    And get the list of events
    Then create an event
    And get the event details
    Then update the price of the event
    And delete an event
    And delete a booking
    Then validate health check is fine