Feature: Integration tests based on Spring Pet Clinic

  @PetClinic
  Scenario: 01 Add an owner - get to Owner form
    Given I open Pet Clinic
    And I go to Find Owner page
    When I press Add Owner button
    Then I am on Owner form

  @PetClinic
  Scenario: 02 Add an owner - filling in Owner form
    Given I go to Owner form
    When I fill in the form with random information
    And I press Add Owner
    Then I am on Owner Information page
    And Information is the same as I added
    When I open Find Owner page
    And Fill in Owners last name and press Find Owner
    Then I am on Owner Information page
    And Information is the same as I added



