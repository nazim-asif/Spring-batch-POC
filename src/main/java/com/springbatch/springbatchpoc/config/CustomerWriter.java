package com.springbatch.springbatchpoc.config;

import com.springbatch.springbatchpoc.entity.Customer;
import com.springbatch.springbatchpoc.entity.Customer_bkp;
import com.springbatch.springbatchpoc.repository.CustomerBkpRepo;
import com.springbatch.springbatchpoc.repository.CustomerRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Nazim Uddin Asif
 * @Since 1.0.0
 */
@Component
public class CustomerWriter implements ItemWriter<Customer_bkp> {

    @Autowired
    private CustomerBkpRepo customerRepository;

    @Override
    public void write(List<? extends Customer_bkp> list) throws Exception {
        System.out.println("Thread Name : -"+Thread.currentThread().getName());
        customerRepository.saveAll(list);
    }

}
