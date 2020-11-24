package io.github.dunwu.spring.data.repositories;

import io.github.dunwu.spring.data.entities.Article;
import io.github.dunwu.spring.data.entities.ArticleBuilder;
import io.github.dunwu.springboot.SpringBootDataElasticsearchApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.FacetedPage;
import org.springframework.data.elasticsearch.core.facet.request.RangeFacetRequestBuilder;
import org.springframework.data.elasticsearch.core.facet.request.TermFacetRequestBuilder;
import org.springframework.data.elasticsearch.core.facet.result.Range;
import org.springframework.data.elasticsearch.core.facet.result.RangeResult;
import org.springframework.data.elasticsearch.core.facet.result.Term;
import org.springframework.data.elasticsearch.core.facet.result.TermResult;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.junit4.SpringRunner;

import static org.elasticsearch.index.query.QueryBuilders.*;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { SpringBootDataElasticsearchApplication.class })
public class ElasticsearchFacetTests {

    public static final String RIZWAN_IDREES = "Rizwan Idrees";

    public static final String MOHSIN_HUSEN = "Mohsin Husen";

    public static final String JONATHAN_YAN = "Jonathan Yan";

    public static final String ARTUR_KONCZAK = "Artur Konczak";

    public static final int YEAR_2002 = 2002;

    public static final int YEAR_2001 = 2001;

    public static final int YEAR_2000 = 2000;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Before
    public void before() {
        elasticsearchTemplate.deleteIndex(Article.class);
        elasticsearchTemplate.createIndex(Article.class);
        elasticsearchTemplate.putMapping(Article.class);
        elasticsearchTemplate.refresh(Article.class);

        IndexQuery article1 = new ArticleBuilder("1").title("article four")
            .addAuthor(RIZWAN_IDREES)
            .addAuthor(ARTUR_KONCZAK)
            .addAuthor(MOHSIN_HUSEN)
            .addAuthor(JONATHAN_YAN)
            .score(10)
            .buildIndex();
        IndexQuery article2 = new ArticleBuilder("2").title("article three")
            .addAuthor(RIZWAN_IDREES)
            .addAuthor(ARTUR_KONCZAK)
            .addAuthor(MOHSIN_HUSEN)
            .addPublishedYear(YEAR_2000)
            .score(20)
            .buildIndex();
        IndexQuery article3 = new ArticleBuilder("3").title("article two")
            .addAuthor(RIZWAN_IDREES)
            .addAuthor(ARTUR_KONCZAK)
            .addPublishedYear(YEAR_2001)
            .addPublishedYear(YEAR_2000)
            .score(30)
            .buildIndex();
        IndexQuery article4 = new ArticleBuilder("4").title("article one")
            .addAuthor(RIZWAN_IDREES)
            .addPublishedYear(YEAR_2002)
            .addPublishedYear(YEAR_2001)
            .addPublishedYear(YEAR_2000)
            .score(40)
            .buildIndex();

        elasticsearchTemplate.index(article1);
        elasticsearchTemplate.index(article2);
        elasticsearchTemplate.index(article3);
        elasticsearchTemplate.index(article4);
        elasticsearchTemplate.refresh(Article.class);
    }

    @Test
    public void shouldReturnFacetedAuthorsForGivenQueryWithDefaultOrder() {

        String facetName = "fauthors";
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchAllQuery())
            .withFacet(new TermFacetRequestBuilder(facetName).fields("authors.untouched").build())
            .build();
        // when
        FacetedPage<Article> result = elasticsearchTemplate.queryForPage(searchQuery, Article.class);
        // then
        assertThat(result.getNumberOfElements(), is(equalTo(4)));

        TermResult facet = (TermResult) result.getFacet(facetName);
        Term term = facet.getTerms().get(0);
        assertThat(term.getTerm(), is(RIZWAN_IDREES));
        assertThat(term.getCount(), is(4));

