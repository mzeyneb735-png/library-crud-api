package az.librarycrudapi.Dto;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
public class BookResponseDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String title;
    private String isbn;
    private Integer publicationYear;
    private String authorName;
    private Long borrowedByMemberId;
}
