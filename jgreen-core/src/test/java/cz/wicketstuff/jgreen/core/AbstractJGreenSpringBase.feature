Feature: Cucumber-Spring support

Scenario: Create Spring context at a cucumber test
	When The glue code extends AbstractJGreenSpringBase
	And The glue code implements ApplicationContextAware interface
	Then the Spring context is autowired 
	