package az.librarycrudapi.Controller;

import az.librarycrudapi.Dto.BookRequestDto;
import az.librarycrudapi.Dto.BookResponseDto;
import az.librarycrudapi.Service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@Tag(name = "Book Controller", description = "Kitab emeliyyatlarinin idare edilmesi")
public class BookController {

    private final BookService bookService;

    @PostMapping
    @Operation(summary = "Yeni kitabin yaradilmasi")
    public ResponseEntity<BookResponseDto> create(@Valid @RequestBody BookRequestDto dto) {
        BookResponseDto created = bookService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    @Operation(summary = "Butun kitablarin siyahisi (Pagination ile)")
    public ResponseEntity<Page<BookResponseDto>> getAll(Pageable pageable) {
        return ResponseEntity.ok(bookService.getAll(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "ID-ye gore kitabin tapilmasi")
    public ResponseEntity<BookResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Kitab melumatlarinin yenilenmesi")
    public ResponseEntity<BookResponseDto> update(@PathVariable Long id, @Valid @RequestBody BookRequestDto dto) {
        return ResponseEntity.ok(bookService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Kitabin silinmesi")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
