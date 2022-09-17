package io.github.dunwu.springboot.data.repositories;

import cn.hutool.core.util.IdUtil;
import io.github.dunwu.springboot.data.entities.Book;
import io.github.dunwu.springboot.SpringBootDataElasticsearchApplication;
import io.github.dunwu.tool.util.RandomUtil;
import org.apache.lucene.search.join.ScoreMode;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Resource;

import static java.util.Arrays.asList;
import static org.elasticsearch.index.query.QueryBuilders.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { SpringBootDataElasticsearchApplication.class })
public class BookRepositoryTest {

    @Resource
    private BookRepository repository;

    @Resource
    private ElasticsearchTemplate template;

    @Before
    public void clear() {
        repository.deleteAll();
    }

    @Test
    public void shouldIndexSingleBookEntity() {

        Book book = new Book();
        book.setId("123455");
        book.setName("Spring Data Elasticsearch");
        book.setVersion(System.currentTimeMillis());
        repository.save(book);
        //lets try to search same record in elasticsearch
        Optional<Book> optional = repository.findById(book.getId());
        Book indexedBook = optional.orElse(null);
        Assertions.assertThat(indexedBook).isNotNull();
        Assertions.assertThat(indexedBook.getId()).isEqualTo(book.getId());
    }

    @Test
    public void shouldBulkIndexMultipleBookEntities() {

        Book book1 = new Book(RandomUtil.randomString(5, 10), "Spring Data", System.currentTimeMillis());
        Book book2 = new Book(RandomUtil.randomString(5, 10), "Elasticsearch", System.currentTimeMillis());
        //Bulk Index using repository
        repository.saveAll(asList(book1, book2));
        //lets try to search same records in elasticsearch
        Book indexedBook1 = repository.findById(book1.getId()).orElse(null);
        Assertions.assertThat(indexedBook1.getId()).isEqualTo(book1.getId());
        Book indexedBook2 = repository.findById(book2.getId()).orElse(null);
        Assertions.assertThat(indexedBook2.getId()).isEqualTo(book2.getId());
    }

    @Test
    @Ignore
    public void crudRepositoryTest() {
        Book book1 = new Book(IdUtil.randomUUID(), "Spring Data", System.currentTimeMillis());
        Book book2 = new Book(IdUtil.randomUUID(), "Elasticsearch", System.currentTimeMillis());
        Book book3 = new Book(IdUtil.randomUUID(), "Spring Data Elasticsearch", System.currentTimeMillis());
        List<Book> books = Arrays.asList(book1, book2);

        //indexing single document
        repository.save(book3);
        //bulk indexing multiple documents
        repository.saveAll(books);
        //searching single document based on documentId
        Book book = repository.findById(book1.getId()).orElse(null);
        Assertions.assertThat(book).isNotNull();
        //to get all records as iteratable collection
        Iterable<Book> bookList = repository.findAll();
        Assertions.assertThat(bookList).hasSize(3);
        //page request which will give first 10 document
        Page<Book> bookPage = repository.findAll(PageRequest.of(0, 1));
        Assertions.assertThat(bookPage.getContent()).hasSize(1);
        // to get all records as ASC on name field
        Iterable<Book> bookIterable = repository.findAll(Sort.by("name").ascending());
        Assertions.assertThat(bookIterable.iterator().next().getName()).isEqualTo("Elasticsearch");
        //to get total number of docoments in an index
        Long count = repository.count();
        Assertions.assertThat(count).isEqualTo(3);
        //to check wheather document exists or not
        boolean exists = repository.existsById(book1.getId());
        Assertions.assertThat(exists).isTrue();
        //delete a document by entity
        repository.delete(book1);
        //delete multiple document using collection
        repository.deleteAll(books);
        //delete a document using documentId
        repository.deleteById(book1.getId());
        //delete all document
        repository.deleteAll();
    }

