package az.librarycrudapi;

import az.librarycrudapi.Dto.OrderRequestDto;
import az.librarycrudapi.Dto.OrderItemRequestDto;
import az.librarycrudapi.Entity.Member;
import az.librarycrudapi.Repository.MemberRepository;
import az.librarycrudapi.Repository.OrderRepository;
import az.librarycrudapi.Service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LibraryCrudApiApplicationTests {

    @Autowired
    private OrderService orderService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void testOrderCreationRollbackOnException() {
        Member member = new Member();
        member.setFullName("Test Istifadeci");
        member.setEmail("test.rollback@library.com");
        Member savedMember = memberRepository.save(member);

        long initialOrderCount = orderRepository.count();

        OrderRequestDto requestDto = new OrderRequestDto();
        requestDto.setMemberId(savedMember.getId());

        OrderItemRequestDto itemDto = new OrderItemRequestDto();
        itemDto.setBookId(9999L);
        itemDto.setQuantity(2);
        itemDto.setPrice(15.5);
        requestDto.setItems(List.of(itemDto));

        assertThrows(RuntimeException.class, () -> {
            orderService.createOrder(requestDto);
        });

        long finalOrderCount = orderRepository.count();
        assertEquals(initialOrderCount, finalOrderCount);
    }
}
