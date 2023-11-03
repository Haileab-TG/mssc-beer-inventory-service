package guru.sfg.beer.inventory.service.messageConsumers;

import common.event.ValidateBeerOrderRequest;
import common.event.ValidateOrderResultEvent;
import common.model.BeerOrderLineDto;
import guru.sfg.beer.inventory.service.config.JmsConfig;
import guru.sfg.beer.inventory.service.repositories.BeerInventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class ValidateBeerOrderListener {
    private final JmsTemplate jmsClient;
    private final BeerInventoryRepository beerInventoryRepository;

    @JmsListener(destination = JmsConfig.VALIDATE_ORDER_REQUEST_QUEUE)
    public void validateBeerOrderListener(ValidateBeerOrderRequest event){
        boolean isValid = validateOrderLineByUPC(event.getBeerOrderDto().getBeerOrderLines());
        sendJmsMsg(
                event.getBeerOrderDto().getId(),
                isValid
        );
    }

    private void sendJmsMsg(UUID orderId, boolean isValid) {
        jmsClient.convertAndSend(
                JmsConfig.VALIDATE_ORDER_RESULT_QUEUE,
                ValidateOrderResultEvent.builder()
                        .orderId(orderId)
                        .isValid(isValid)
                        .build()
        );
    }

    private boolean validateOrderLineByUPC(List<BeerOrderLineDto> beerOrderLines) {
       for(BeerOrderLineDto orderLineDto: beerOrderLines){
           if(beerInventoryRepository
                   .countBeerInventoriesByUpc(orderLineDto.getUpc()) == 0
           ) return false;
       }
       return true;
    }
}
