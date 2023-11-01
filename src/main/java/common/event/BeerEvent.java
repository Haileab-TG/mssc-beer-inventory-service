package common.event;

import guru.sfg.beer.inventory.service.messageConsumers.model.BeerDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BeerEvent{
    private BeerDTO beerDTO;
}
