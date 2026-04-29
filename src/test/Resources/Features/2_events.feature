Feature: Events functionality
Background:
  Then login with user

  Scenario: create an event and update event details
    Then create an event
    And get the event details
    Then update the price of the event