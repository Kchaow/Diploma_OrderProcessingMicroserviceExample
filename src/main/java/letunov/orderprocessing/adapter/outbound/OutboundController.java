package letunov.orderprocessing.adapter.outbound;

import letunov.contracts.dto.OrderConfirmationDto;
import letunov.contracts.dto.OrderStatusDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/out")
@RequiredArgsConstructor
public class OutboundController {
    private final SendConfirmationNotification sendConfirmationNotification;
    private final SendOrderStatusNotification sendOrderStatusNotification;

    @GetMapping("/notifications/confirmation")
    public ResponseEntity<Void> micro8_1() {
        return sendConfirmationNotification.sendOrderConfirmationNotification(new OrderConfirmationDto("1", "1", "message"));
    }

    @GetMapping("/notifications/order-status")
    public ResponseEntity<Void> micro8_2() {
        return sendOrderStatusNotification.sendOrderStatusNotification(new OrderStatusDto("1", "1", "message", "message"));
    }
}
