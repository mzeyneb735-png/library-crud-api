package az.librarycrudapi.Controller;

import az.librarycrudapi.Dto.OrderRequestDto;
import az.librarycrudapi.Dto.OrderResponseDto;
import az.librarycrudapi.Service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Order Controller", description = "Sifaris emeliyyatlarinin idare edilmesi")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @Operation(summary = "Yeni sifarisin yaradilmasi (Transactional)")
    public ResponseEntity<OrderResponseDto> createOrder(@Valid @RequestBody OrderRequestDto dto) {
        OrderResponseDto created = orderService.createOrder(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    @Operation(summary = "Sifarislerin getirilmesi (N+1 helli ile)")
    public ResponseEntity<Page<OrderResponseDto>> getAllOrders(Pageable pageable) {
        return ResponseEntity.ok(orderService.getAllOrders(pageable));
    }

    @GetMapping("/search")
    @Operation(summary = "Dinamik filtrlendirme ve axtaris (Specification API)")
    public ResponseEntity<Page<OrderResponseDto>> searchOrders(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Long memberId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date,
            Pageable pageable) {
        return ResponseEntity.ok(orderService.searchOrders(status, memberId, date, pageable));
    }
}
