# X-Men Validator
Home assignment Challenge for evaluation from Mercado Libre.

The service perform operations according to :

1. Validate if some Dna Sequence Array String as example :

    ```jshelllanguage
    String[] dna = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"};
    ```
2. Getting statistics from validations service.

## Getting Started

This project was build as a Spring Boot Rest API with Java 8 and reactive java.

### Prerequisites

 * Java Development Kit 1.8
 * Spring Boot
 * Maven
 * Postman
 * DynamoDb Instance

## Download project

`By starting...`

Clone the project:

```sh
git clone https://github.com/jstephenvar/x-men-validator/
```

then over application path build: 

```sh
$~\room-occupancy-manager\ mvn clean install -U
```

### Running API

Run the application with maven

```sh
$~\room-occupancy-manager\mvn spring-boot:run
```
### Initializing Data

By default, the repository at this moment use DynamoDb. Furthermore, AWS Secret Manager storage the keys to connect 
to an instance of Dynamo Db.

## Connecting to DynamoDb

[Dbeaver Example](https://www.cdata.com/kb/tech/dynamodb-jdbc-dbvr.rst) - Or just access to AIM user - `Note :Credentials
 passed by mail.`

## Service

### Rest API Documentation

* Test Service using Swagger UI

  Go to path `/x-men-validator-api/v1/swagger-ui.html` 
  
  [Link Swagger UI AWS deployed](http://x-men-loader-2008599056.us-east-1.elb.amazonaws.com/x-men-validator-api/v1/swagger-ui.html)

  Example :
    ```text
    http://localhost:1964/x-men-validator-api/v1/swagger-ui.html
    ```
    And there you can test the method with help of the UI.
    
### Test Service By Postman

* Get validation about Dna Sequence
 
    `POST /x-men-validator-api/v1/validate/mutant/`
    
    Endpoint AWS [`http://x-men-loader-2008599056.us-east-1.elb.amazonaws.com/x-men-validator-api/v1/validate/mutant/`]
  
     Curl Example local : 
  
    ```bash
    curl --location --request POST 'http://localhost:1964/x-men-validator-api/v1/validate/mutant/' \
    --header 'Content-Type: application/json' \
    --data-raw '{
        "dna": [
            "ATGCGA", 
            "CAGTGC",
            "XXXXXX",
            "XXXXXX",
            "adbcTA",
            "TCACtg"
        ]
    }'
  ```

* Get Stats about Dna Sequence Validations
 
    `GET /x-men-validator-api/v1/monitor/stats/`
    
    Endpoint AWS [`http://x-men-loader-2008599056.us-east-1.elb.amazonaws.com/x-men-validator-api/v1/monitor/stats/`]
  
     Curl Example Local : 
  
    ```bash
    curl --location --request GET 'http://localhost:1964/x-men-validator-api/v1/monitor/stats'
    ```

* Response

    Success
    
    ```json
    {
      "count_mutant_dna": "2",
      "count_human_dna": "3",
      "ratio": "0.6"
    }
    ```

    Error
    
    ```json
    {
      "status": 500,
      "errors": [
          {
            "internalMessage": null,
            "developerMessage": "Error during execution of stats request."
          }
      ]
    }
    ```

## Running the tests

### Execution

* Execution all test:

    ```bash
    mvn test
    ```

### Testing Strategy

The unit testing with Junit and Mockito, Integration Testing with MockMvc.

#### Unit test  

* `Coverage Jacoco : 98%` with valid exceptions.

    ![Jacoco validation example](https://github.com/jstephenvar/x-men-validator/blob/master/documentation/jacoco-coverage-img.jpg)

Example unit testing

```java
public class ValidatorServiceTest {
    
    @InjectMocks
    private ValidatorService validatorService;
    
    @Mock
    private ValidatorRepositoryImpl validatorRepository;
    
    @BeforeEach
    public void init() {
        initMocks(this);
    }
    
    @Test
    public void isMutant_Success() {
        assertTrue(validatorService.isMutant(DNA_MATCH));
    }
}
```

On the test below, validate an expected behavior and that it pass.

#### Integration Testing

Example integration testing over the app using MockMvc:

```java
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@SpringBootTest(classes = XMenValidatorApplication.class)
public class StatsApiControllerTest {
    
    @Mock
    StatsController statsController;
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;
    
    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }
    
    @Test
    public void when_getStats_Success() throws Exception {
        
        mockMvc.perform(
                get("/monitor/stats")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
```

### Service Architecture Diagram

![AWS architecture](https://github.com/jstephenvar/x-men-validator/blob/master/documentation/architecture-service-img.jpg)

### High payload and concurrency

This test was perform with [Apache HTTP server benchmarking tool](https://httpd.apache.org/docs/2.4/programs/ab.html) 

* Validator 

    command : `./ab -n 1000 -c 100 -m POST http://localhost:1964/x-men-validator-api/v1/validate/mutant/`
    
    ![Result](https://github.com/jstephenvar/x-men-validator/blob/master/documentation/validator-request-concurrency-img.jpg)
    
* Stats 

    command : `./ab -n 100 -c 10 -m GET http://localhost:1964/x-men-validator-api/v1/monitor/stats`
    
    ![Result](https://github.com/jstephenvar/x-men-validator/blob/master/documentation/stats-request-concurrency-img.jpg)

## Code analyze with SonarQube - Optional

Run over project path: 

```sh
mvn clean verify sonar:sonar
```

![Sonar validation example](https://github.com/jstephenvar/x-men-validator/blob/master/documentation/sonarqube-img.jpg)

## Built with

### Introduction
The application was at beginning of develop using TDD. Besides, then was apply some strategies for concurrency using
 Rx Java, event-driven and database performance with cache abstraction (Spring Cache).

### Frameworks and Tools

* [Spring](https://spring.io/projects) - Framework to build the REST API, projects used (Boot, Data, WebMVC)
* [Maven](https://maven.apache.org/) - Dependency Management
* [Lombok](https://projectlombok.org/) - Used to generate automatically implicit methods inside class Java Object.
* [Swagger2](https://swagger.io/) - Generate automatically and UI to API REST Documentation and testing
* [Jacoco](https://www.eclemma.org/jacoco/) - To analyze the coverage of testing.
* [SonarQube](https://docs.sonarqube.org/latest/analysis/scan/sonarscanner-for-maven/) - Is a code analyzer
 integrated with  maven as a plugin.
* [Rx Java 3](https://github.com/ReactiveX/RxJava/tree/2.x) - A library for composing asynchronous and event-based
 programs by using observable sequences. (Reactive Programming)
* [DynamoDb](https://aws.amazon.com/dynamodb/) - A provided Non-SQL Database from AWS. 

## Version

Actual Version 0.0.2-SNAPSHOT

## Authors

* Johan Stephen Vargas
