### CustomerCRUDAPI


Overview
This is a Java-based project consisting of two main components: a RESTful API for managing customer data and a  command line client application to interact with the API.
1) CustomerAPI:
  - The primary component of this project, CustomerAPI, is a Spring Boot-based REST API designed for managing customer data with PostgreSQL     as the database. 
  -  Both the CustomerAPI application and its PostgreSQL database run in Docker containers, creating a fully isolated environment that           simplifies setup and deployment.
  - The project includes integration and unit tests that utilize an H2 in-memory database for isolated testing.
  - API endpoints can be tested using Postman or the command-line client mentioned below.

2) CustomerTestAPIClient:
- A command-line Java application that interacts with the CustomerAPI, allowing users to create, read, update, and delete customer records.


### Setup
### Pre-requisites
Before beginning, ensure you have the following installed:
1) Docker Desktop:
This project requires Docker to containerize and run both the CustomerAPI  and its PostgreSQL database.
- Docker Desktop provides an easy way to manage containers on macOS and Windows.
- Download Docker Desktop
- Verify Docker installation by running: docker --version

2)Java Development Kit (JDK) 17 or Newer:
The CustomerAPI  is built with Java 17, so you need JDK 17 or a newer version.
- This is necessary for running tests in the CustomerAPI and executing the CustomerTestAPIClient application.
- Download JDK 17 or newer
- Verify the installation by running: java -version


3) Apache Maven:
- Maven is required for dependency management and running tests within the CustomerAPI
- Download Maven
- Verify the installation by running: mvn -v

     


Note
Docker will handle running the Spring Boot application and PostgreSQL database in containers, providing an isolated environment for easy setup.
JDK and Maven are required locally only for executing the CustomerTestAPIClient and for running tests within the CustomerAPI.


### Running the Application
1) Clone the Repository:
```
 git clone https://github.com/Shubhi-Srivastava/CustomerCRUDAPI.git
 cd  CustomerCRUDAPI
 cd CustomerAPI
```


2) Start the Docker Containers : In a terminal window go to the CustomerAPI directory, and start the containers with:
```
docker-compose up -d
```
   This will start a PostgreSQL database and the Spring Boot application.





### Verification and Testing 

### API Endpoints
The CustomerAPI provides the following endpoints:


- Create Customer: `POST /api/customers` - Creates a new customer.
- Get All Customers: `GET /api/customers` - Retrieves all customers.
- Get Customer by ID: `GET /api/customers/{id}` - Retrieves a specific customer by ID.
- Update Customer: `PUT /api/customers/{id}` - Updates an existing customer by ID.
- Delete Customer: `DELETE /api/customers/{id}` - Deletes a customer by ID.

### Testing the APIs


To test the CustomerAPI endpoints running on Docker, you can use one of the following methods:

1) Using CustomerTestAPIClient
CustomerTestAPIClient provides a simple CLI to interact with the CustomerAPI. Follow these steps to run it:

    a) Navigate to the CustomerTestAPIClient Directory:
   ```
     cd ..
     cd CustomerTestAPIClient
   ```
   
   
       

    b) Build the Client Application:
   ```
     mvn clean install
   ```

    c) Run the Client:
   ```
     mvn exec:java -Dexec.mainClass="org.example.CustomerTestApiClient"
   ```

    Follow CLI Prompts to interact with the API, including creating, retrieving, updating, and deleting customers.This includes a menu     
    being shown with six options where you can select different options for creating customer, retrieval of all customer, updating, 
    deleting/retrieval of a customer with a specific id 
    On selection of the option, you will further be prompted for details according to the option you have provided.



