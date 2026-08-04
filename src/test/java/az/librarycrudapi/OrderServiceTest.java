package az.librarycrudapi;

import az.librarycrudapi.Dto.OrderItemRequestDto;
import az.librarycrudapi.Dto.OrderRequestDto;
import az.librarycrudapi.Entity.Member;
import az.librarycrudapi.Exception.ResourceNotFoundException;
import az.librarycrudapi.Repository.BookRepository;
import az.librarycrudapi.Repository.MemberRepository;
import az.librarycrudapi.Repository.OrderRepository;
import az.librarycrudapi.Service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private BookRepository bookRepository;

    @Test
    public void testOrderCreationRollbackWhenBookNotFound() {
        Member member = new Member();
        member.setId(1L);
        member.setFullName("Test Uzv");
        member.setEmail("test.uzv@example.com");

        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        when(orderRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(bookRepository.findById(999999L)).thenReturn(Optional.empty());

        OrderItemRequestDto itemDto = new OrderItemRequestDto();
        itemDto.setBookId(999999L);
        itemDto.setQuantity(1);
        itemDto.setPrice(10.0);

        OrderRequestDto finalRequestDto = new OrderRequestDto();
        finalRequestDto.setMemberId(1L);
        finalRequestDto.setItems(List.of(itemDto));

        assertThrows(ResourceNotFoundException.class, () -> {
            orderService.createOrder(finalRequestDto);
        });

        verify(orderRepository, atLeastOnce()).save(any());
        verify(orderRepository, never()).save(argThat(order -> order.getOrderItems().size() > 0));
    }
}
