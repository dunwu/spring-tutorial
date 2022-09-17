package io.github.dunwu.springboot.data.repositories;

import io.github.dunwu.springboot.data.entities.Author;
import io.github.dunwu.springboot.data.entities.Book;
import io.github.dunwu.springboot.data.entities.Car;
import io.github.dunwu.springboot.data.entities.GirlFriend;
import io.github.dunwu.springboot.data.entities.Person;
import io.github.dunwu.springboot.data.entities.PersonMultipleLevelNested;
import io.github.dunwu.springboot.SpringBootDataElasticsearchApplication;
import io.github.dunwu.tool.util.RandomUtil;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.GetQuery;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.*;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { SpringBootDataElasticsearchApplication.class })
public class NestedObjectTests {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private BookRepository bookRepository;

    @Before
    public void before() {
        elasticsearchTemplate.deleteIndex(Book.class);
        elasticsearchTemplate.createIndex(Book.class);
        elasticsearchTemplate.refresh(Book.class);
        elasticsearchTemplate.deleteIndex(Person.class);
        elasticsearchTemplate.createIndex(Person.class);
        elasticsearchTemplate.putMapping(Person.class);
        elasticsearchTemplate.refresh(Person.class);
        elasticsearchTemplate.deleteIndex(PersonMultipleLevelNested.class);
        elasticsearchTemplate.createIndex(PersonMultipleLevelNested.class);
        elasticsearchTemplate.putMapping(PersonMultipleLevelNested.class);
        elasticsearchTemplate.refresh(PersonMultipleLevelNested.class);
    }

    @Test
    public void shouldIndexInnerObject() {
        // given
        String id = RandomUtil.randomString(5, 10);
        Book book = new Book();
        book.setId(id);
        book.setName("xyz");
        book.setVersion(System.nanoTime());
        Author author = new Author();
        author.setId("1");
        author.setName("ABC");
        // when
        bookRepository.save(book);
        // then
        assertThat(bookRepository.findById(id).orElse(null), is(notNullValue()));
    }

    @Test
    public void shouldIndexInitialLevelNestedObject() {

        List<Car> cars = new ArrayList<Car>();

        Car saturn = new Car();
        saturn.setName("Saturn");
        saturn.setModel("SL");

        Car subaru = new Car();
        subaru.setName("Subaru");
        subaru.setModel("Imprezza");

        Car ford = new Car();
        ford.setName("Ford");
        ford.setModel("Focus");

        cars.add(saturn);
        cars.add(subaru);
        cars.add(ford);

        Person foo = new Person();
        foo.setName("Foo");
        foo.setId("1");
        foo.setCar(cars);

        Car car = new Car();
        car.setName("Saturn");
        car.setModel("Imprezza");

        Person bar = new Person();
        bar.setId("2");
        bar.setName("Bar");
        bar.setCar(Arrays.asList(car));

        List<IndexQuery> indexQueries = new ArrayList<IndexQuery>();
        IndexQuery indexQuery1 = new IndexQuery();
        indexQuery1.setId(foo.getId());
        indexQuery1.setObject(foo);

        IndexQuery indexQuery2 = new IndexQuery();
        indexQuery2.setId(bar.getId());
        indexQuery2.setObject(bar);

        indexQueries.add(indexQuery1);
        indexQueries.add(indexQuery2);

        elasticsearchTemplate.putMapping(Person.class);
        elasticsearchTemplate.bulkIndex(indexQueries);
        elasticsearchTemplate.refresh(Person.class);

        QueryBuilder builder = nestedQuery("car",
            boolQuery().must(termQuery("car.name", "saturn")).must(termQuery("car.model", "imprezza")),
            ScoreMode.Total);

        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(builder).build();
        List<Person> persons = elasticsearchTemplate.queryForList(searchQuery, Person.class);

        assertThat(persons.size(), is(1));
    }

    @Test
    public void shouldIndexMultipleLevelNestedObject() {
        //given
        List<IndexQuery> indexQueries = createPerson();

        //when
        elasticsearchTemplate.putMapping(PersonMultipleLevelNested.class);
        elasticsearchTemplate.bulkIndex(indexQueries);
        elasticsearchTemplate.refresh(PersonMultipleLevelNested.class);

        //then
        GetQuery getQuery = new GetQuery();
        getQuery.setId("1");
        PersonMultipleLevelNested personIndexed =
            elasticsearchTemplate.queryForObject(getQuery, PersonMultipleLevelNested.class);
        assertThat(personIndexed, is(notNullValue()));
    }

    private List<IndexQuery> createPerson() {

        PersonMultipleLevelNested person1 = new PersonMultipleLevelNested();

        person1.setId("1");
        person1.setName("name");

        Car saturn = new Car();
        saturn.setName("Saturn");
        saturn.setModel("SL");

        Car subaru = new Car();
        subaru.setName("Subaru");
        subaru.setModel("Imprezza");

        Car car = new Car();
        car.setName("Saturn");
        car.setModel("Imprezza");

        Car ford = new Car();
        ford.setName("Ford");
        ford.setModel("Focus");

        GirlFriend permanent = new GirlFriend();
        permanent.setName("permanent");
        permanent.setType("permanent");
        permanent.setCars(Arrays.asList(saturn, subaru));

        GirlFriend temp = new GirlFriend();
        temp.setName("temp");
        temp.setType("temp");
        temp.setCars(Arrays.asList(car, ford));

        person1.setGirlFriends(Arrays.asList(permanent, temp));

        IndexQuery indexQuery1 = new IndexQuery();
        indexQuery1.setId(person1.getId());
        indexQuery1.setObject(person1);

        PersonMultipleLevelNested person2 = new PersonMultipleLevelNested();

        person2.setId("2");
        person2.setName("name");

        person2.setGirlFriends(Arrays.asList(permanent));

        IndexQuery indexQuery2 = new IndexQuery();
        indexQuery2.setId(person2.getId());
        indexQuery2.setObject(person2);

        List<IndexQuery> indexQueries = new ArrayList<IndexQuery>();
        indexQueries.add(indexQuery1);
        indexQueries.add(indexQuery2);

        return indexQueries;
    }

    @Test
    public void shouldSearchUsingNestedQueryOnMultipleLevelNestedObject() {
        //given
        List<IndexQuery> indexQueries = createPerson();

        //when
        elasticsearchTemplate.putMapping(PersonMultipleLevelNested.class);
        elasticsearchTemplate.bulkIndex(indexQueries);
        elasticsearchTemplate.refresh(PersonMultipleLevelNested.class);

        //then
        BoolQueryBuilder builder = boolQuery();
        builder.must(nestedQuery("girlFriends", termQuery("girlFriends.type", "temp"), ScoreMode.Total))
            .must(nestedQuery("girlFriends.cars", termQuery("girlFriends.cars.name", "Ford".toLowerCase()),
                ScoreMode.Total));

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
            .withQuery(builder)
            .build();

        Page<PersonMultipleLevelNested> personIndexed =
            elasticsearchTemplate.queryForPage(searchQuery, PersonMultipleLevelNested.class);
        assertThat(personIndexed, is(notNullValue()));
        assertThat(personIndexed.getTotalElements(), is(1L));
        assertThat(personIndexed.getContent().get(0).getId(), is("1"));
    }

}
