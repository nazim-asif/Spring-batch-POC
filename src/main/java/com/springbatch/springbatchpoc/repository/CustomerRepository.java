package com.springbatch.springbatchpoc.repository;

import com.springbatch.springbatchpoc.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository  extends JpaRepository<Customer,Integer> {
}
