package az.librarycrudapi.Service;

import az.librarycrudapi.Exception.ResourceNotFoundException;
import az.librarycrudapi.Dto.AuthorRequestDto;
import az.librarycrudapi.Dto.AuthorResponseDto;
import az.librarycrudapi.Entity.Author;
import az.librarycrudapi.Repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public AuthorResponseDto create(AuthorRequestDto dto) {
        Author author = new Author();
        author.setFullName(dto.getFullName());
        author.setCountry(dto.getCountry());

        Author saved = authorRepository.save(author);
        return toResponseDto(saved);
    }

    public Page<AuthorResponseDto> getAll(Pageable pageable) {
        return authorRepository.findAll(pageable)
                .map(this::toResponseDto);
    }

    public AuthorResponseDto getById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author tapilmadi " + id));
        return toResponseDto(author);
    }

    public AuthorResponseDto update(Long id, AuthorRequestDto dto) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author tapilmadi " + id));

        author.setFullName(dto.getFullName());
        author.setCountry(dto.getCountry());

        Author updated = authorRepository.save(author);
        return toResponseDto(updated);
    }

    public void delete(Long id) {
        if (!authorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Author tapilmadi " + id);
        }
        authorRepository.deleteById(id);
    }

    private AuthorResponseDto toResponseDto(Author author) {
        AuthorResponseDto dto = new AuthorResponseDto();
        dto.setId(author.getId());
        dto.setFullName(author.getFullName());
        dto.setCountry(author.getCountry());
        return dto;
    }
}