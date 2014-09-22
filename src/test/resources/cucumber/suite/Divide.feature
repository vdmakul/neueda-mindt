Feature: Divide

Background: 
	Given A local calculator
	And request path is "/rest/divide"
	And request method is "POST"

Scenario: simple division
	When I enter 5 and 2
	Then result is 2.5

Scenario: division by negative number
	When I enter 22.36 and -5
	Then result is -4.472

Scenario: division by zero
	When I enter 10 and 0
	Then result is DIV/0

