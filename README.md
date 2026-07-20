# Library CRUD API

Spring Boot ile yazilmis kitabxana idareetme sistemi. Author, Book ve Member ucun tam CRUD emeliyyatlari var. Qatli arxitektura ile qurulub: Controller -> Service -> Repository -> DTO.

## Istifade olunan texnologiyalar

Java 21, Spring Boot 4.1.0, Spring Data JPA, PostgreSQL, Lombok, Springdoc OpenAPI (Swagger), JUnit 5, Mockito

## Qurma addimlari

1. Lazim olanlar: JDK 21 ve PostgreSQL

2. Veriler bazasini yarat:
   CREATE DATABASE library_crud_api_db;

3. application.yaml faylini duzelt (src/main/resources/application.yaml):
   spring.datasource.url=jdbc:postgresql://localhost:5432/library_crud_api_db
   spring.datasource.username=postgres
   spring.datasource.password=parolunuz

4. Layiheni ise sal:
   ./gradlew bootRun

Tetbiq http://localhost:8080 unvaninda acilacaq.

## Swagger

Butun endpoint-leri gormek ucun:
http://localhost:8080/swagger-ui.html

## Endpoint-ler

POST /api/authors - Muellif yarat
GET /api/authors - Butun muellifleri getir
GET /api/authors/{id} - Bir muellifi getir
PUT /api/authors/{id} - Muellifi yenile
DELETE /api/authors/{id} - Muellifi sil

Ayni struktur /api/books ve /api/members ucun de var.

## Testler

./gradlew test

## Fayl strukturu

src/main/java/az/librarycrudapi/ altinda: Controller, Service, Repository, Entity, Dto, Exception qovluqlari var.