3) Using Postman
You can also use Postman to test the API endpoints manually. When testing, ensure your API server is running on Docker, and use the following URL , http://localhost:8080) in Postman
 
   a) Create Customer `(POST /api/customers)`:
   Specify the method POST in request type and give the following URL 
   ```http://localhost:8080/api/customers```


    The request body will look something like this 

   `{ 
   "firstName": "John",
    "middleName": "A", 
   "lastName": "Doe",
    "emailAddress": "john.doe@example.com", 
   "phoneNumber": "1234567890" 
   }`


  Note : Once the customer is created you will get the Id in the response body 

   b) Get All Customers
   Specify the method GET  in request type and give the following URL 
   `http://localhost:8080/api/customers`


   c) Get a specific customer by their id 
   Specify the method GET in request type and give the following URL 
   `http://localhost:8080/api/customers/{id}`

   d) Update a specific customer by their id
   Specify the method PUT in request type and give the following URL 
   `http://localhost:8080/api/customers/{id}`

   The request body will consist of updated details 

   `{ 
   "firstName": "John",
    "middleName": "B", 
   "lastName": "Doe",
    "emailAddress": "john.doe@example.com", 
   "phoneNumber": "1234567890" 
   }`

   e) Delete a specific customer by their id 
   Specify the method DELETE  in request type and give the following URL 
   `http://localhost:8080/api/customers/{id}`




  ### Running Tests 
  For running tests navigate to the folder CustomerAPI/src/
  Run unit tests with the command:
  \n

  ```mvn clean test -Dspring.profiles.active=test```

  Run integration tests with the command: 
  \n
  
  ```mvn clean verify -Dspring.profiles.active=test```






### Detailed Project Overiew and Explanation




### 1. Introduction
This project is a Java-based application for managing customer data, designed to run on Docker containers with a PostgreSQL database and includes a CLI client for API interactions. The project supports both integration and unit testing using an H2 in-memory database.


### 2. Creation of RESTful API  ( Project Structure and Flow )
### CustomerAPI (Spring Boot API)

### Development Part 
The CustomerAPI is a Spring Boot REST API that performs CRUD operations on customer data.
- Tech Stack: Java 17, Spring Boot, PostgreSQL (via Docker)
- Containerization: Both the application and the database run in Docker containers, ensuring portability and ease of setup.
- Testing: Unit and integration tests are supported with an H2 in-memory database, so tests can be executed independently of the PostgreSQL Docker container.
  
Main Components:


`a) Controller Layer`: This is the entry point for all the API requests. Whenever a user sends HTTP requests using any of the methods (GET, POST, PUT, DELETE), the request is routed to the controller class. It is responsible for handling all HTTP requests and responses. 
  - `CustomerController Class` : This is the main controller class. All the incoming HTTP requests are routed here. It is annotated with a 
    @RestController to define it as a REStful controller.
  - Each method in the CustomerController is mapped to an endpoint using annotations like @PostMapping, @GetMapping, @PutMapping, and 
    @DeleteMapping.
  - URL paths are defined for each operation, e.g., /api/customers for creating and retrieving all customers, and /api/customers/{id} for 
    customer-specific actions.
 Depending on the request type and the path , the request is routed to each of the methods which then call the methods defined in the 
 service layer for the  main business logic of handling the operation requested.

   - `GlobalExceptionHandler:` The GlobalExceptionHandler class deals with the error handling and exceptions. When the service method called 
     by the Controller method throws an exception and returns it to the controller method, the GlobalExceptionHandler class intervenes and 
     handles the exception before it reaches the client. The handler maps the exception to a specific HTTP status code and returns a 
     standardized error response with a meaningful message. It uses @ControllerAdvice to catch exceptions from controller methods.




`b) Service Layer` – The service layer contains the business logic of the code. 


- `CustomerServcie` : This interface defines the methods which our main Service layer class which implements it must implement as in all the methods for handling customers data operation, including creating, deletion, updation and retrieval of either a specific or all customers


- `CustomerCrudService`: This is the main service class implementing the CustomerService interface, where methods for handling customer operations are defined. Methods like addCustomer include validations, such as checking if any required fields are empty or if an email already exists in the system. Other methods like findCustomerById, deleteCustomer, and updateCustomer contain validations to ensure that operations are not attempted on non-existent customer IDs, and they throw custom exceptions with specific messages if a requested ID does not exist.
In case of no exception, methods like addCustomer, findCustomerById, findAllCustomers, and deleteCustomer interact with a CustomerRepository object. This repository, an implementation of the JPA repository, provides built-in functions for these operations, allowing CustomerCrudService to perform database interactions seamlessly. For methods like updateCustomer the logic is provided in the method itself.



