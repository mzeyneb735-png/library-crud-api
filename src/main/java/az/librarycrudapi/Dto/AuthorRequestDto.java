package az.librarycrudapi.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorRequestDto {

    @NotBlank(message = "Ad boş ola bilmez")
    @Size(min = 2, max = 100)
    private String fullName;

    @NotBlank(message = "Ölke boş ola bilmez")
    private String country;
}