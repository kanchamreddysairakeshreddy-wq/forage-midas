# Midas Core – JPMorgan Chase Software Engineering Virtual Experience

A Spring Boot–based financial transaction processing system developed as part of the JPMorgan Chase Software Engineering Virtual Experience Program on Forage.

The application processes financial transactions through Apache Kafka, validates account activity, persists transaction records, integrates with an external incentive service, updates user balances, and exposes account balance information through a REST API.

---

## Overview

Midas Core is an event-driven transaction processing service that receives transactions from Kafka, validates business rules, stores transaction history in a relational database, communicates with an external Incentive API, and maintains accurate user balances.

The system demonstrates modern backend development concepts including messaging systems, REST APIs, database persistence, and microservice communication.

---

## Features

- Real-time transaction processing using Apache Kafka
- Transaction validation and balance verification
- H2 database integration with Spring Data JPA
- Persistent transaction history storage
- Incentive API integration using REST communication
- Automatic balance updates for users
- REST endpoint for balance retrieval
- Event-driven architecture

---

## Architecture

```text
Kafka Producer
      |
      v
+----------------------+
|   Kafka Topic        |
|   transactions       |
+----------------------+
      |
      v
+----------------------+
| Kafka Transaction    |
| Listener             |
+----------------------+
      |
      +--------------------+
      |                    |
      v                    v
Transaction          Incentive API
Validation           (REST Service)
      |                    |
      +---------+----------+
                |
                v
        Database Updates
                |
                v
          H2 Database
                |
                v
        Balance REST API
```

---

## Technology Stack

| Technology | Purpose |
|------------|----------|
| Java 17 | Core Development |
| Spring Boot | Application Framework |
| Apache Kafka | Event Streaming |
| Spring Kafka | Kafka Integration |
| Spring Data JPA | Database Access |
| Hibernate | ORM |
| H2 Database | Persistence Layer |
| Maven | Dependency Management |
| REST APIs | Service Communication |

---

## Core Components

### Kafka Transaction Listener

Consumes transaction events from Kafka topics and initiates transaction processing.

### Transaction Validation Engine

Transactions are processed only when:

- Sender exists
- Recipient exists
- Sender has sufficient balance

Invalid transactions are discarded without modifying stored data.

### Database Persistence

Implemented using Spring Data JPA and Hibernate.

#### UserRecord

Stores:

- User ID
- User Name
- Account Balance

#### TransactionRecord

Stores:

- Sender
- Recipient
- Transaction Amount
- Incentive Amount

Maintains proper entity relationships between users and transactions.

### Incentive API Integration

Valid transactions are sent to an external Incentive API.

The returned incentive amount:

- Is stored with the transaction
- Is credited to the recipient
- Does not reduce the sender balance

### Balance REST API

Provides balance information for users through a REST endpoint.

---

## API Endpoint

### Get User Balance

```http
GET /balance?userId={id}
```

### Example Request

```http
GET /balance?userId=1
```

### Example Response

```json
{
  "amount": 1250.75
}
```

If the user does not exist:

```json
{
  "amount": 0
}
```

---

## Database Model

### UserRecord

| Field | Type |
|---------|---------|
| id | Long |
| name | String |
| balance | Float |

### TransactionRecord

| Field | Type |
|---------|---------|
| id | Long |
| sender | UserRecord |
| recipient | UserRecord |
| amount | Float |
| incentive | Float |

---

## Running the Project

### Start the Incentive Service

```bash
java -jar services/transaction-incentive-api.jar
```

### Run Midas Core

```bash
./mvnw spring-boot:run
```

Application runs on:

```text
http://localhost:33400
```

---

## Testing

Run individual task validations:

```bash
./mvnw test -Dtest=TaskOneTests
```

```bash
./mvnw test -Dtest=TaskTwoTests
```

```bash
./mvnw test -Dtest=TaskThreeTests
```

```bash
./mvnw test -Dtest=TaskFourTests
```

```bash
./mvnw test -Dtest=TaskFiveTests
```

---

## Key Concepts Demonstrated

- Event-Driven Architecture
- Apache Kafka Consumers
- REST API Development
- Service-to-Service Communication
- Spring Boot Development
- Database Design
- JPA Entity Relationships
- Transaction Processing Systems
- Financial Data Validation
- Backend System Integration

---

## Project Outcome

Successfully implemented an end-to-end financial transaction processing workflow capable of:

- Consuming transactions from Kafka
- Validating business rules
- Persisting transaction data
- Integrating with external services
- Updating user balances
- Providing balance lookup through a REST API

This project demonstrates practical experience with enterprise Java development patterns commonly used in modern financial systems.

---

## Author

**Sai Rakesh Reddy**

JPMorgan Chase Software Engineering Virtual Experience (Forage)