`c) Repository Layer `
- `CustomerRepository`: It  is a data access layer component in the application responsible for interacting with the database to perform CRUD operations on customer data.
The CustomerRepository interface extends JpaRepository, which is part of the Spring Data JPA library. By extending JpaRepository, CustomerRepository inherits several built-in methods for interacting with the database, which eliminates the need to write common SQL statements manually. The generic types in JpaRepository<Customer, UUID> specify:
Customer: The entity type this repository manages.
UUID: The type of the entity’s primary key.

CRUD Operations: Since CustomerRepository extends JpaRepository, it automatically provides several CRUD methods:

  - `save(Customer customer): Saves a new or updated customer entity.`
  
  - `findById(UUID id): Finds a customer by its primary key (UUID).`
  
  - `findAll(): Retrieves all customer records.`
  
  - `deleteById(UUID id): Deletes a customer by its UUID.`
  
  - `existsById(UUID id): Checks if a customer with a specific UUID exists.`


`d) Entity Layer`: The Entity Layer defines the Customer data model with necessary fields and validations and is mapped with the postgreSQL database.  It includes getter and setter methods, enabling interaction with its variables while keeping them encapsulated. When the Spring Boot application runs for the first time, a Customer table is created in the Pos database with attributes: id, first_name, middle_name, last_name, email_address, and phone_number. The id serves as the primary key, middle_name is optional (allowed to be null), and phone_number uses a composite type for structured storage.


`e)Exception`: The Exception Layer includes custom exception classes to handle different types of errors specific to this application. Each class extends RunTime Exception enabling customized error messages and handling at runtime.There are three customer exceptions including InvalidCustomerDataException, CustomerNotFoundException, and DuplicateEmailException.



`f) application.properties` - This file sets up essential configurations for the CustomerAPI application.In this case we are using Docker to run the Spring Boot application postgreSQL database, so we can specify the server address and port. 
It also includes the log file location and specifies the database connection. In this case we have used Docker to run the database, so we specify the connection in docker.compose file, but in case we want to use a local instance of PostgreSQL, we specify the connection in this file for local development and testing.
`g) pom.xml`- The pom.xml file manages dependencies and build settings for the CustomerAPI project using Maven. It includes essential libraries for Spring Boot, Spring Data JPA, PostgreSQL, and testing frameworks. It also defines plugins for packaging the application as a runnable JAR and for Docker integration, ensuring a smooth build, test, and deployment process across environments.
`Note : Right now the credentials to the DB are hardcoded. In the future we can make use of env variables to avoid this.`


### Testing Part
### Unit Testing 
Unit Testing works on testing each individual unit of code, for example it will test only the functionality of a method, whether that method is working as expected and whether that method is doing what is specified. It tests each function in isolation without interference from external dependencies. For external dependencies it mocks its behavior. It mocks the behavior of external  libraries and dependencies, so that only the function is tested. This project has comprehensive unit tests for the CustomerCrudService and CustomerController classes, verifying that each service and controller operation works as expected, even in scenarios involving exceptions and validations.

- `CustomerCrudServcieTest:` The CustomerCrudServiceTest class tests the service layer by simulating interactions with the CustomerRepository 
 and handling various customer operations like adding, finding, updating, and deleting customers. Setup: Before each test, a mock 
 CustomerRepository and a CustomerCrudService instance are created, along with a sample Customer object for testing purposes.


    - `Add Customer`: Tests the normal addition, as well as validation cases for missing fields and duplicate emails.
      
    - `Find Customer`: Tests successful retrieval by ID and handling of a non-existent customer.
  
    - `Update Customer`: Ensures updates are handled correctly, verifying that changes to customer fields are saved.
      
    - `Delete Customer`: Confirms that deletion works as expected, with a single verification of the delete operation.

