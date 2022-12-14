package me.wac.scjustice.service;

import me.wac.scjustice.config.WebClientConfig;
import me.wac.scjustice.model.Beer;
import me.wac.scjustice.model.BeerPagedList;
import me.wac.scjustice.model.BeerStyleEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class BeerClientImplTest {

    WebClient webClient;

    BeerClientImpl beerClient;

    @BeforeEach
    void setUp() {
        beerClient = new BeerClientImpl(new WebClientConfig().webClient());
    }

    @Test
    void getBeers() {
        Mono<BeerPagedList> testMonoResult = beerClient.getBeers(null, null,
                null, null, null);

        assertThat(testMonoResult).isNotNull();

        BeerPagedList testResult = testMonoResult.block();

        assertThat(testResult).isNotNull();
        assertThat(testResult.getContent().size()).isGreaterThan(1);
        System.out.print(testResult.getContent());
    }

    @Test
    void getBeersLimit10() {
        Mono<BeerPagedList> testMonoResult = beerClient.getBeers(1, 10,
                null, null, null);

        assertThat(testMonoResult).isNotNull();

        BeerPagedList testResult = testMonoResult.block();

        assertThat(testResult).isNotNull();
        assertThat(testResult.getContent().size()).isEqualTo(10);

    }

    @Test
    void getBeersNone() {
        Mono<BeerPagedList> testMonoResult = beerClient.getBeers(10, 20,
                null, null, null);

        assertThat(testMonoResult).isNotNull();

        BeerPagedList testResult = testMonoResult.block();

        assertThat(testResult).isNotNull();
        assertThat(testResult.getContent().size()).isEqualTo(0);

    }

    @Test
    void createBear() {
        Beer newBeer = Beer.builder()
                .beerName("Zoey Zestful Ale")
                .beerStyle(BeerStyleEnum.ALE)
                .upc("Z1O2E3Y4")
                .price(new BigDecimal("9.99"))
                .build();
        Mono<ResponseEntity<Void>> responseEntityMono = beerClient.createBear(newBeer);
        assertThat(responseEntityMono).isNotNull();
        ResponseEntity<Void> responseEntity = responseEntityMono.block();
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void getBeer() {
        Mono<Beer> testMonoResult = beerClient.getBeer(UUID.fromString("779f98b4-b8cc-40f2-8497-7018302202f5"), true);

        assertThat(testMonoResult).isNotNull();

        Beer testResult = testMonoResult.block();

        assertThat(testResult).isNotNull();
    }

    @Test
    void updateBear() {
    }

    @Test
    void deleteBeer() {
    }

    @Test
    void getBeerByUPC() {
        Mono<Beer> testMonoResult = beerClient.getBeerByUPC("Z1O2E3Y4");
        assertThat(testMonoResult).isNotNull();
        Beer testResult = testMonoResult.block();
        assertThat(testResult).isNotNull();
    }
}