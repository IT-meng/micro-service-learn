package cn.itcast.feign.clients.fallBack;

import cn.itcast.feign.clients.UserClient;
import cn.itcast.feign.pojo.User;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserClientFallBackFactory implements FallbackFactory<UserClient> {
    @Override
    public UserClient create(Throwable throwable) {
        return new UserClient() {
            @Override
            //降级的逻辑
            public User getById(Long id) {
                log.info("查询用户信息失败",throwable);

                return new User();
            }
        };
    }
}