- `CustomerControllerTest` : The CustomerControllerTest class tests the controller layer, ensuring that API requests correctly call service methods and handle responses and exceptions as expected. It uses mocks for CustomerService to simulate behavior in different scenarios.
Test Cases:
    - `Create Customer:` Verifies that a successful customer creation returns HTTP 201 Created and the correct response body.
      
    - `Get Customer by ID:` Tests retrieval by ID, ensuring a 200 OK status when found.
      
    - `Update Customer:` Checks that updating customer data returns the updated data and a 200 OK status.
      
    - `Delete Customer:` Verifies that deletion by ID triggers the delete method and responds with HTTP 204 No Content.

### Integration Testing 
Integration testing ensures that different layers of the application work together as expected. In this case, the `CustomerControllerIntegrationTest` class verifies the behavior of the entire CustomerAPI by testing the controller endpoints (CustomerController) and their interactions with the service and repository layers, using a real TestRestTemplate to simulate HTTP requests. These tests cover the main operations: creating, retrieving, updating, and deleting customers.
To test that the CustomerController endpoints behave as expected when accessed over HTTP, including database interactions. Unlike unit tests, these integration tests do not mock dependencies; instead, they use a live database (in this case, H2 in-memory) to verify end-to-end functionality.

  - Port Configuration: The tests use a random port, configured with @SpringBootTest(webEnvironment = 
   SpringBootTest.WebEnvironment.RANDOM_PORT).
  - Headers: Common HTTP headers are set in @BeforeAll, specifying Content-Type as application/json.
  - Cleanup: After each test, customerRepository.deleteAll() clears the database for isolation.
  - Profile: Runs on a separate test profile to prevent interference with production settings.
  Test Cases Overview
  - Creating a Customer (testCreateCustomer): Sends a POST request to /api/customers with sample customer data. Verifies response 201 Created and correct data in the response body.
  - Retrieving a Customer by ID (testGetCustomerById): Saves a sample customer, then sends a GET request to /api/customers/{id}. Confirms response 200 OK and retrieved data match the saved customer.
  - Updating a Customer (testUpdateCustomer): Updates an existing customer by sending a PUT request to /api/customers/{id} with modified data. Checks response 200 OK and updated data in the response.
  - Deleting a Customer (testDeleteCustomer): Deletes a customer by sending a DELETE request to /api/customers/{id}.Ensures response 204 No Content and verifies the customer is deleted from the database.

`application-test.properties - This file handles the connection to the embedded H2 database used for integration testing.`






### 2) CustomerTestAPIClient (Java CLI Application)
The CustomerTestAPIClient is a command-line Java application that interacts with the CustomerAPI endpoints to manage customer records.

- Tech Stack: Java 17
- Functionality: This client application provides options to create, read, update, and delete customer records from the command line.
- Main Components:
 - Main Client: The main CLI interface provides options for different operations.
 - HTTP Client: Utilizes Java's HttpClient to send HTTP requests to CustomerAPI.
 - JSON Parsing: Processes responses from the API using JSON for seamless data handling.


#### 3. Instrumentation of API
To add observability to the API, I have implemented structured logging in my code, especially for my CRUD operations so that there is a clear log trail for every request and response action including errors. 
For that I added a dependency(SLF4J with Logback)  library in pom.xml for logging and added logging statements at different levels of INFO, WARN and ERROR. I logged Info logs in the Controller class to log when the request is received and in the service class so that information is logged when the operation is completed successfully and WARN logs when the service class is about to throw an exception. Similarly I have added ERROR logs in the GlobalException handler class so that exceptions or exception error messages are logged in the application logs 

In the future, to track metrics such as request count, request duration, and error rate, Micrometer with Prometheus integration can be added by setting up the Prometheus instance to scrape metrics from this endpoint (localhost:8080/actuator/prometheus). Micrometer will automatically provide basic metrics, but we  can also add custom counters in the service class.


	 	 	 		
			
				
					
### 4. Containerization :
To containerize the application, so that the application can be run on my device irrespective of the environment and platform, I have used Docker. Docker will package the entire application including its environment, dependencies and libraries into a Docker image which can then be used to run containers.

For this particular application, I had two things, the spring boot application and the postgreSQL DB that it was using to perform CRUD operations. I decided to create containers for both. PostgreSQL image was already available. I then created the Spring Boot docker image 

`Dockerfile` - This file created the CustomerAPI application docker image. It used a jdk17 image for the environment and then copied the already built customer api jar into the folder and then served an entry point as app.jar for the customerAPI docker container to start.


