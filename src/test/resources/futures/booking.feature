Feature: Search in Booking
  Scenario: Looking for 'Akra Kemer' hotel
    Given booking search page is opened
    When user searches for "Akra Kemer"
    Then "Akra Kemer - Ultra All Inclusive" is shown
    And hotel has rating "9.1"

    Scenario Outline: Looking hotels
      Given booking search page is opened
      When user searches for "<hotel>"
      Then "<expectedResult>" hotel is shown
      And hotel has rating "<rate>"
      Examples:
      | hotel | expectedResult |rate|
      | Akra Kemer | Akra Kemer - Ultra All Inclusive |9,1|
      | Meraki | Meraki Resort Sharm El Sheikh Adults only |9,4|