package com.springbatch.springbatchpoc.repository;

import com.springbatch.springbatchpoc.entity.Customer_bkp;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Nazim Uddin Asif
 * @Since 1.0.0
 */
public interface CustomerBkpRepo extends JpaRepository<Customer_bkp, Integer> {
}
