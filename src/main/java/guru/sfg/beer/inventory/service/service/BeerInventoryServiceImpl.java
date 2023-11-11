package guru.sfg.beer.inventory.service.service;

import guru.sfg.beer.inventory.service.domain.BeerInventory;
import guru.sfg.beer.inventory.service.repositories.BeerInventoryRepository;
import guru.sfg.beer.inventory.service.web.mappers.BeerInventoryMapper;
import guru.sfg.beer.inventory.service.web.model.BeerInventoryDto;
import guru.sfg.beer.inventory.service.web.model.BeerInventoryPagedList;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BeerInventoryServiceImpl implements BeerInventoryService {
    private final BeerInventoryRepository beerInventoryRepository;
    private final BeerInventoryMapper beerInventoryMapper;

    @Override
    public BeerInventoryPagedList findAll(UUID beerId, Integer pageNumber, Integer pageSize) {
        Page<BeerInventory> beerInventoryPage = beerInventoryRepository.findBeerInventoriesByBeerId(
                beerId,
                PageRequest.of(pageNumber, pageSize)
        );
        List<BeerInventoryDto> content = beerInventoryPage.get()
                .map(beerInventoryMapper::beerInventoryToBeerInventoryDto).toList();

        return new BeerInventoryPagedList(content, PageRequest.of(
                beerInventoryPage.getPageable().getPageNumber(),
                beerInventoryPage.getPageable().getPageSize()
        ), beerInventoryPage.getTotalElements());
    }

    @Override
    public List<BeerInventoryDto> findAll(UUID beerId) {
        return beerInventoryRepository.findBeerInventoriesByBeerId(beerId)
                .stream()
                .map(beerInventoryMapper::beerInventoryToBeerInventoryDto)
                .toList();
    }
}
