package me.wac.scjustice.service;

import lombok.RequiredArgsConstructor;
import me.wac.scjustice.config.WebClientProperties;
import me.wac.scjustice.model.Beer;
import me.wac.scjustice.model.BeerPagedList;
import me.wac.scjustice.model.BeerStyleEnum;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BeerClientImpl implements BeerClient {

    private final WebClient webClient;

    @Override
    public Mono<BeerPagedList> getBeers(Integer pageNumber, Integer pageSize, String beerName, BeerStyleEnum beerStyle, Boolean showInventoryOnHand) {

        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path(WebClientProperties.BEER_V1_PATH)
                        .queryParamIfPresent("pageNumber", Optional.ofNullable(pageNumber))
                        .queryParamIfPresent("pageSize", Optional.ofNullable(pageSize))
                        .queryParamIfPresent("beerName", Optional.ofNullable(beerName))
                        .queryParamIfPresent("beerStyle", Optional.ofNullable(beerStyle))
                        .queryParamIfPresent("showInventoryOnhand", Optional.ofNullable(showInventoryOnHand))
                        .build()
                )
                .retrieve()
                .bodyToMono(BeerPagedList.class);
    }

    @Override
    public Mono<ResponseEntity<Void>> createBear(Beer beer) {

        return webClient.post()
                .uri(uriBuilder -> uriBuilder.path(WebClientProperties.BEER_V1_PATH).build())
                .body(BodyInserters.fromValue(beer))
                .retrieve()
                .toBodilessEntity();
    }

    @Override
    public Mono<Beer> getBeer(UUID beerId, Boolean showInventoryOnHand) {
        String fullPath = WebClientProperties.BEER_V1_PATH.concat("/".concat(beerId.toString()));
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path(fullPath)
                        .queryParamIfPresent("showInventoryOnhand", Optional.ofNullable(showInventoryOnHand))
                        .build()
                )
                .retrieve()
                .bodyToMono(Beer.class);
    }

    @Override
    public Mono<ResponseEntity> updateBear(Beer beer) {
        return null;
    }

    @Override
    public Mono<ResponseEntity> deleteBeer(UUID beerId) {
        return null;
    }

    @Override
    public Mono<Beer> getBeerByUPC(String beerUpc) {
        String fullPath = String.format(WebClientProperties.BEER_UPC_PATH, beerUpc);
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path(fullPath)
                        .build()
                )
                .retrieve()
                .bodyToMono(Beer.class);
    }
}
