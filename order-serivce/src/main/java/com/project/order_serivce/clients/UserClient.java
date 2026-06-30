package com.project.order_serivce.clients;

import com.project.order_serivce.configurations.FeignClientInterceptorConfig;
import com.project.order_serivce.dtos.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", configuration = FeignClientInterceptorConfig.class)
public interface UserClient {
    @GetMapping("/api/users/{id}")
    UserDTO getUserById(@PathVariable Long id);

    @GetMapping("/api/users/keycloak/{sub}")
    UserDTO getUserByKeycloakId(@PathVariable("sub") String keycloakId);
}
