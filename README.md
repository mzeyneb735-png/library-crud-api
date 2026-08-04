# Library CRUD API

Spring Boot, Java 21 və PostgreSQL istifadə edilərək yaradılmış kitabxana idarəetmə sistemi. Layihə qatlı arxitektura (Controller, Service, Repository, Entity, Dto, Exception) ilə qurulmuşdur.

## Qurma addımları

1. **Verilənlər bazası:** PostgreSQL-də `library_crud_api_db` bazasını yaradın.
2. **Konfiqurasiya:** `src/main/resources/application.yaml` faylını öz məlumatlarınızla (PostgreSQL user, password və db url) yeniləyin.
3. **İşə salma:** Terminalda `./gradlew bootRun` əmrini işlədin. Tətbiq `http://localhost:8080` ünvanında işləyəcək.
4. **API sənədləşməsi (Swagger):** `http://localhost:8080/swagger-ui.html` linkindən istifadə edin.
5. **Test:** `./gradlew test` əmri ilə testləri ylayın.

## Strukturu

`src/main/java/az/librarycrudapi/` altında aşağıdakı paketlər mövcuddur:
* **Config:** Security və Filter konfiqurasiyaları
* **Controller:** API endpoint-ləri
* **Dto:** Məlumat ötürmə obyektləri (Request və Response)
* **Entity:** Verilənlər bazası cədvəlləri
* **Exception:** Qlobal xəta idarəetməsi
* **Repository:** Data qatındakı interfeyslər
* **Service:** Biznes məntiqi qatı

## Son Yeniliklər və Mexanizmlər

### Week 2 Düzəlişləri
* **JWT Fallback Silindi:** `JwtService` daxilindəki default secret key silindi, sistemin zəif açarla işə düşməsinin qarşısı alındı.
* **Xüsusi Xətalar:** `UserAlreadyExistsException` (409 Conflict) və `BadCredentialsException` (401 Unauthorized) xətaları yaradıldı və `GlobalExceptionHandler` qatında idare olundu.
* **Token Expired İdarəetməsi:** Tokenin vaxtı bitdikdə `JwtFilter` bunu catch edir və `HandlerExceptionResolver` vasitəsilə geriyə təmiz 401 JSON cavabı qaytarır.

### Week 3 Funksionallığı
* **Cədvəl Əlaqələri:** `Category` və `Book` arasında Many-to-Many, `Order` və `OrderItem` arasında One-to-Many əlaqələri düzgün quruldu.
* **N+1 Sorğu Həlli:** Sifarişləri gətirərkən yaranan N+1 probleminin qarşısını almaq üçün `OrderRepository`-də `@EntityGraph(attributePaths = {"orderItems", "member"})` istifadə olundu.
* **Dinamik Filtrləmə:** `Specification API` istifadə edilərək status, memberId və tarixə görə dinamik axtarış endpoint-i (`/api/orders/search`) yaradıldı.
* **Tranzaksiya İdarəetməsi:** Yeni sifariş yaradılarkən birdən çox cədvələ eyni anda yazma əməliyyatı üçün `@Transactional` annotasiyasi tətbiq olundu.
* **Rollback Testi:** `OrderServiceTest` daxilində kitabın tapılmaması halında tranzaksiyanın uğurla geri çəkildiyini (rollback olunduğunu) yoxlayan Mockito Unit Testi yazıldı.
