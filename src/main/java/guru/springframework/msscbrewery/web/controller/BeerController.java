package guru.springframework.msscbrewery.web.controller;

import guru.springframework.msscbrewery.service.BeerService;
import guru.springframework.msscbrewery.web.model.BeerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/beer")
public class BeerController {
    @Autowired
    BeerService beerService;
    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDto> getBeer(@PathVariable("beerId") UUID beerId){
        return new ResponseEntity<BeerDto>(beerService.getBeer(beerId), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<BeerDto> saveNewBeer(@Valid @RequestBody BeerDto beerDto){
        BeerDto savedBeerDto=beerService.saveNewBeer(beerDto);
        HttpHeaders headers = new HttpHeaders();
        System.out.println("build test with spring developer!!!!");
        //TODO how the host and port dynamically here in below header definition ----to be done
        headers.add("Location","http://localhost:8080/api/v1/beer/"+savedBeerDto.getId().toString());
        return new ResponseEntity<>(headers,HttpStatus.CREATED);
    }
    @PutMapping("/{beerId}")
    public ResponseEntity updateBeer(@PathVariable UUID beerId,@Valid @RequestBody BeerDto beerDto){
        beerService.updateBeer(beerId,beerDto);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping("/{beerId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBeer(@PathVariable UUID beerId){
        beerService.deleteBeer(beerId);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<String>> validationErrorHandler(ConstraintViolationException e){
        List<String> errorMessages=e.getConstraintViolations().stream().map(cv -> cv.getPropertyPath() +" : "+ cv.getMessage()).collect(Collectors.toList());
        return new ResponseEntity<>(errorMessages,HttpStatus.BAD_REQUEST);
    }

}
