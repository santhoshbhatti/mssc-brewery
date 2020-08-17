package guru.springframework.msscbrewery.service.impl;

import guru.springframework.msscbrewery.service.CustomerService;
import guru.springframework.msscbrewery.web.model.CustomerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    @Override
    public CustomerDto getCustomerById(UUID customerId) {
        return CustomerDto.builder().customerId(customerId).name("John Cena").build();
    }

    @Override
    public CustomerDto saveCustomer(CustomerDto customerDto) {
        customerDto.setCustomerId(UUID.randomUUID());
        log.info("saving customer new id generated is "+customerDto.getCustomerId());
        return customerDto;
    }

    @Override
    public void updateCustomer(UUID customerId, CustomerDto customerDto) {
        log.info("updating customer with id "+customerId);
    }

    @Override
    public void deleteCustomer(UUID customerId) {
        log.info("deleting customer with id "+customerId);
    }
}
