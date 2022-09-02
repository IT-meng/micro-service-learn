package cn.itcast.hotel;

import cn.itcast.hotel.pojo.Hotel;
import cn.itcast.hotel.pojo.HotelDoc;
import cn.itcast.hotel.service.IHotelService;
import com.alibaba.fastjson.JSON;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

@SpringBootTest
public class HotelDocumentTest {
    private RestHighLevelClient client;

    @Autowired
    private IHotelService hotelService;
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
    public void testAddDocument() throws IOException {
        Hotel hotel = hotelService.getById(36934L);
        HotelDoc hotelDoc = new HotelDoc(hotel);
        IndexRequest request = new IndexRequest("hotel").id(hotel.getId().toString());
        request.source(JSON.toJSONString(hotelDoc), XContentType.JSON);
        client.index(request, RequestOptions.DEFAULT);
    }

    @Test
    public void testGetDocument() throws IOException {

        GetRequest request = new GetRequest("hotel", "36934");
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        //解析Response
        String hotelDocJson = response.getSourceAsString();
        HotelDoc hotelDoc = JSON.parseObject(hotelDocJson, HotelDoc.class);
        System.out.println(hotelDoc);
    }

    @Test
    public void testUpdateDocument() throws IOException {
        UpdateRequest request = new UpdateRequest("hotel", "36934");
        request.doc(
          "price","335",
           "brand","5天酒店"
        );
        client.update(request,RequestOptions.DEFAULT);
    }

    @Test
    public void testDeleteDocument() throws IOException {
        DeleteRequest request = new DeleteRequest("hotel", "36934");
        client.delete(request,RequestOptions.DEFAULT);
    }

    //批量操作
    @Test
    public void testBulk() throws IOException {
        BulkRequest request = new BulkRequest();
        List<Hotel> list = hotelService.list();
        for (Hotel hotel:
             list) {
            HotelDoc hotelDoc = new HotelDoc(hotel);
            request.add(new IndexRequest("hotel")
                    .id(hotelDoc.getId().toString())
                    .source(JSON.toJSONString(hotelDoc),XContentType.JSON));
        }
        client.bulk(request,RequestOptions.DEFAULT);
    }
}
