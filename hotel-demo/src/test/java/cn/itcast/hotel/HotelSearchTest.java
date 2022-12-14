package cn.itcast.hotel;

import cn.itcast.hotel.pojo.HotelDoc;
import cn.itcast.hotel.service.IHotelService;
import com.alibaba.fastjson.JSON;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class HotelSearchTest {
    private RestHighLevelClient client;

    @Autowired
    private IHotelService service;

    @BeforeEach
    public void setup(){
        this.client = new RestHighLevelClient(RestClient.builder(
                HttpHost.create("192.168.154.129:9200")
        )

        );
    }

    @AfterEach
    public void free() throws IOException {
        this.client.close();
    }

    @Test
    public void testMatchAll() throws IOException {
        SearchRequest request = new SearchRequest("hotel");
        request.source().query(QueryBuilders.matchAllQuery());
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        //??????response
        handleResponse(response);
    }

    @Test
    public void testMatch() throws IOException {
        SearchRequest request = new SearchRequest("hotel");
        request.source().query(QueryBuilders.matchQuery("all","??????"));
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        //??????response
        handleResponse(response);
    }

    @Test
    public void testMultiMatch() throws IOException {
        SearchRequest request = new SearchRequest("hotel");
        request.source().query(QueryBuilders.multiMatchQuery("??????","name","brand"));
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        //??????response
        handleResponse(response);
    }
    @Test
    public void testBooleanQuery() throws IOException {
        SearchRequest request = new SearchRequest("hotel");
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.termQuery("city","??????"));
        boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").lte(250));
        request.source().query(boolQueryBuilder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        //??????response
        handleResponse(response);
    }
    @Test
    public void testPageAndSort() throws IOException {
        int page=1,size = 5;
        SearchRequest request = new SearchRequest("hotel");
        request.source().query(QueryBuilders.matchAllQuery());
        //??????
        request.source().from((page-1)*size).size(size);
        //??????
        request.source().sort("price", SortOrder.ASC);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        //??????response
        handleResponse(response);
    }
    @Test
    public void testHighLight() throws IOException {
        SearchRequest request = new SearchRequest("hotel");
        request.source().query(QueryBuilders.matchQuery("all","??????"));
        request.source().highlighter(new HighlightBuilder().field("name").requireFieldMatch(false));
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        //??????response
        handleHighLightResponse(response);
    }
    private void handleResponse(SearchResponse response) {
        SearchHits searchHits = response.getHits();
        long total = searchHits.getTotalHits().value;
        System.out.println("????????????"+total+"?????????");
        SearchHit[] hits = searchHits.getHits();
        for (SearchHit hit : hits) {
            String json = hit.getSourceAsString();
            HotelDoc hotelDoc = JSON.parseObject(json, HotelDoc.class);
            System.out.println("=============================");
            System.out.println(hotelDoc);
            System.out.println("==============================");
        }
    }
    //??????????????????????????????
    private void handleHighLightResponse(SearchResponse response) {
        SearchHits searchHits = response.getHits();
        long total = searchHits.getTotalHits().value;
        System.out.println("????????????"+total+"?????????");
        SearchHit[] hits = searchHits.getHits();
        for (SearchHit hit : hits) {
            String json = hit.getSourceAsString();
            HotelDoc hotelDoc = JSON.parseObject(json, HotelDoc.class);
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            HighlightField highlightField = highlightFields.get("name");
            String name = highlightField.fragments()[0].string();
            hotelDoc.setName(name);
            System.out.println("=============================");
            System.out.println(hotelDoc);
            System.out.println("==============================");
        }
    }
    //bucket????????????
    @Test
    public void testAggregation() throws IOException {
        SearchRequest request = new SearchRequest("hotel");
        request.source().size(0);
        request.source().aggregation(AggregationBuilders
                .terms("brandAggs")
                .field("brand")
                .size(20)
        );
        SearchResponse response = client.search(request,RequestOptions.DEFAULT);
        //??????????????????
        Aggregations aggregations = response.getAggregations();
        Terms brandTerms = aggregations.get("brandAggs");
        List<? extends Terms.Bucket> buckets = brandTerms.getBuckets();
        for (Terms.Bucket bucket : buckets) {
            System.out.println(bucket.getKeyAsString());
        }

    }

    @Test
    public void testSuggest() throws IOException {
        SearchRequest request = new SearchRequest("hotel");
        request.source().suggest(new SuggestBuilder().addSuggestion(
                "my_suggestion",
                SuggestBuilders.completionSuggestion("suggestion")
                .prefix("hz")
                .skipDuplicates(true)
                .size(10)
        ));
        //?????????
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        //??????????????????
        Suggest suggest = response.getSuggest();
        CompletionSuggestion suggestion =  suggest.getSuggestion("my_suggestion");
        List<CompletionSuggestion.Entry.Option> options = suggestion.getOptions();
        for (CompletionSuggestion.Entry.Option option : options) {
            System.out.println(option.getText().string());
        }
    }

    @Test
    public void testFilters(){
//        System.out.println(service.filters(params));
    }
}
