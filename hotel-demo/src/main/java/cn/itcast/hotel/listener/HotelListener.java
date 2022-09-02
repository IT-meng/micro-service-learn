package cn.itcast.hotel.listener;

import cn.itcast.hotel.constantes.HotelConstantes;
import cn.itcast.hotel.pojo.Hotel;
import cn.itcast.hotel.pojo.HotelDoc;
import cn.itcast.hotel.service.IHotelService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class HotelListener {

    @Autowired
    private IHotelService hotelService;
    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @RabbitListener(bindings = {@QueueBinding(
           exchange = @Exchange(name = HotelConstantes.EXCHANGE_NAME,type = ExchangeTypes.TOPIC),
            value = @Queue(name = HotelConstantes.INSERT_QUEUE),
            key = {HotelConstantes.INSERT_KEY}
    )})
    public void listenInsertOrUpdate(Long id){
        Hotel hotel = hotelService.getById(id);
        HotelDoc hotelDoc = new HotelDoc(hotel);
        IndexRequest request = new IndexRequest("hotel").id(id.toString());
        request.source(JSON.toJSONString(hotelDoc), XContentType.JSON);
        try {
            restHighLevelClient.index(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    exchange = @Exchange(name=HotelConstantes.EXCHANGE_NAME,type = ExchangeTypes.TOPIC),
                    value = @Queue(name = HotelConstantes.DELETE_QUEUE),
                    key = {HotelConstantes.DELETE_KEY}
            )
    })
    public void listenDelete(Long id){
        DeleteRequest request = new DeleteRequest("hotel", id.toString());
        try {
            restHighLevelClient.delete(request,RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
