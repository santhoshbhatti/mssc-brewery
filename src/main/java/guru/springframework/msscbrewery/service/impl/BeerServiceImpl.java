package guru.springframework.msscbrewery.service.impl;

import guru.springframework.msscbrewery.service.BeerService;
import guru.springframework.msscbrewery.web.model.BeerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
@Slf4j
public class BeerServiceImpl implements BeerService {
    @Override
    public BeerDto getBeer(UUID beerId) {
        return BeerDto.builder().id(beerId).
                upc(10000L).beerName("bud").beerStyle("draught").build();
    }

    @Override
    public BeerDto saveNewBeer(BeerDto beerDto) {
        beerDto.setId(UUID.randomUUID());
        return beerDto;

    }

    @Override
    public void updateBeer(UUID beerId, BeerDto beerDto) {
        System.out.println("Beer updated!!!!!!!"+beerId);
    }

    @Override
    public void deleteBeer(UUID beerId) {
        log.info("deleteing the beer with id"+beerId);
    }
}
