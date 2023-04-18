package com.springbatch.springbatchpoc.config;

import com.springbatch.springbatchpoc.entity.Customer_bkp;
import com.springbatch.springbatchpoc.repository.CustomerBkpRepo;
import com.springbatch.springbatchpoc.repository.WritterCustomRepo;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Nazim Uddin Asif
 * @Since 1.0.0
 */
@Component
public class DynamicCustomerWriter implements ItemWriter<Object> {

    @Autowired
    private WritterCustomRepo customerRepository;

    @Override
    public void write(List<?> list) throws Exception {
        System.out.println("Thread Name : -"+Thread.currentThread().getName());
        customerRepository.findEntitiesByCriteria(list);
    }
}
