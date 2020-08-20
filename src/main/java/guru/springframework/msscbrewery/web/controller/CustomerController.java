package guru.springframework.msscbrewery.web.controller;

import guru.springframework.msscbrewery.service.CustomerService;
import guru.springframework.msscbrewery.web.model.CustomerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.UUID;
@Slf4j
@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable("id") UUID customerId){
        return new ResponseEntity<>(customerService.getCustomerById(customerId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity saveCustomer(@Valid @RequestBody  CustomerDto customerDto){
        HttpHeaders headers = new HttpHeaders();
        CustomerDto savedDto = customerService.saveCustomer(customerDto);
        log.info("saved customer details "+savedDto.getCustomerId());
        headers.add("Location","http://localhost:8080/api/v1/customer/"+savedDto.getCustomerId());
        return new ResponseEntity<>(headers,HttpStatus.OK);
    }
    @PutMapping("/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateCustomer(@PathVariable UUID customerId,@Valid @RequestBody CustomerDto customerDto){
        customerService.updateCustomer(customerId,customerDto);
    }
    @DeleteMapping("/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCustomer(@PathVariable UUID customerId){
        log.info("deleting the customer "+customerId);
        customerService.deleteCustomer(customerId);
    }


}
