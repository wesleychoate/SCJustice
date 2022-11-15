package me.wac.scjustice.service;

import me.wac.scjustice.model.Beer;
import me.wac.scjustice.model.BeerPagedList;
import me.wac.scjustice.model.BeerStyleEnum;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface BeerClient {
    Mono<BeerPagedList> getBeers(Integer pageNumber, Integer pageSize,
                                 String beerName, BeerStyleEnum beerStyle, Boolean showInventoryOnHand);

    Mono<ResponseEntity> createBear(Beer beer);

    Mono<Beer> getBeer(UUID beerId, Boolean showInventoryOnHand);

    Mono<ResponseEntity> updateBear(Beer beer);

    Mono<ResponseEntity> deleteBeer(UUID beerId);

    Mono<Beer> getBeerByUPC(String beerUpc);
}
