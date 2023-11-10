package common.event;

import common.model.BeerOrderDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AllocateOrderResultEvent {
    private boolean pendingInventory;
    private boolean allocationError;
    private BeerOrderDto beerOrderDto;
}
