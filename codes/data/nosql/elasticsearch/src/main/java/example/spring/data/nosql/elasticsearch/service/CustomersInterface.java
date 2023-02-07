package example.spring.data.nosql.elasticsearch.service;

import example.spring.data.nosql.elasticsearch.model.Person;

import java.util.List;

public interface CustomersInterface {

    List<Person> searchCity(Integer pageNumber, Integer pageSize, String searchContent);

}
