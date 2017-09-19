Feature: Hello World code that demonstrates new jGreen style

  @Current
  Scenario: Go to Google and find BDD mentions
    Given I go to Google.cz page
    And I wait until it is loaded
    When I type "<BDD>" to search field
    And I click Search button
    Then I am on search results page