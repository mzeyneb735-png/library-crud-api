package az.librarycrudapi.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemResponseDto {

    private Long id;
    private Long bookId;
    private String bookTitle;
    private Integer quantity;
    private Double price;
}
