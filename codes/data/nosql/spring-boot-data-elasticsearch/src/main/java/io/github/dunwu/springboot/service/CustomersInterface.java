package io.github.dunwu.springboot.service;

import io.github.dunwu.springboot.model.Customer;

import java.util.List;

public interface CustomersInterface {

    List<Customer> searchCity(Integer pageNumber, Integer pageSize, String searchContent);

}
