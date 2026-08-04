package az.librarycrudapi.Dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemRequestDto {

    @NotNull(message = "Kitab id-si teleb olunur")
    private Long bookId;

    @NotNull(message = "Miqdar teleb olunur")
    @Positive(message = "Miqdar musbet olmalidir")
    private Integer quantity;

    @NotNull(message = "Qiymet teleb olunur")
    @Positive(message = "Qiymet musbet olmalidir")
    private Double price;
}
