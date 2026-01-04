package feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import request.LoginRequest;
import response.LoginResponse;

@FeignClient(name = "user-service", path = "/api/users")
public interface UserFeignClient {

    @PostMapping("/login")
    LoginResponse login(@RequestBody LoginRequest request);

    // 其他接口可以在后续添加
}