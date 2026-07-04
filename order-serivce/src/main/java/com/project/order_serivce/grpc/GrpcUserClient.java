package com.project.order_serivce.grpc;

import com.project.user_serivce.grpc.GetUserRequest;
import com.project.user_serivce.grpc.UserGrpcServiceGrpc;
import com.project.user_serivce.grpc.UserResponse;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class GrpcUserClient {

    @GrpcClient("user-service")
    private UserGrpcServiceGrpc.UserGrpcServiceBlockingStub userStub;

    public UserResponse getUserById(Long id) {
        GetUserRequest request = GetUserRequest.newBuilder().setUserId(id).build();
        return userStub.getUserById(request);
    }
}
