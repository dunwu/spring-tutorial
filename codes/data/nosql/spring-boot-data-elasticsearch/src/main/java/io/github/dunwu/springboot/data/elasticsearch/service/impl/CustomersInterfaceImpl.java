package io.github.dunwu.springboot.data.elasticsearch.service.impl;

import io.github.dunwu.springboot.data.elasticsearch.model.Customer;
import io.github.dunwu.springboot.data.elasticsearch.repository.CustomerRepository;
import io.github.dunwu.springboot.data.elasticsearch.service.CustomersInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class CustomersInterfaceImpl implements CustomersInterface {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Customer> searchCity(Integer pageNumber, Integer pageSize, String searchContent) {
        return null;
    }

}
