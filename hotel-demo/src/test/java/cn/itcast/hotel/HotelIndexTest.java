package cn.itcast.hotel;

import cn.itcast.hotel.constantes.HotelConstances;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class HotelIndexTest {
    private RestHighLevelClient client;

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
    public void createIndexTest() throws IOException {
        //1、创建CreateIndexRequest对象
        CreateIndexRequest request = new CreateIndexRequest("hotel");
        //2、准备DSL语句
        request.source(HotelConstances.MAPPING_TEMPLATE, XContentType.JSON);
        //3、发送请求
        client.indices().create(request, RequestOptions.DEFAULT);
    }
    @Test
    public void testDeleteIndex() throws IOException {
        //1
        DeleteIndexRequest request = new DeleteIndexRequest("hotel");
        //2
        client.indices().delete(request,RequestOptions.DEFAULT);
    }

    @Test
    public void testExsistsIndex() throws IOException {
        GetIndexRequest request = new GetIndexRequest("hotel");
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        System.err.println(exists?"索引库存在":"索引库不存在");
    }
}
