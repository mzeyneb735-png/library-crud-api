package az.librarycrudapi.Service;

import az.librarycrudapi.Repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduledTaskService {

    private final OrderRepository orderRepository;

    @Scheduled(cron = "0 0 0 * * ?")
    public void performMidnightCleanup() {
        orderRepository.count();
    }
}
