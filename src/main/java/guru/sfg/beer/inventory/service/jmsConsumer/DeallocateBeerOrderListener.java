package guru.sfg.beer.inventory.service.jmsConsumer;

import common.event.DeallocateCancelledOrderReqEvent;
import guru.sfg.beer.inventory.service.config.JmsConfig;
import guru.sfg.beer.inventory.service.service.AllocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeallocateBeerOrderListener {
    private final AllocationService allocationService;
    @JmsListener(destination =JmsConfig.CANCELLED_ORDER_DEALLOCATE_REQ_QUEUE)
    public void listener(DeallocateCancelledOrderReqEvent event){
        allocationService.deallocateOrder(event.getBeerOrderDto());
    }
}
