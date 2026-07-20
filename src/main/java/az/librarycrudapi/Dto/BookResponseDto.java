package az.librarycrudapi.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookResponseDto {

    private Long id;
    private String title;
    private String isbn;
    private Integer publicationYear;
    private String authorName;
    private Long borrowedByMemberId;
}