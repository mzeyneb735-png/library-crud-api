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
import az.librarycrudapi.Repository.OrderSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;

    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto dto) {
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new ResourceNotFoundException("Uzv tapilmadi " + dto.getMemberId()));

        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");
        order.setMember(member);

        Order savedOrder = orderRepository.save(order);

        dto.getItems().forEach(itemDto -> {
            Book book = bookRepository.findById(itemDto.getBookId())
                    .orElseThrow(() -> new ResourceNotFoundException("Kitab tapilmadi " + itemDto.getBookId()));

            OrderItem orderItem = new OrderItem();
            orderItem.setQuantity(itemDto.getQuantity());
            orderItem.setPrice(itemDto.getPrice());
            orderItem.setBook(book);
            orderItem.setOrder(savedOrder);

            savedOrder.getOrderItems().add(orderItem);
        });

        Order fullySavedOrder = orderRepository.save(savedOrder);
        return toResponseDto(fullySavedOrder);
    }

    @Transactional(readOnly = true)
    public Page<OrderResponseDto> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable)
                .map(this::toResponseDto);
    }

    @Transactional(readOnly = true)
    public Page<OrderResponseDto> searchOrders(String status, Long memberId, LocalDateTime date, Pageable pageable) {
        Specification<Order> spec = Specification.where(OrderSpecification.hasStatus(status))
                .and(OrderSpecification.hasMemberId(memberId))
                .and(OrderSpecification.createdAfter(date));

        return orderRepository.findAll(spec, pageable)
                .map(this::toResponseDto);
    }

    private OrderResponseDto toResponseDto(Order order) {
        OrderResponseDto dto = new OrderResponseDto();
        dto.setId(order.getId());
        dto.setOrderDate(order.getOrderDate());
        dto.setStatus(order.getStatus());
        dto.setMemberId(order.getMember().getId());
        dto.setMemberName(order.getMember().getFullName());

        dto.setItems(order.getOrderItems().stream().map(item -> {
            OrderItemResponseDto itemDto = new OrderItemResponseDto();
            itemDto.setId(item.getId());
            itemDto.setBookId(item.getBook().getId());
            itemDto.setBookTitle(item.getBook().getTitle());
            itemDto.setQuantity(item.getQuantity());
            itemDto.setPrice(item.getPrice());
            return itemDto;
        }).collect(Collectors.toList()));

        return dto;
    }
}
