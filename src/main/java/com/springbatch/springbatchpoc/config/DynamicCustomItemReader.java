package com.springbatch.springbatchpoc.config;

import com.springbatch.springbatchpoc.entity.Customer;
import com.springbatch.springbatchpoc.repository.CustomerCustomRepo;
import com.springbatch.springbatchpoc.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Nazim Uddin Asif
 * @Since 1.0.0
 */
@Component
@RequiredArgsConstructor
public class DynamicCustomItemReader implements ItemReader<Customer>, ItemStream {

//    private Iterator<Customer> customerIterator;
    private Iterator<Map<String, Object>> customerIterator;

    public DynamicCustomItemReader(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    public DynamicCustomItemReader(CustomerRepository customerRepository, CustomerCustomRepo customerCustomRepo) {
        this.customerRepository = customerRepository;
        this.customerCustomRepo = customerCustomRepo;
    }

    private CustomerRepository customerRepository;
    private CustomerCustomRepo customerCustomRepo;

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
//        List<Customer> customers = customerRepository.findAllLimi10K();
        List<Map<String, Object>> customers = customerCustomRepo.findEntitiesByCriteria();
        customerIterator = customers.iterator();
    }

    @Override
    public Customer read() {
        if (customerIterator != null && customerIterator.hasNext()) {
            return (Customer) customerIterator.next();
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