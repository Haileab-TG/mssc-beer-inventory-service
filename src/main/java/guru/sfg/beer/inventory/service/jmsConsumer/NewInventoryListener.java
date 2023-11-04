package guru.sfg.beer.inventory.service.jmsConsumer;

import guru.sfg.beer.inventory.service.config.JmsConfig;
import guru.sfg.beer.inventory.service.domain.BeerInventory;
import common.event.NewInventoryEvent;
import guru.sfg.beer.inventory.service.repositories.BeerInventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NewInventoryListener {
    private final BeerInventoryRepository beerInventoryRepository;

    @JmsListener(destination = JmsConfig.NEW_INVENTORY_QUEUE)
    public void newInventoryListener(NewInventoryEvent newInventoryEvent){
        BeerInventory beerInventory = BeerInventory.builder()
                .beerId(newInventoryEvent.getBeerDTO().getId())
                .upc(newInventoryEvent.getBeerDTO().getUpc())
                .quantityOnHand(newInventoryEvent.getBeerDTO().getQuantityOnHand())
                .build();
        beerInventoryRepository.save(beerInventory);
        System.out.println("new-inventory message consumed and new inventory persisted");
    }
}
