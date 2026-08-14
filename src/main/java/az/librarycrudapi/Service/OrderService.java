package az.librarycrudapi.Service;

import az.librarycrudapi.Dto.OrderRequestDto;
import az.librarycrudapi.Dto.OrderResponseDto;
import az.librarycrudapi.Dto.OrderItemResponseDto;
import az.librarycrudapi.Entity.Book;
import az.librarycrudapi.Entity.Member;
import az.librarycrudapi.Entity.Order;
import az.librarycrudapi.Entity.OrderItem;
import az.librarycrudapi.Exception.ResourceNotFoundException;
import az.librarycrudapi.Repository.BookRepository;
import az.librarycrudapi.Repository.MemberRepository;
import az.librarycrudapi.Repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;
    private final NotificationService notificationService;

    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto dto) {
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new ResourceNotFoundException("Uzv tapilmadi " + dto.getMemberId()));

        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");
        order.setMember(member);

        if (dto.getItems() != null) {
            dto.getItems().forEach(itemDto -> {
                Book book = bookRepository.findById(itemDto.getBookId())
                        .orElseThrow(() -> new ResourceNotFoundException("Kitab tapilmadi " + itemDto.getBookId()));

                OrderItem orderItem = new OrderItem();
                orderItem.setQuantity(itemDto.getQuantity());
                orderItem.setPrice(BigDecimal.valueOf(itemDto.getPrice()));
                orderItem.setBook(book);
                orderItem.setOrder(order);

                order.getOrderItems().add(orderItem);
            });
        }

        Order savedOrder = orderRepository.save(order);
        notificationService.sendOrderNotification(member.getFullName(), savedOrder.getId());
        return toResponseDto(savedOrder);
    }

    @Transactional(readOnly = true)
    public Page<OrderResponseDto> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable).map(this::toResponseDto);
    }

    @Transactional(readOnly = true)
    public Page<OrderResponseDto> searchOrders(String status, Long memberId, LocalDateTime date, Pageable pageable) {
        var spec = az.librarycrudapi.Repository.OrderSpecification.hasStatus(status)
                .and(az.librarycrudapi.Repository.OrderSpecification.hasMemberId(memberId))
                .and(az.librarycrudapi.Repository.OrderSpecification.createdAfter(date));
        return orderRepository.findAll(spec, pageable).map(this::toResponseDto);
    }

    private OrderResponseDto toResponseDto(Order order) {
        OrderResponseDto dto = new OrderResponseDto();
        dto.setId(order.getId());
        dto.setOrderDate(order.getOrderDate());
        dto.setStatus(order.getStatus());
        dto.setMemberId(order.getMember().getId());
        dto.setMemberName(order.getMember().getFullName());

        if (order.getOrderItems() != null) {
            dto.setItems(order.getOrderItems().stream().map(item -> {
                OrderItemResponseDto itemDto = new OrderItemResponseDto();
                itemDto.setId(item.getId());
                itemDto.setBookId(item.getBook().getId());
                itemDto.setBookTitle(item.getBook().getTitle());
                itemDto.setQuantity(item.getQuantity());
                itemDto.setPrice(item.getPrice().doubleValue());
                return itemDto;
            }).collect(Collectors.toList()));
        }
        return dto;
    }
}
