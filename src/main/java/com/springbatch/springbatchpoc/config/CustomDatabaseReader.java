package com.springbatch.springbatchpoc.config;

/**
 * @author Nazim Uddin Asif
 * @Since 1.0.0
 */
import com.springbatch.springbatchpoc.entity.Customer;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomDatabaseReader implements ItemReader<Customer>, ItemStream {
    private JdbcCursorItemReader<Customer> itemReader;

    public CustomDatabaseReader(DataSource dataSource) {
        itemReader = new JdbcCursorItemReader<>();
        itemReader.setDataSource(dataSource);
        itemReader.setSql("SELECT id, first_name, last_name, email, gender, contact_no, country, dob FROM customers");
        itemReader.setRowMapper(new CustomerRowMapper());
    }

    @Override
    public Customer read() throws Exception {
        return itemReader.read();
    }

    private class CustomerRowMapper implements RowMapper<Customer> {
        @Override
        public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
            Customer customer = new Customer();
            customer.setId(rs.getInt("id"));
            customer.setFirstName(rs.getString("first_name"));
            customer.setLastName(rs.getString("last_name"));
            customer.setEmail(rs.getString("email"));
            customer.setGender(rs.getString("gender"));
            customer.setContactNo(rs.getString("contact_no"));
            customer.setCountry(rs.getString("country"));
            customer.setDob(rs.getString("dob"));
            return customer;
        }
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        itemReader.open(executionContext);
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        itemReader.update(executionContext);
    }

    @Override
    public void close() throws ItemStreamException {
        itemReader.close();
    }
}
