Feature: Work "click here"

  Scenario: Change content
    Given I am on the https://the-internet.herokuapp.com/dynamic_content
    When I click on "click here"
    Then I should see change content of the threeth agent