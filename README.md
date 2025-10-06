Online Quiz API

A Spring Boot REST API for creating and taking quizzes online. Supports single-choice, multiple-choice, and text-based questions with automated evaluation.


      -------------------------------------------------------------------------------------------------------------------

      

Project Overview

The Online Quiz API allows:

Creating a quiz with a title.

Adding questions of type: SINGLE, MULTIPLE, TEXT.

Submitting answers and receiving an automatic score.

Keyword-based evaluation for text answers with an 80% match threshold.

Retrieving all available quizzes with questions (without exposing correct answers).



Technologies Used

Java 17

Spring Boot 3.x with Maven setup

Spring Data JPA / Hibernate

H2 Database (in-memory)

JUnit 5 + Mockito for unit testing

ModelMapper for DTO mapping

Swagger/OpenAPI for API documentation


## ⚙️ Setup Instructions

### 1. Clone the repository

```bash
git clone https://github.com/sailm11/Online_Quiz_API.git
cd Online_Quiz_API
```

2. Configure H2 Database

The API uses H2 in-memory database, so no installation is needed.

3. Build and Run

   ./mvnw clean install
  ./mvnw spring-boot:run
   
API will be accessible at:
👉 http://localhost:8081/


📖 API Documentation (Swagger)

Swagger UI is integrated for interactive API testing and documentation.

🔗 Access Swagger at:
http://localhost:8081/swagger-ui/index.html


🧪 Testing

Unit tests validate all major functionalities:

Scoring for text, single, and multiple-choice questions

Keyword-based evaluation accuracy

Error handling for invalid submissions



🧩 Project Structure

```bash 
src/main/java/com/quizzy/v1/
├─ controller
│   └─ QuizController.java
├─ service
│   ├─ QuizServiceImpl.java
│   └─ QuizEvaluationServiceImpl.java
├─ model
│   ├─ Quiz.java
│   ├─ Question.java
│   └─ Answer.java
├─ repository
│   ├─ QuizRepository.java
│   └─ AnswerRepository.java
└─ dto
    ├─ request
    │   ├─ QuizSubmissionDto.java
    │   └─ AnswerSubmissionDto.java
    └─ response
        └─ QuizResultDto.java

```
