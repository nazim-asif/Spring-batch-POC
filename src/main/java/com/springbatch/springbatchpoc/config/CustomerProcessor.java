package com.springbatch.springbatchpoc.config;


import com.springbatch.springbatchpoc.entity.Customer;
import com.springbatch.springbatchpoc.entity.Customer_bkp;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class CustomerProcessor implements ItemProcessor<Object, Object> {

//    @Override
//    public Customer_bkp process(Customer customer) throws Exception {
//        Customer_bkp bkp = new Customer_bkp();
//        BeanUtils.copyProperties(customer, bkp);
//        return bkp;
////        if(customer.getCountry().equals("United States")) {
////            return customer;
////        }else{
////            return null;
////        }
//    }
    @Override
    public Object process(Object obj) throws Exception {
        return obj;
    }
}
