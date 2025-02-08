package br.com.edielsonassis.mapper;

import br.com.edielsonassis.dtos.UserEventRequest;
import br.com.edielsonassis.models.User;


public class UserMapper {
    
    private UserMapper() {}

    public static User toEntity(UserEventRequest userEvent) {
        var user = new User();
        user.setUserId(userEvent.getUserId());
        user.setEmail(userEvent.getEmail());
        user.setRole(userEvent.getRole());
        return user;
    }
}