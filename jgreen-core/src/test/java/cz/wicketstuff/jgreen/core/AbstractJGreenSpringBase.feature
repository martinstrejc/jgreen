Feature: Cucumber-Spring support

Scenario: Create Spring context at a cucumber test
	When The glue code extends AbstractJGreenSpringBase
	And The glue code implements ApplicationContextAware interface
	Then the Spring context is autowired via a setter 

Scenario: Inject Spring Beans
	When The glue code extends AbstractJGreenSpringBase
	And The glue code implements ApplicationContextAware interface
	Then the Spring context is autowired into a Spring annotated field
	And the Spring context is autowired into a JEE annotated field
	