package io.github.dunwu.springboot.data.elasticsearch.service;

import io.github.dunwu.springboot.data.elasticsearch.model.Customer;

import java.util.List;

public interface CustomersInterface {

    List<Customer> searchCity(Integer pageNumber, Integer pageSize, String searchContent);

}
