# Library Management CRUD API - Week 3 & Week 4

Bu layihə, Kitabxana İdarəetmə Sisteminin backend tərəfini təşkil edən, Spring Boot və Spring Security əsaslı professional bir RESTful API layihəsidir. Week 3 mentor rəyləri tamamilə aradan qaldırılmış və Week 4 çərçivəsində layihənin performansı və funksionallığı qabaqcıl səviyyəyə çatdırılmışdır.

## 🛠 Texnoloji Stack
* **Java 21** & **Spring Boot 4.x / 5.x**
* **Spring Security 7.x** & **JWT (Json Web Token)**
* **Spring Data JPA** & **PostgreSQL**
* **Spring Cache** (In-Memory / ConcurrentMap)
* **Spring Doc OpenAPI 3** (Swagger UI)
* **Gradle**

---

## 🎯 Week 3 Mentor İradlarının Tam Düzəlişləri
1. **Mürəkkəb JPQL Sorğusu:** `BookRepository` daxilinə `GROUP BY` və `HAVING` şərtləri istifadə olunaraq populyar kitabları sifariş miqdarına görə süzgəcləyən qabaqcıl `@Query` sorğusu əlavə edildi.
2. **N+1 Sorğu Probleminin Kökündən Həlli:** Həm normal listələmə, həm də Specification əsaslı dinamik axtarış (`searchOrders`) metodlarında yaranan N+1 problemi `orderItems.book` zəncirvari yolu `@EntityGraph`-a daxil edilərək tamamilə həll olundu.
3. **Redundant (Artıq) Save Əməliyyatı:** `OrderService` daxilindəki artıq olan ilk `save()` çağırışı silindi, Cascade mexanizminin effektiv işləməsi təmin olundu və verilənlər bazası performansı optimallaşdırıldı.
4. **Real Rollback İntegasiya Testi:** Mockito unit testləri əvəzinə, real Spring Context və verilənlər bazası mühitində tranzaksiyanın uğursuzluq anında məlumatları necə geri qaytardığını (rollback) yoxlayan real `@SpringBootTest` inteqrasiya testi yazıldı.

---

## 🚀 Week 4 Yeni Funksionallıqlar (Checkpoint 1 - 7)

### 1. Keşləmə Mexanizmi & Eviction (Checkpoint 1 & 5)
* `BookService` daxilində tez-tez oxunan `getById` metodu `@Cacheable` annotasiyası ilə keşləndi.
* Data bütövlüyünü qorumaq və istifadəçiyə həmişə ən son məlumatı göstərmək üçün kitab yenilənəndə (`PUT`) və ya silinəndə (`DELETE`) köhnəlmiş keş məlumatları `@CacheEvict` vasitəsilə avtomatik etibarsızlaşdırılır.
* `CacheConfig` sinfi yaradılaraq daxili `ConcurrentMapCacheManager` konfiqurasiya olundu.

### 2. Təhlükəsiz Fayl Yükləmə və Endirmə (Checkpoint 2)
* `/api/files/upload` və `/api/files/download/{filename}` endpointləri yaradıldı.
* **Magic Bytes / MIME Type Yoxlanışı:** Faylın tipi sadəcə adına və ya uzantısına görə yox, `URLConnection.guessContentTypeFromStream` ilə həqiqi daxili strukturuna görə validasiya olunur.
* Maksimum 5MB ölçü limiti və yalnız JPEG, PNG, PDF faylları üçün sərt whitelist tətbiq edildi.

### 3. Asinxron Emal (Checkpoint 4)
* `@Async` dəstəyi ilə əsas axını (main thread) bloklamadan, arxa plonda e-poçt / sifariş bildirişlərinin göndərilməsini simulyasiya edən `NotificationService` quruldu və `createOrder` anında tətikləndi.

### 4. Planlaşdırılmış Tapşırıqlar (Checkpoint 3)
* `@Scheduled` istifadə edilərək hər gecə yarısı (Midnight) avtomatik işə düşən və sistem statistikalarını/təmizləmələrini idarə edən `ScheduledTaskService` tətbiq olundu.

### 5. Profillərin Ayrılması və Xarici Konfiqurasiya (Checkpoint 6)
* Konfiqurasiyalar `application.yml` daxilindən xaricə çıxarıldı. Layout mühitləri üçün `dev` profili ayrıldı (`application-dev.yml`).
* Spring Boot-un daxili Thread Pool-larının toqquşmaması üçün asinxron, keşləmə və planlaşdırma annotasiyaları müstəqil konfiqurasiya klaslarına (`AppConfig`, `CacheConfig`) köçürüldü.

### 6. Swagger Sənədləşdirilməsi (Checkpoint 7)
* Yeni əlavə olunan fayl idarəetmə endpointləri OpenApi v3 standartlarına uyğun olaraq `@Tag` və `@Operation` annotasiyaları ilə tam sənədləşdirildi.

---

## 🏃‍♂️ Layihəni Başlatmaq

Layihəni `dev` profili ilə birbaşa işə salmaq üçün:

```bash
./gradlew bootRun
```

Swagger UI sənədləşməsinə daxil olmaq üçün brauzerdə bu ünvana keçid edin:
`http://localhost:8080/swagger-ui.html`
