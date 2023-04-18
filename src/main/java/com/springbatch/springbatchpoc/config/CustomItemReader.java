package com.springbatch.springbatchpoc.config;

import com.springbatch.springbatchpoc.entity.Customer;
import com.springbatch.springbatchpoc.repository.CustomerRepository;
import org.springframework.batch.item.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

/**
 * @author Nazim Uddin Asif
 * @Since 1.0.0
 */
@Component
public class CustomItemReader implements ItemReader<Customer>, ItemStream {

    private Iterator<Customer> customerIterator;

    public CustomItemReader(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    private CustomerRepository customerRepository;

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        List<Customer> customers = customerRepository.findAllLimi10K();
        customerIterator = customers.iterator();
    }

    @Override
    public Customer read() {
        if (customerIterator != null && customerIterator.hasNext()) {
            return customerIterator.next();
        } else {
            return null;
        }
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        // No need to implement this method for this use case
    }

    @Override
    public void close() throws ItemStreamException {
        // No need to implement this method for this use case
    }
}