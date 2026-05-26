JPMorgan Chase Forage – Midas Core Project
Overview

This project was completed as part of the JPMorgan Chase Software Engineering Virtual Experience Program (Forage).

The goal of the project was to enhance and extend the Midas Core transaction processing system through a series of progressively complex engineering tasks involving:

Apache Kafka
Spring Boot
REST APIs
H2 Database
JPA/Hibernate
Event-driven architecture
Microservice communication

The application processes financial transactions received through Kafka, validates them, persists them to a database, integrates with an external Incentive API, and exposes account balances through a REST endpoint.

Tech Stack
Java 17
Spring Boot
Spring Kafka
Spring Data JPA
Hibernate
H2 Database
Maven
REST APIs
Apache Kafka
Project Architecture
Producer
   |
   v
Kafka Topic (transactions)
   |
   v
KafkaTransactionListener
   |
   +--> Validate Transaction
   |
   +--> Query Incentive API
   |
   +--> Update User Balances
   |
   +--> Persist Transaction
   |
   v
H2 Database
   |
   v
Balance REST API
Tasks Completed
Task 1 – Kafka Consumer Integration
Objective

Configure Midas Core to consume transaction events from Kafka.

Implementation
Configured Kafka consumer.
Created Kafka listener using @KafkaListener.
Verified transaction messages were successfully received from the configured topic.
Key Concepts
Event-driven architecture
Kafka consumers
Spring Kafka
Task 2 – Kafka Topic Configuration
Objective

Ensure Midas Core correctly subscribes to the required Kafka topic.

Implementation
Updated Kafka topic configuration inside application.yml.
Fixed incorrect topic name.
Verified successful message consumption.
Key Learning

Configuration mistakes can completely block event processing even when application code is correct.

Task 3 – Database Integration and Transaction Validation
Objective

Persist valid transactions and update user balances.

Business Rules

A transaction is valid only if:

Sender exists
Recipient exists
Sender balance is greater than or equal to transaction amount
Implementation

Created:

TransactionRecord

entity containing:

sender
recipient
amount

Implemented:

User lookup
Balance validation
Balance updates
Transaction persistence
JPA Relationships
UserRecord (1)
    |
    |----< TransactionRecord

Both sender and recipient maintain a many-to-one relationship with users.

Key Concepts
JPA Entities
Entity Relationships
Data Persistence
Transaction Validation
Task 4 – Incentive API Integration
Objective

Integrate Midas Core with an external Incentive API.

Implementation

Created:

IncentiveQuerier

using:

RestTemplate

Workflow:

Receive transaction.
Validate transaction.
Call Incentive API.
Receive incentive amount.
Store incentive with transaction.
Add incentive to recipient balance.
Example

Transaction:

Sender -> 100
Recipient -> +100
Incentive -> +5

Result:

Sender loses 100
Recipient gains 105
Key Concepts
REST Clients
Spring RestTemplate
Service-to-Service Communication
JSON Serialization
Task 5 – Balance REST API
Objective

Expose account balances through a REST endpoint.

Endpoint
GET /balance?userId={id}
Example
GET /balance?userId=1

Response:

{
  "amount": 1250.75
}

If user does not exist:

{
  "amount": 0
}
Implementation

Created:

BalanceController

Configured application to run on:

Port 33400
Key Concepts
Spring MVC
REST Controllers
Request Parameters
JSON Responses
Database Model
UserRecord
Field	Type
id	Long
name	String
balance	float
TransactionRecord
Field	Type
id	Long
sender	UserRecord
recipient	UserRecord
amount	float
incentive	float
How to Run
Start Incentive API
java -jar services/transaction-incentive-api.jar
Run Midas Core
./mvnw spring-boot:run
Run Individual Tasks
./mvnw test -Dtest=TaskOneTests
./mvnw test -Dtest=TaskTwoTests
./mvnw test -Dtest=TaskThreeTests
./mvnw test -Dtest=TaskFourTests
./mvnw test -Dtest=TaskFiveTests
Key Engineering Learnings
Building Kafka consumers using Spring Kafka
Event-driven system design
Integrating relational databases with Spring Data JPA
Modeling entity relationships
Consuming external REST APIs
Designing RESTful endpoints
Debugging distributed systems
Working with financial transaction validation logic
End-to-end transaction processing pipelines
Outcome

Successfully completed all five tasks of the JPMorgan Chase Forage Midas Core project by implementing:

✅ Kafka transaction processing

✅ Topic configuration and event consumption

✅ H2 database persistence

✅ Transaction validation and balance updates

✅ Incentive API integration

✅ REST API for balance retrieval

✅ End-to-end tested financial transaction workflow
