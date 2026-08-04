package az.librarycrudapi.Dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderResponseDto {

    private Long id;
    private LocalDateTime orderDate;
    private String status;
    private Long memberId;
    private String memberName;
    private List<OrderItemResponseDto> items;
}
