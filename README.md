# URL Shortener Service

A production-style scalable URL shortener backend inspired by Bitly.

This project demonstrates backend engineering concepts including:

* REST API development
* Redis caching
* H2 databse integration
* Dockerized infrastructure
* Layered architecture
* URL redirection handling
* Base62 encoding

---

#  Tech Stack

* Java 17
* Spring Boot
* H2 database
* Redis
* Docker
* Maven
* Spring Data JPA
* Swagger/OpenAPI

---

#  Features

* Shorten long URLs
* Redirect shortened URLs
* Redis caching for faster lookup
* Custom short aliases
* Click analytics
* URL expiry support
* RESTful APIs
* Dockerized setup
* Swagger documentation

---

# Architecture

```txt
Client
   ↓
Controller Layer
   ↓
Service Layer
   ↓
Repository Layer
   ↓
PostgreSQL

Redis used for caching hot URLs
```

---

# Project Structure

```txt
url-shortener/
│
├── src/main/java/com/prashant/urlshortener
│   ├── controller
│   ├── service
│   ├── repository
│   ├── entity
│   ├── dto
│   ├── util
│   ├── config
│   ├── exception
│   └── UrlShortenerApplication.java
│
├── src/main/resources
│   └── application.yml
│
├── Dockerfile
├── docker-compose.yml
└── pom.xml
```

---

# API Endpoints

## Create Short URL

### Request

```http
POST /api/v1/shorten
Content-Type: application/json
```

```json
{
  "url": "https://leetcode.com/problems/two-sum"
}
```

### Response

```json
{
  "originalUrl": "https://leetcode.com/problems/two-sum",
  "shortUrl": "http://localhost:8080/abc12"
}
```

---

## Redirect URL

```http
GET /{shortCode}
```

Example:

```txt
http://localhost:8080/abc12
```

Redirects to original URL.

---

# 🗄️ Database Schema

## Table: short_urls

| Column       | Type        |
| ------------ | ----------- |
| id           | BIGSERIAL   |
| original_url | TEXT        |
| short_code   | VARCHAR(20) |
| click_count  | BIGINT      |
| created_at   | TIMESTAMP   |
| expiry_at    | TIMESTAMP   |

---

# ⚡ Redis Caching Flow

```txt
Client Request
      ↓
Redis Cache
      ↓ miss
H2 data base
      ↓
Redis Update
```

Frequently accessed URLs are cached in Redis to reduce database load and improve redirect latency.

---

# 🐳 Running Locally

## Prerequisites

* Java 17
* Maven

---

## Step 1 — Clone Repository

```bash
git clone <your-repository-url>
cd url-shortener
```

---

## Step 2 — Start PostgreSQL and Redis

```bash
docker-compose up -d
```

---

## Step 3 — Run Application

```bash
mvn spring-boot:run
```

---

# 📖 Swagger API Documentation

Open:

```txt
http://localhost:8080/swagger-ui/index.html
```

---

# 🐋 Docker Setup

## docker-compose.yml

```yaml
version: '3.9'

services:

  redis:
    image: redis:latest
    container_name: redis
    ports:
      - "6379:6379"
```

---

# Future Improvements

* JWT Authentication
* Kafka-based analytics pipeline
* Rate limiting using Redis
* Distributed ID generation
* Kubernetes deployment
* Prometheus + Grafana monitoring
* QR code generation
* User dashboard
* Custom domains

---

# Learning Outcomes

This project helped in understanding:

* REST API design
* Distributed system concepts
* Database optimization
* Caching strategies
* Docker containerization
* Backend scalability patterns

---
Prashant Mishra
