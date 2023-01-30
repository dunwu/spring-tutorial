package example.spring.data.nosql.elasticsearch.service;

import example.spring.data.nosql.elasticsearch.model.Customer;

import java.util.List;

public interface CustomersInterface {

    List<Customer> searchCity(Integer pageNumber, Integer pageSize, String searchContent);

}
