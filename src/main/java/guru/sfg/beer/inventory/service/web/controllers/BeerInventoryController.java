package guru.sfg.beer.inventory.service.web.controllers;

import guru.sfg.beer.inventory.service.service.BeerInventoryService;
import guru.sfg.beer.inventory.service.web.model.BeerInventoryDto;
import guru.sfg.beer.inventory.service.web.model.BeerInventoryPagedList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Created by jt on 2019-05-31.
 */
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/beer/{beerId}")
@RestController
public class BeerInventoryController {

    private final BeerInventoryService beerInventoryService;

    public static final Integer DEFAULT_PAGE_SIZE = 25;
    public static final Integer DEFAULT_PAGE_NUMBER = 0;

    @GetMapping("/inventory")
    List<BeerInventoryDto> listBeersById(@PathVariable UUID beerId){
        return beerInventoryService.findAll(beerId);
    }

    @GetMapping("/pagedInventory")
    BeerInventoryPagedList listBeersByIdPaged(
            @PathVariable UUID beerId,
            @RequestParam(name = "pageNumber", required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", required = false) Integer pageSize
    ){
        pageNumber = pageNumber == null ? DEFAULT_PAGE_NUMBER : pageNumber;
        pageSize = pageSize == null || pageSize > 25 ? DEFAULT_PAGE_SIZE : pageSize;

        return beerInventoryService.findAll(beerId, pageNumber, pageSize);
    }
}
