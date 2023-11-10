package common.event;

import common.model.BeerOrderDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidateOrderRequestEvent {
    private BeerOrderDto beerOrderDto;
}
