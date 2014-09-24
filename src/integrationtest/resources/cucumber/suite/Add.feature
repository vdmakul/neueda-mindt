Feature: Add

Background: 
	Given A local calculator
	And request path is "/rest/add"
	And request method is "POST"

Scenario: simple addition
	When I enter 6 and 8
	Then result is 14

Scenario: adding a negative number
	When I enter -5.34 and 3.95
	Then result is -1.39

