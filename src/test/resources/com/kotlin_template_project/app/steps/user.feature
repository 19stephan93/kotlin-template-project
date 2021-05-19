Feature: User

  Scenario Outline: Get all users
    Given I request all users list
    Then I receive http status "ok"
    And I receive content type "application/json"
    And I receive the following user: "<username>" "<firstName>" "<lastName>"
    Examples:
      | username | firstName | lastName |
      | smaldini | Stéphane | Maldini |