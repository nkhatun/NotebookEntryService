## Notebook Entry Service
Maven based spring boot application for Notebook Entry assignment task

## Description
The repository contains client side (**NotebookFront**) and server side(**NotebookEntryTask**) application for the task. 

## Scope
1. Creating a notebook entry (default TEXT type). Assumed the entry is being created under a fixed project named 'Assignment Task'.
2. Searching a given text frquency occurence from the created notebook entry 
3. Searching list of similar words of a given text from the created notebook entry whose Levenshtein distance is not more than 1.

## Technology
- **Spring Boot**     - Server side framework
- **Lombok**          - Provides automated getter/setters
- **Actuator**        - Application insights on the fly
- **Spring Security** - Spring's security layer
- **Swagger**         - In-built swagger2 documentation support
- **Junit**           - Unit testing framework
- **Angular**         - Client side framework

## Application Set up and Build Steps
**Frontend** 
Run the below command from the root directory
1. `npm install` 
2. `npm run build` 

**Backend** 
Run the below command from the root directory
1. `mvn clean install`

## Running the server locally
**Frontend** 
Run the below command from the root directory
1. `npm start` 
URL: http://<host-name>:4200/

**Backend** 
Run the below command from the root directory
1. `mvn spring-boot:run`
URL: http://<host-name>:8085/
  
## Swagger Documentation
Swagger documentation for  can be accessed at the following URL -
http://<host-name>:8085/swagger-ui.html
username: demo
password: demo

## Unit test cases
**Backend** 
Run the below command from the root directory
1. `mvn clean test`

## Time Spent
**Backend**  4 hr
**Frontend**  2 hr

## ENHANCEMENT SCOPE
**Backend** 
1. Dockerizing the application
2. JPA integration
3. Performance Testing

**Frontend** 
1. Allowing notebook entry per project
2. Allowing file type entry
3. Cosmetic Fixes
4. Responsive Design
