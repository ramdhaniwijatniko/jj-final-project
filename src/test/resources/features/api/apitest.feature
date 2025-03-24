Feature: API Testing for DummyAPI.io

Scenario: Create a new user
  Given I have user details with first name "reni", last name "astuti", and a unique email
  When I send a request to create the user
  Then the user should be created successfully

  Scenario: Get user by ID
    Given I have the saved user ID
    When I send a GET request to "/user/{id}"
    Then the response status should be 200
    And the response should contain the user details

  Scenario: Update user details
    Given I have the saved user ID
    And I have updated user details with name "John Updated" and email "john.updated@example.com"
    When I send a PUT request to "/user/{id}"
    Then the response status should be 200
    And the response should contain the updated details

  Scenario: Delete user
    Given I have the saved user ID
    When I send a DELETE request to "/user/{id}"
    Then the response status should be 200
    And the user should no longer exist
