package letunov.orderprocessing.adapter.outbound;

import letunov.contract.ContractConsumer;
import letunov.contracts.SendOrderStatusNotificationContract;
import letunov.contracts.dto.OrderStatusDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@ContractConsumer(serviceName = "notifications")
public class SendOrderStatusNotification implements SendOrderStatusNotificationContract {
    @Value("${integration.notifications-url}")
    private String notificationsUrl;

    public ResponseEntity<Void> sendOrderStatusNotification(OrderStatusDto dto) {
        return WebClient.create(notificationsUrl)
                .post()
                .bodyValue(dto)
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}
