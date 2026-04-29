Feature: Booking functionality
Background:
  Then login with user

  Scenario: create a booking for an event
    Then create a booking to an event
    And get list of all bookings
    Then get a single booking details
    And get the list of events
    And delete a booking
    And delete an event
