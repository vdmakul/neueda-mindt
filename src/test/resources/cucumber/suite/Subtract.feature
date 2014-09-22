Feature: Subtract

Background: 
	Given A local calculator
	And request path is "/rest/subtract"
	And request method is "POST"

Scenario: simple subtraction
	When I enter 97 and 58
	Then result is 39

Scenario: subtracting a negative number
	When I enter -34.12 and -55.7
	Then result is 21.58

