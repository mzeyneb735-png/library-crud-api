package az.librarycrudapi.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookRequestDto {

    @NotBlank(message = "Baslıg bos ola bilmez")
    private String title;

    @NotBlank(message = "ISBN bos ola bilmez")
    private String isbn;

    private Integer publicationYear;

    @NotNull(message = "Muellif id-si teleb olunur")
    private Long authorId;
}