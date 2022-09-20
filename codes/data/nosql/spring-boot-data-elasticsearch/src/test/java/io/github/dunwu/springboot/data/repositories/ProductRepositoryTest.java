package io.github.dunwu.springboot.data.repositories;

import io.github.dunwu.springboot.SpringBootDataElasticsearchApplication;
import io.github.dunwu.springboot.data.entities.Product;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { SpringBootDataElasticsearchApplication.class })
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository repository;

    @Before
    public void clear() {
        repository.deleteAll();
    }

    @Test
    public void shouldReturnListOfProductsByName() {
        Product product1 = new Product("1", "test product 1",
            "How great would it be if we could search for this product.", true);
        Product product2 = new Product("2", "test Product 2",
            "How great would it be if we could search for this other product.", true);

        repository.index(product1);
        repository.index(product2);

        List<Product> products = repository.findByName("product");
        Assert.assertEquals(2, products.size());
    }

    @Test
    public void shouldReturnListOfBookByNameWithPageable() {
        Product product1 = new Product("1", "test product 1",
            "How great would it be if we could search for this product.", true);
        Product product2 = new Product("2", "test Product 2",
            "How great would it be if we could search for this other product.", true);

        repository.index(product1);
        repository.index(product2);

        List<Product> products = repository.findByName("product", PageRequest.of(0, 1));
        Assert.assertEquals(1, products.size());
    }

    @Test
    public void shouldReturnListOfProductsForGivenNameAndId() {
        Product product1 = new Product("1", "test product 1",
            "How great would it be if we could search for this product.", true);
        Product product2 = new Product("2", "test Product 2",
            "How great would it be if we could search for this other product.", true);

        repository.save(product1);
        repository.save(product2);

        List<Product> products = repository.findByNameAndId("product", "1");
        Assert.assertEquals(1, products.size());
    }

}
