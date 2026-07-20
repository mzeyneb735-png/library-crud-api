package az.librarycrudapi.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorResponseDto {

    private Long id;
    private String fullName;
    private String country;
}