    @Test
    public void shouldCountAllElementsInIndex() {

        List<Book> books = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            books.add(new Book(RandomUtil.randomString(5, 10), "Spring Data Rocks !", System.currentTimeMillis()));
        }
        //Bulk Index using repository
        repository.saveAll(books);
        //count all elements
        long count = repository.count();
        Assertions.assertThat(count).isEqualTo(10);
    }

    @Test
    public void shouldExecuteCustomSearchQueries() {

        Book book1 = new Book(RandomUtil.randomString(5, 10), "Custom Query", System.currentTimeMillis());
        Book book2 = new Book(RandomUtil.randomString(5, 10), null, System.currentTimeMillis());
        //indexing a book
        repository.saveAll(Arrays.asList(book1, book2));

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
            .withQuery(matchAllQuery())
            .withFilter(existsQuery("name"))
            .withPageable(PageRequest.of(0, 10))
            .build();

        Page<Book> books = repository.search(searchQuery);
        Assertions.assertThat(books.getNumberOfElements()).isEqualTo(1);
    }

    @Test
    public void shouldReturnBooksForCustomMethodsWithAndCriteria() {
        Book book1 = new Book(RandomUtil.randomString(5, 10), "test", System.currentTimeMillis());
        Book book2 = new Book(RandomUtil.randomString(5, 10), "test", System.currentTimeMillis());
        book1.setPrice(10L);
        book2.setPrice(10L);
        repository.saveAll(Arrays.asList(book1, book2));

        Page<Book> books = repository.findByNameAndPrice("test", 10, PageRequest.of(0, 10));
        Assertions.assertThat(books.getContent()).hasSize(2);
    }

    @Test
    public void shouldReturnBooksWithName() {
        Book book1 = new Book(RandomUtil.randomString(5, 10), "test1", System.currentTimeMillis());
        Book book2 = new Book(RandomUtil.randomString(5, 10), "test2", System.currentTimeMillis());
        repository.saveAll(Arrays.asList(book1, book2));

        Page<Book> books = repository.findByName("test1", PageRequest.of(0, 10));
        Assertions.assertThat(books.getContent()).hasSize(1);
    }

    @Test
    public void shouldReturnBooksForGivenBucket() {
        Book book1 = new Book(RandomUtil.randomString(5, 10), "test1", System.currentTimeMillis());
        Book book2 = new Book(RandomUtil.randomString(5, 10), "test2", System.currentTimeMillis());

        Map<Integer, Collection<String>> map1 = new HashMap<>();
        map1.put(1, Arrays.asList("test1", "test2"));

        Map<Integer, Collection<String>> map2 = new HashMap<>();
        map2.put(1, Arrays.asList("test3", "test4"));

        book1.setBuckets(map1);
        book2.setBuckets(map2);

        repository.saveAll(Arrays.asList(book1, book2));

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
            .withQuery(nestedQuery("buckets", termQuery("buckets.1", "test3"), ScoreMode.Total))
            .build();

        Page<Book> books = repository.search(searchQuery);
        Assertions.assertThat(books.getContent()).hasSize(1);
    }

    @Test
    public void shouldReturnBooksForGivenBucketUsingTemplate() {

        template.deleteIndex(Book.class);
        template.createIndex(Book.class);
        template.putMapping(Book.class);
        template.refresh(Book.class);

        Book book1 = new Book(RandomUtil.randomString(5, 10), "test1", System.currentTimeMillis());
        Book book2 = new Book(RandomUtil.randomString(5, 10), "test2", System.currentTimeMillis());

        Map<Integer, Collection<String>> map1 = new HashMap<>();
        map1.put(1, Arrays.asList("test1", "test2"));

        Map<Integer, Collection<String>> map2 = new HashMap<>();
        map2.put(1, Arrays.asList("test3", "test4"));

        book1.setBuckets(map1);
        book2.setBuckets(map2);

        repository.saveAll(Arrays.asList(book1, book2));

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
            .withQuery(nestedQuery("buckets", termQuery("buckets.1", "test3"), ScoreMode.Total))
            .build();

        Page<Book> books = repository.search(searchQuery);

        Assertions.assertThat(books.getContent()).hasSize(1);
    }

    @Test
    public void shouldReturnBooksForCustomMethodsWithOrCriteria() {
        Book book1 = new Book(RandomUtil.randomString(5, 10), "test Or", System.currentTimeMillis());
        Book book2 = new Book(RandomUtil.randomString(5, 10), "test And", System.currentTimeMillis());
        book1.setPrice(10L);
        book2.setPrice(10L);
        repository.saveAll(Arrays.asList(book1, book2));

        Page<Book> books = repository.findByNameOrPrice("message", 10, PageRequest.of(0, 10));
        Assertions.assertThat(books.getContent()).hasSize(2);
    }

    @Test
    public void shouldGiveIterableOfBooks() {
        Book book1 = new Book(RandomUtil.randomString(5, 10), "test Or", System.currentTimeMillis());
        Book book2 = new Book(RandomUtil.randomString(5, 10), "test And", System.currentTimeMillis());
        book1.setPrice(10L);
        book2.setPrice(10L);
        repository.saveAll(Arrays.asList(book1, book2));

        Iterable<Book> books = repository.search(matchAllQuery());
        Assertions.assertThat(books).isNotEmpty();
    }

}
