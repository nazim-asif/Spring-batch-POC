package com.springbatch.springbatchpoc.repository;

import com.springbatch.springbatchpoc.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository  extends JpaRepository<Customer,Integer> {
    @Query(value = "Select * from CUSTOMERS_INFO limit 10000",nativeQuery = true )
    List<Customer> findAllLimi10K();
//    List<Customer>findFirst10000();
}
