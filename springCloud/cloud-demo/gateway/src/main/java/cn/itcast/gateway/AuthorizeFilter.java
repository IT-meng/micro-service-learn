package cn.itcast.gateway;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Order(-1) //配置过滤器顺序，数字越小优先级越高
@Component
public class AuthorizeFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        MultiValueMap<String, String> queryParams = request.getQueryParams();
        String authorization = queryParams.getFirst("authorization");
        if("admin".equals(authorization)){
            //直接放行
            return chain.filter(exchange);
        }else{

            //设置状态码
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);

            //拦截
            return exchange.getResponse().setComplete();
        }
    }
}
