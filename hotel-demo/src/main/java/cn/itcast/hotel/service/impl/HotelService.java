package cn.itcast.hotel.service.impl;

import cn.itcast.hotel.mapper.HotelMapper;
import cn.itcast.hotel.pojo.Hotel;
import cn.itcast.hotel.pojo.HotelDoc;
import cn.itcast.hotel.pojo.PageResult;
import cn.itcast.hotel.pojo.RequestParams;
import cn.itcast.hotel.service.IHotelService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HotelService extends ServiceImpl<HotelMapper, Hotel> implements IHotelService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Override
    public PageResult search(RequestParams requestParams) {

        SearchRequest request = new SearchRequest("hotel");

        buildBasicQuery(requestParams, request);
        //发请求
        SearchResponse response = null;
        try {
            response = restHighLevelClient.search(request,RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PageResult pageResult = new PageResult();
        SearchHits searchHits = response.getHits();
        long total = searchHits.getTotalHits().value;
        pageResult.setTotal(total);
        SearchHit[] hits = searchHits.getHits();
        ArrayList<HotelDoc> list = new ArrayList<>();
        for (SearchHit hit : hits) {
            String json = hit.getSourceAsString();
            HotelDoc hotelDoc = JSON.parseObject(json, HotelDoc.class);
            Object[] sortValues = hit.getSortValues();
            if(sortValues!=null && sortValues.length>0){
                hotelDoc.setDistance(sortValues[0]);
            }
            list.add(hotelDoc);
        }
        pageResult.setHotels(list);
        return pageResult;
    }

    private void buildBasicQuery(RequestParams requestParams, SearchRequest request) {
        //多条件过滤查询
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //关键字查询
        String key = requestParams.getKey();
        if(key!=null && !"".equals(key)){
                //key不为null,根据key查询
                boolQueryBuilder.must(QueryBuilders.matchQuery("all",key));
            }else{
                //key为null，查询所有
                boolQueryBuilder.must(QueryBuilders.matchAllQuery());
            }

        //city->term
        String city =  requestParams.getCity();
        if(city!=null && !"".equals(city)){
            boolQueryBuilder.filter(QueryBuilders.termQuery("city",city));
        }

        //brand->term
        String brand = requestParams.getBrand();
        if(brand!=null && !"".equals(brand)){
            boolQueryBuilder.filter(QueryBuilders.termQuery("brand",brand));
        }

        //starName->term
        String starName = requestParams.getStarName();
        if(starName!=null && !"".equals(starName)){
            boolQueryBuilder.filter(QueryBuilders.termQuery("starName",starName));
        }
        //price->range
        Integer maxPrice = requestParams.getMaxPrice();
        Integer minPrice = requestParams.getMinPrice();
        if(maxPrice!=null && minPrice!=null){
            boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").gte(minPrice).lte(maxPrice));
        }
        //分页
        Integer page = requestParams.getPage();
        Integer size = requestParams.getSize();
        request.source().from((page-1)*size).size(size);
        //距离排序
        String location = requestParams.getLocation();
        if(location!=null && !"".equals(location)){
            request.source().sort(SortBuilders.geoDistanceSort("location",new GeoPoint())
                    .order(SortOrder.ASC)
                    .unit(DistanceUnit.KILOMETERS));
        }
        //算分控制
        request.source().query(QueryBuilders.functionScoreQuery(
                boolQueryBuilder,
                new FunctionScoreQueryBuilder.FilterFunctionBuilder[]{
                        new FunctionScoreQueryBuilder.FilterFunctionBuilder(
                                QueryBuilders.termQuery("isAD",true),
                                ScoreFunctionBuilders.weightFactorFunction(10)
                        )
                }
        ));
    }

    @Override
    public Map<String, List<String>> filters(RequestParams params) {
        SearchRequest request = buildQuery();
        buildBasicQuery(params,request);
        SearchResponse response = null;
        try {
            response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<String> brandAgg = getAggByName(response, "brandAgg");
        List<String> cityAgg = getAggByName(response, "cityAgg");
        List<String> starAgg = getAggByName(response, "starAgg");
        HashMap<String, List<String>> result = new HashMap<>();
        result.put("brand",brandAgg);
        result.put("city",cityAgg);
        result.put("starName",starAgg);
        return result;
    }

    @Override
    public List<String> getSuggestions(String key) {
        SearchRequest request = new SearchRequest("hotel");
        request.source().suggest(new SuggestBuilder().addSuggestion(
                "my_suggestion",
                SuggestBuilders.completionSuggestion("suggestion")
                        .prefix(key)
                        .skipDuplicates(true)
                        .size(10)
        ));
        //发请求
        SearchResponse response = null;
        try {
            response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //解析响应结果
        Suggest suggest = response.getSuggest();
        CompletionSuggestion suggestion =  suggest.getSuggestion("my_suggestion");
        List<CompletionSuggestion.Entry.Option> options = suggestion.getOptions();
        ArrayList<String> list = new ArrayList<>();
        for (CompletionSuggestion.Entry.Option option : options) {
            list.add(option.getText().string());
        }
        return list;
    }

    //构建DSL
    private SearchRequest buildQuery(){
        SearchRequest request = new SearchRequest("hotel");
        request.source().size(0);
        request.source().aggregation(AggregationBuilders
        .terms("brandAgg")
                .field("brand")
                .size(100)
        );
        request.source().aggregation(AggregationBuilders
                .terms("cityAgg")
                .field("city")
                .size(100)
        );
        request.source().aggregation(AggregationBuilders
                .terms("starAgg")
                .field("starName")
                .size(100)
        );
        return request;
    }

    //解析查询结果
    private List<String> getAggByName(SearchResponse response,String aggName){
        ArrayList<String> list = new ArrayList<>();
        Terms terms = response.getAggregations().get(aggName);
        List<? extends Terms.Bucket> buckets = terms.getBuckets();
        for (Terms.Bucket bucket : buckets) {
            list.add(bucket.getKeyAsString());
        }
        return list;
    }
}