To ensure I don’t have to explicitly build and run Docker images for postgreSQL and customerapi, I created a docker-compose file which would first start the docker container for the postgreSQL database from the database image, it would initialize postgreSQL server with the credentials provided and  then  create the customerDB in it. After which it would build the customerAPI docker image and run the container. When the container was launched customer table was formed in the customerDB database from the Customer Entity class and the application was accessible at http://localhost:8080/api/customers

For the port mapping for customerAPI, I did 8080:8080 so that any requests coming to the localhost at 8080 port is forwarded to the customerAPI container which was listening at 8080 
and for the database I did the mapping from localhost 5433:  5432 of postgreSQL container. the customerAPI docker container will call the database container at 5432 and if we want to access the database from local host we can use  pgadmin(interface for running postgreSQL operation),  in which we can register a server at localhost 5433 which would then send the request to 5432. 


Also, the database url and credentials have to be provided in the compose.yml file for the spring boot application to connect with it 



				
### 5. CI/CD			

For building a CI/CD pipeline I would use Jenkins. The pipeline would have 5 stages 


### 1) Code Checkout Stage :
This stage fetches the latest code from the Git repository, ensuring that the pipeline runs with the most recent changes. 
- Action :  Using  a Git command or a CI tool’s built-in checkout step we can pull the latest code from the main branch or the branch that 
 triggered the pipeline.
- Automated Gates: Pipeline stops if the checkout fails, preventing further stages from running without the latest code.

### 2) Code Quality Analysis (SonarQube) 
This stage runs static code analysis using SonarQube to ensure code quality and identify potential issues like code smells, bugs, and security vulnerabilities.
- Action : Run SonarQube scan on the source code using mvn sonar:sonar (requires SonarQube server and token setup). This scan will analyze 
 code quality metrics, including maintainability, complexity,  security vulnerabilities, and duplications.
- Success Criteria: Code passes SonarQube quality gate thresholds (no critical issues or severe vulnerabilities).
- Automated Gates: Pipeline stops if SonarQube reports critical issues or fails quality gates.


		

### 3) Build Stage : 
This stage compiles the application and prepares artifacts for testing.

- Action:  Run mvn clean install -DskipTests to compile and package the application. Skip tests    in this stage since they are run in 
 subsequent testing stages.

- Success Criteria : Build completes without errors.
- Artifacts : Compiled JAR files(in the /target directory)

    
### 4) Unit Testing :  
Executes unit tests to validate individual components of the application, such as service and controller classes.
- Action : Run  mvn clean test -Dspring.profiles.active=test  to execute unit tests and generate test reports.
- Success Criteria : All unit tests pass with zero failures.
- Automated Gates: Pipeline halts if any unit tests fail.
- Artifacts : Test Reports


### 5) Integration Testing : 
Validates end-to-end functionality of CustomerAPI using in-memory database, checking API interactions and data persistence.
- Actions: Set up the test environment, including an H2 database, for isolated testing. Execute integration tests using  mvn clean verify -Dspring.profiles.active=test
- Success Criteria: All integration tests pass successfully.
- Automated Gates: Pipeline fail if any test fails
- Artifacts:Integration test reports


### 6) Docker Build Image : 
Creates Docker images for the application and database (PostgreSQL) after all tests pass.

- Actions: Use the Dockerfile to build a Docker image of the application. Tag and store the Docker image in a container registry.
- Success Criteria: Docker image is built and pushed successfully.
- Artifacts : Docker image (customerapi:latest).


### 7) Deployment to a Staging Environment
Deploys the application to a staging environment for further testing, with a manual gate before deploying to production.
- Action : Deploy the Docker image to a staging environment .Run API endpoint tests in staging.
- Success Criteria: All tests pass
- Automates Gates:All tests  must pass before proceeding to the manual gate.


### 8 ) Production Deployment : 
- Manual Gate: Approval is required from team leads before deploying to production.
- Actions: Deploy the Docker image to the production environment. 
- Success Criteria: Production deployment completes successfully, with all health checks and tests passing.






