        term = facet.getTerms().get(1);
        assertThat(term.getTerm(), is(ARTUR_KONCZAK));
        assertThat(term.getCount(), is(3));

        term = facet.getTerms().get(2);
        assertThat(term.getTerm(), is(MOHSIN_HUSEN));
        assertThat(term.getCount(), is(2));

        term = facet.getTerms().get(3);
        assertThat(term.getTerm(), is(JONATHAN_YAN));
        assertThat(term.getCount(), is(1));
    }

    @Test
    public void shouldReturnFacetedAuthorsForGivenFilteredQuery() {
        // given
        String facetName = "fauthors";
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchAllQuery())
            .withFilter(boolQuery().must(termQuery("title", "four")))
            .withFacet(new TermFacetRequestBuilder(facetName).applyQueryFilter().fields("authors.untouched").build())
            .build();
        // when
        FacetedPage<Article> result = elasticsearchTemplate.queryForPage(searchQuery, Article.class);
        // then
        assertThat(result.getNumberOfElements(), is(equalTo(3)));

        TermResult facet = (TermResult) result.getFacet(facetName);
        Term term = facet.getTerms().get(0);
        assertThat(term.getTerm(), is(RIZWAN_IDREES));
        assertThat(term.getCount(), is(3));

        term = facet.getTerms().get(1);
        assertThat(term.getTerm(), is(ARTUR_KONCZAK));
        assertThat(term.getCount(), is(2));

        term = facet.getTerms().get(2);
        assertThat(term.getTerm(), is(MOHSIN_HUSEN));
        assertThat(term.getCount(), is(1));
    }

    @Test
    public void shouldExcludeTermsFromFacetedAuthorsForGivenQuery() {
        // given
        String facetName = "fauthors";
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchAllQuery())
            .withFilter(boolQuery().must(termQuery("title", "four")))
            .withFacet(new TermFacetRequestBuilder(facetName).applyQueryFilter()
                .fields("authors.untouched")
                .excludeTerms(RIZWAN_IDREES, ARTUR_KONCZAK)
                .build()).build();
        // when
        FacetedPage<Article> result = elasticsearchTemplate.queryForPage(searchQuery, Article.class);
        // then
        assertThat(result.getNumberOfElements(), is(equalTo(3)));

        TermResult facet = (TermResult) result.getFacet(facetName);

        assertThat(facet.getTerms().size(), is(1));

        Term term = facet.getTerms().get(0);
        assertThat(term.getTerm(), is(MOHSIN_HUSEN));
        assertThat(term.getCount(), is(1));
    }

    @Test
    public void shouldReturnFacetedAuthorsForGivenQueryOrderedByTerm() {

        // given
        String facetName = "fauthors";
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchAllQuery())
            .withFacet(new TermFacetRequestBuilder(facetName).fields("authors.untouched").ascTerm().build()).build();
        // when
        FacetedPage<Article> result = elasticsearchTemplate.queryForPage(searchQuery, Article.class);
        // then
        assertThat(result.getNumberOfElements(), is(equalTo(4)));

        TermResult facet = (TermResult) result.getFacet(facetName);
        Term term = facet.getTerms().get(0);
        assertThat(term.getTerm(), is(ARTUR_KONCZAK));
        assertThat(term.getCount(), is(3));

        term = facet.getTerms().get(1);
        assertThat(term.getTerm(), is(JONATHAN_YAN));
        assertThat(term.getCount(), is(1));

        term = facet.getTerms().get(2);
        assertThat(term.getTerm(), is(MOHSIN_HUSEN));
        assertThat(term.getCount(), is(2));

        term = facet.getTerms().get(3);
        assertThat(term.getTerm(), is(RIZWAN_IDREES));
        assertThat(term.getCount(), is(4));
    }

    @Test
    public void shouldReturnFacetedAuthorsForGivenQueryOrderedByCountAsc() {

        // given
        String facetName = "fauthors";
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchAllQuery())
            .withFacet(new TermFacetRequestBuilder(facetName).fields("authors.untouched").ascCount().build()).build();
        // when
        FacetedPage<Article> result = elasticsearchTemplate.queryForPage(searchQuery, Article.class);
        // then
        assertThat(result.getNumberOfElements(), is(equalTo(4)));

        TermResult facet = (TermResult) result.getFacet(facetName);
        Term term = facet.getTerms().get(0);
        assertThat(term.getTerm(), is(JONATHAN_YAN));
        assertThat(term.getCount(), is(1));

        term = facet.getTerms().get(1);
        assertThat(term.getTerm(), is(MOHSIN_HUSEN));
        assertThat(term.getCount(), is(2));

        term = facet.getTerms().get(2);
        assertThat(term.getTerm(), is(ARTUR_KONCZAK));
        assertThat(term.getCount(), is(3));

        term = facet.getTerms().get(3);
        assertThat(term.getTerm(), is(RIZWAN_IDREES));
        assertThat(term.getCount(), is(4));
    }

    @Test
    public void shouldReturnFacetedYearsForGivenQuery() {

        // given
        String facetName = "fyears";
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchAllQuery())
            .withFacet(new TermFacetRequestBuilder(facetName).fields("publishedYears").descCount().build()).build();
        // when
        FacetedPage<Article> result = elasticsearchTemplate.queryForPage(searchQuery, Article.class);
        // then
        assertThat(result.getNumberOfElements(), is(equalTo(4)));

        TermResult facet = (TermResult) result.getFacet(facetName);
        assertThat(facet.getTerms().size(), is(equalTo(3)));

        Term term = facet.getTerms().get(0);
        assertThat(term.getTerm(), is(Integer.toString(YEAR_2000)));
        assertThat(term.getCount(), is(3));

        term = facet.getTerms().get(1);
        assertThat(term.getTerm(), is(Integer.toString(YEAR_2001)));
        assertThat(term.getCount(), is(2));

        term = facet.getTerms().get(2);
        assertThat(term.getTerm(), is(Integer.toString(YEAR_2002)));
        assertThat(term.getCount(), is(1));
    }

    @Test
    public void shouldReturnSingleFacetOverYearsAndAuthorsForGivenQuery() {

        // given
        String facetName = "fyears";
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchAllQuery())
            .withFacet(
                new TermFacetRequestBuilder(facetName).fields("publishedYears", "authors.untouched").ascTerm().build())
            .build();
        // when
        FacetedPage<Article> result = elasticsearchTemplate.queryForPage(searchQuery, Article.class);
        // then
        assertThat(result.getNumberOfElements(), is(equalTo(4)));

        TermResult facet = (TermResult) result.getFacet(facetName);
        assertThat(facet.getTerms().size(), is(equalTo(7)));

        Term term = facet.getTerms().get(0);
        assertThat(term.getTerm(), is(Integer.toString(YEAR_2000)));
        assertThat(term.getCount(), is(3));

        term = facet.getTerms().get(1);
        assertThat(term.getTerm(), is(Integer.toString(YEAR_2001)));
        assertThat(term.getCount(), is(2));

        term = facet.getTerms().get(2);
        assertThat(term.getTerm(), is(Integer.toString(YEAR_2002)));
        assertThat(term.getCount(), is(1));

        term = facet.getTerms().get(3);
        assertThat(term.getTerm(), is(ARTUR_KONCZAK));
        assertThat(term.getCount(), is(3));

        term = facet.getTerms().get(4);
        assertThat(term.getTerm(), is(JONATHAN_YAN));
        assertThat(term.getCount(), is(1));

        term = facet.getTerms().get(5);
        assertThat(term.getTerm(), is(MOHSIN_HUSEN));
        assertThat(term.getCount(), is(2));

        term = facet.getTerms().get(6);
        assertThat(term.getTerm(), is(RIZWAN_IDREES));
        assertThat(term.getCount(), is(4));
    }

    @Test
    public void shouldReturnFacetedYearsAndFacetedAuthorsForGivenQuery() {

        // given
        String numberFacetName = "fAuthors";
        String stringFacetName = "fyears";
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchAllQuery())
            .withFacet(new TermFacetRequestBuilder(numberFacetName).fields("publishedYears").ascTerm().build())
            .withFacet(new TermFacetRequestBuilder(stringFacetName).fields("authors.untouched").ascTerm().build())
            .build();
        // when
        FacetedPage<Article> result = elasticsearchTemplate.queryForPage(searchQuery, Article.class);
        // then
        assertThat(result.getNumberOfElements(), is(equalTo(4)));

        TermResult numberFacet = (TermResult) result.getFacet(numberFacetName);
        assertThat(numberFacet.getTerms().size(), is(equalTo(3)));

        Term numberTerm = numberFacet.getTerms().get(0);
        assertThat(numberTerm.getTerm(), is(Integer.toString(YEAR_2000)));
        assertThat(numberTerm.getCount(), is(3));

        numberTerm = numberFacet.getTerms().get(1);
        assertThat(numberTerm.getTerm(), is(Integer.toString(YEAR_2001)));
        assertThat(numberTerm.getCount(), is(2));

        numberTerm = numberFacet.getTerms().get(2);
        assertThat(numberTerm.getTerm(), is(Integer.toString(YEAR_2002)));
        assertThat(numberTerm.getCount(), is(1));

        TermResult stringFacet = (TermResult) result.getFacet(stringFacetName);
        Term stringTerm = stringFacet.getTerms().get(0);
        assertThat(stringTerm.getTerm(), is(ARTUR_KONCZAK));
        assertThat(stringTerm.getCount(), is(3));

        stringTerm = stringFacet.getTerms().get(1);
        assertThat(stringTerm.getTerm(), is(JONATHAN_YAN));
        assertThat(stringTerm.getCount(), is(1));

        stringTerm = stringFacet.getTerms().get(2);
        assertThat(stringTerm.getTerm(), is(MOHSIN_HUSEN));
        assertThat(stringTerm.getCount(), is(2));

        stringTerm = stringFacet.getTerms().get(3);
        assertThat(stringTerm.getTerm(), is(RIZWAN_IDREES));
        assertThat(stringTerm.getCount(), is(4));
    }

    @Test
    public void shouldReturnFacetedYearsForNativeFacet() {

        // given
        String facetName = "fyears";
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchAllQuery()).build();
        // when
        FacetedPage<Article> result = elasticsearchTemplate.queryForPage(searchQuery, Article.class);
        // then
        assertThat(result.getNumberOfElements(), is(equalTo(4)));

        TermResult facet = (TermResult) result.getFacet(facetName);
        assertThat(facet.getTerms().size(), is(equalTo(3)));

        Term term = facet.getTerms().get(0);
        assertThat(term.getTerm(), is(Integer.toString(YEAR_2000)));
        assertThat(term.getCount(), is(3));

        term = facet.getTerms().get(1);
        assertThat(term.getTerm(), is(Integer.toString(YEAR_2001)));
        assertThat(term.getCount(), is(2));

        term = facet.getTerms().get(2);
        assertThat(term.getTerm(), is(Integer.toString(YEAR_2002)));
        assertThat(term.getCount(), is(1));
    }

    @Test
    public void shouldFilterResultByRegexForGivenQuery() {
        // given
        String facetName = "regex_authors";
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchAllQuery())
            .withFilter(boolQuery().must(termQuery("title", "four")))
            .withFacet(new TermFacetRequestBuilder(facetName).applyQueryFilter()
                .fields("authors.untouched")
                .regex("Art.*")
                .build()).build();
        // when
        FacetedPage<Article> result = elasticsearchTemplate.queryForPage(searchQuery, Article.class);
        // then
        assertThat(result.getNumberOfElements(), is(equalTo(3)));

        TermResult facet = (TermResult) result.getFacet(facetName);

        assertThat(facet.getTerms().size(), is(1));

        Term term = facet.getTerms().get(0);
        assertThat(term.getTerm(), is(ARTUR_KONCZAK));
        assertThat(term.getCount(), is(2));
    }

    @Test
    public void shouldReturnAllTermsForGivenQuery() {
        // given
        String facetName = "all_authors";
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchAllQuery())
            .withFilter(boolQuery().must(termQuery("title", "four")))
            .withFacet(new TermFacetRequestBuilder(facetName).applyQueryFilter()
                .fields("authors.untouched")
                .allTerms()
                .build()).build();
        // when
        FacetedPage<Article> result = elasticsearchTemplate.queryForPage(searchQuery, Article.class);
        // then
        assertThat(result.getNumberOfElements(), is(equalTo(3)));

        TermResult facet = (TermResult) result.getFacet(facetName);

        assertThat(facet.getTerms().size(), is(4));
    }

    @Test
    public void shouldReturnRangeFacetForGivenQuery() {
        // given
        String facetName = "rangeYears";
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchAllQuery())
            .withFacet(
                new RangeFacetRequestBuilder(facetName).field("publishedYears")
                    .to(YEAR_2000).range(YEAR_2000, YEAR_2002).from(YEAR_2002).build()
            ).build();
        // when
        FacetedPage<Article> result = elasticsearchTemplate.queryForPage(searchQuery, Article.class);
        // then
        assertThat(result.getNumberOfElements(), is(equalTo(4)));

        RangeResult facet = (RangeResult) result.getFacet(facetName);
        assertThat(facet.getRanges().size(), is(equalTo(3)));

        Range range = facet.getRanges().get(0);
        assertThat(range.getFrom(), nullValue());
        assertThat(range.getTo(), is((double) YEAR_2000));
        assertThat(range.getCount(), is(0L));
        assertThat(range.getTotal(), is(0.0));

        range = facet.getRanges().get(1);
        assertThat(range.getFrom(), is((double) YEAR_2000));
        assertThat(range.getTo(), is((double) YEAR_2002));
        assertThat(range.getCount(), is(3L));
        assertThat(range.getTotal(), is(6000.0));

        range = facet.getRanges().get(2);
        assertThat(range.getFrom(), is((double) YEAR_2002));
        assertThat(range.getTo(), nullValue());
        assertThat(range.getCount(), is(1L));
        assertThat(range.getTotal(), is(2002.0));
    }

    @Test
    public void shouldReturnKeyValueRangeFacetForGivenQuery() {
        // given
        String facetName = "rangeScoreOverYears";
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchAllQuery())
            .withFacet(
                new RangeFacetRequestBuilder(facetName).fields("publishedYears", "score")
                    .to(YEAR_2000).range(YEAR_2000, YEAR_2002).from(YEAR_2002).build()
            ).build();
        // when
        FacetedPage<Article> result = elasticsearchTemplate.queryForPage(searchQuery, Article.class);
        // then
        assertThat(result.getNumberOfElements(), is(equalTo(4)));

        RangeResult facet = (RangeResult) result.getFacet(facetName);
        assertThat(facet.getRanges().size(), is(equalTo(3)));

        Range range = facet.getRanges().get(0);
        assertThat(range.getFrom(), nullValue());
        assertThat(range.getTo(), is((double) YEAR_2000));
        assertThat(range.getCount(), is(0L));
        assertThat(range.getTotal(), is(0.0));

        range = facet.getRanges().get(1);
        assertThat(range.getFrom(), is((double) YEAR_2000));
        assertThat(range.getTo(), is((double) YEAR_2002));
        assertThat(range.getCount(), is(3L));
        assertThat(range.getTotal(), is(90.0));

        range = facet.getRanges().get(2);
        assertThat(range.getFrom(), is((double) YEAR_2002));
        assertThat(range.getTo(), nullValue());
        assertThat(range.getCount(), is(1L));
        assertThat(range.getTotal(), is(40.0));
    }

}

