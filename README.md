# Library CRUD API

Spring Boot, Java 21 və PostgreSQL istifadə edilərək yaradılmış kitabxana idarəetmə sistemi. Layihə qatlı arxitektura (Controller, Service, Repository) ilə qurulmuşdur.

## Qurma addımları
1. **Verilənlər bazası:** PostgreSQL-də `library_crud_api_db` bazasını yaradın.
2. **Konfiqurasiya:** `src/main/resources/application.yaml` faylını öz məlumatlarınızla yeniləyin.
3. **İşə salma:** Terminalda `./gradlew bootRun` əmrini işlədin. Tətbiq `http://localhost:8080` ünvanında işləyəcək.
4. **API sənədləşməsi (Swagger):** `http://localhost:8080/swagger-ui.html` linkindən istifadə edin.
5. **Test:** `./gradlew test` əmri ilə testləri yoxlayın.

## Strukturu
`src/main/java/az/librarycrudapi/` altında Controller, Service, Repository, Entity, Dto, Exception paketləri mövcuddur.
