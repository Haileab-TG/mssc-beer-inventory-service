package guru.sfg.beer.inventory.service.service;

import common.model.BeerOrderDto;
import common.model.BeerOrderLineDto;
import guru.sfg.beer.inventory.service.repositories.BeerInventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
@Component
public class AllocationServiceImpl implements AllocationService {
    private final BeerInventoryRepository beerInventoryRepository;

    @Override
    public Boolean allocateOrder(BeerOrderDto beerOrderDto) {
        AtomicInteger totalOrder = new AtomicInteger(0);
        AtomicInteger totalAllocatedOrder = new AtomicInteger(0);

        List<BeerOrderLineDto> orderLines = beerOrderDto.getBeerOrderLines();
        orderLines.forEach(orderLine -> {
            int orderQty = orderLine.getOrderQuantity() == null ? 0 : orderLine.getOrderQuantity();
            int allocatedQty = orderLine.getQuantityAllocated() == null ? 0 : orderLine.getQuantityAllocated();
            if(orderQty > allocatedQty){
                allocateOrderLine(orderLine, orderQty, allocatedQty);
                totalOrder.set(totalOrder.get() + orderQty);
                totalAllocatedOrder.set(
                        totalAllocatedOrder.get() + orderLine.getQuantityAllocated() //getting it from obj cuz it may be paritial allocation
                );
            }
        });

        return totalOrder.get() == totalAllocatedOrder.get();
    }

    private void allocateOrderLine(BeerOrderLineDto orderLine, Integer orderQty, Integer allocatedQty) {
        AtomicInteger allocationNeeded = new AtomicInteger(orderQty - allocatedQty);
        beerInventoryRepository.findBeerInventoriesByUpc(orderLine.getUpc())
                .forEach(beerInventory -> {
                    if(allocationNeeded.get() > 0){
                        int qtyOnHand = beerInventory.getQuantityOnHand() == null ? 0 : beerInventory.getQuantityOnHand();
                        if(qtyOnHand > 0 && qtyOnHand < allocationNeeded.get()) {// take it all
                            allocationNeeded.set(allocationNeeded.get() - beerInventory.getQuantityOnHand());

                            beerInventoryRepository.delete(beerInventory); //cuz inventory is zero now (all allocated)
                        }else if(qtyOnHand > 0 && qtyOnHand > allocationNeeded.get()){
                            int allocated = qtyOnHand - allocationNeeded.get();
                            beerInventory.setQuantityOnHand(qtyOnHand - allocated);
                            beerInventoryRepository.save(beerInventory);
                            allocationNeeded.set(0);
                        }
                    }
                });
        int allocatedFromAllInventory = (orderQty - allocatedQty) - allocationNeeded.get();
        orderLine.setQuantityAllocated(allocatedFromAllInventory);
    }
}
