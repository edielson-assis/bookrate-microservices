package br.com.edielsonassis.mapper;

import br.com.edielsonassis.dtos.UserEventRequest;
import br.com.edielsonassis.dtos.UserRequest;
import br.com.edielsonassis.dtos.UserResponse;
import br.com.edielsonassis.model.User;

public class UserMapper {
    
    private UserMapper() {}

    public static User toEntity(UserRequest userRequest) {
        var user = new User();
        user.setEmail(userRequest.email());
        user.setPassword(userRequest.password());
        return user;
    }

    public static UserResponse toDto(User user) {
        return UserResponse.builder()
                .userId(user.getUserId())
                .email(user.getEmail()).build();
    }

    public static UserEventRequest toEventDto(User user) {
        var userEvent = new UserEventRequest();
        userEvent.setUserId(user.getUserId());
        userEvent.setEmail(user.getEmail());
        userEvent.setRole(user.getRole().name());
        return userEvent;
    }
}