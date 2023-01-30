package example.spring.data.nosql.elasticsearch.service.impl;

import example.spring.data.nosql.elasticsearch.model.Customer;
import example.spring.data.nosql.elasticsearch.repository.CustomerRepository;
import example.spring.data.nosql.elasticsearch.service.CustomersInterface;
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
