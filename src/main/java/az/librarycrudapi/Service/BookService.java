package az.librarycrudapi.Service;

import az.librarycrudapi.Exception.ResourceNotFoundException;
import az.librarycrudapi.Dto.BookRequestDto;
import az.librarycrudapi.Dto.BookResponseDto;
import az.librarycrudapi.Entity.Author;
import az.librarycrudapi.Entity.Book;
import az.librarycrudapi.Repository.AuthorRepository;
import az.librarycrudapi.Repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookResponseDto create(BookRequestDto dto) {
        Author author = authorRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("Author tapilmadi " + dto.getAuthorId()));

        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setIsbn(dto.getIsbn());
        book.setPublicationYear(dto.getPublicationYear());
        book.setAuthor(author);

        Book saved = bookRepository.save(book);
        return toResponseDto(saved);
    }

    public Page<BookResponseDto> getAll(Pageable pageable) {
        return bookRepository.findAll(pageable)
                .map(this::toResponseDto);
    }

    public BookResponseDto getById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Kitab tapilmadi " + id));
        return toResponseDto(book);
    }

    public BookResponseDto update(Long id, BookRequestDto dto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Kitab tapilmadi " + id));

        Author author = authorRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("Author tapilmadi " + dto.getAuthorId()));

        book.setTitle(dto.getTitle());
        book.setIsbn(dto.getIsbn());
        book.setPublicationYear(dto.getPublicationYear());
        book.setAuthor(author);

        Book updated = bookRepository.save(book);
        return toResponseDto(updated);
    }

    public void delete(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Kitab tapilmadi " + id);
        }
        bookRepository.deleteById(id);
    }

    private BookResponseDto toResponseDto(Book book) {
        BookResponseDto dto = new BookResponseDto();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setIsbn(book.getIsbn());
        dto.setPublicationYear(book.getPublicationYear());
        dto.setAuthorName(book.getAuthor().getFullName());
        if (book.getBorrowedBy() != null) {
            dto.setBorrowedByMemberId(book.getBorrowedBy().getId());
        }
        return dto;
    }
}
