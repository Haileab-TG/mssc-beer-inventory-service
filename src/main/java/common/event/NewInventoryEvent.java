package common.event;

import common.model.BeerDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Data
public class NewInventoryEvent  {
    private BeerDTO beerDTO;
}
