package guru.sfg.beer.inventory.service.service;

import guru.sfg.beer.inventory.service.web.model.BeerInventoryDto;
import guru.sfg.beer.inventory.service.web.model.BeerInventoryPagedList;

import java.util.List;
import java.util.UUID;

public interface BeerInventoryService {
    BeerInventoryPagedList findAll(UUID beerId, Integer pageNumber, Integer pageSize);

    List<BeerInventoryDto> findAll(UUID beerId);
}
