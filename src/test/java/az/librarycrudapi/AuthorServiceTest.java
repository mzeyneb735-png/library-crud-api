package az.librarycrudapi;

import az.librarycrudapi.Dto.AuthorRequestDto;
import az.librarycrudapi.Dto.AuthorResponseDto;
import az.librarycrudapi.Entity.Author;
import az.librarycrudapi.Exception.ResourceNotFoundException;
import az.librarycrudapi.Repository.AuthorRepository;
import az.librarycrudapi.Service.AuthorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    @Test
    void shouldCreateAuthor() {
        AuthorRequestDto dto = new AuthorRequestDto();
        dto.setFullName("Cəfər Cabbarlı");
        dto.setCountry("Azərbaycan");

        Author saved = new Author();
        saved.setId(1L);
        saved.setFullName("Cəfər Cabbarlı");
        saved.setCountry("Azərbaycan");

        when(authorRepository.save(any(Author.class))).thenReturn(saved);

        AuthorResponseDto result = authorService.create(dto);

        assertNotNull(result);
        assertEquals("Cəfər Cabbarlı", result.getFullName());
        assertEquals(1L, result.getId());
    }

    @Test
    void shouldThrowExceptionWhenAuthorNotFound() {
        when(authorRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            authorService.getById(999L);
        });
    }
}