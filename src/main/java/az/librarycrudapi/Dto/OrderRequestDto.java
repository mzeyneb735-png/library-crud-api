package az.librarycrudapi.Dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class OrderRequestDto {

    @NotNull(message = "Uzv id-si teleb olunur")
    private Long memberId;

    @NotEmpty(message = "Sifaris elementleri bos ola bilmez")
    private List<OrderItemRequestDto> items;
}
