package guru.sfg.beer.inventory.service.jmsConsumer;

import common.event.AllocateOrderRequestEvent;
import common.event.AllocateOrderResultEvent;
import guru.sfg.beer.inventory.service.config.JmsConfig;
import guru.sfg.beer.inventory.service.service.AllocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AllocateBeerOrderListener {
    private final AllocationService allocationService;
    private final JmsTemplate jmsClient;

    @JmsListener(destination = JmsConfig.ALLOCATE_ORDER_REQUEST_QUEUE)
    public void listener(AllocateOrderRequestEvent event){
        Boolean isAllAllocated = false;
        Boolean allocationError = false;

        try{
           isAllAllocated  = allocationService.allocateOrder(event.getBeerOrderDto());
        }catch (Exception e){
            allocationError = true;
        }

        AllocateOrderResultEvent resultEvent = AllocateOrderResultEvent.builder()
                .beerOrderDto(event.getBeerOrderDto())
                .pendingInventory(!isAllAllocated)
                .allocationError(allocationError)
                .build();
        jmsClient.convertAndSend(JmsConfig.ALLOCATE_ORDER_RESPONSE_QUEUE, resultEvent);
    }
}
