package com.acoustic.zenedit.rest.mapper;

import org.mapstruct.Mapper;
import org.springframework.context.annotation.Configuration;

import com.acoustic.zenedit.entity.User;
import com.acoustic.zenedit.rest.dto.SignupRequest;
import com.acoustic.zenedit.rest.dto.UserDto;

@Configuration
@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toUserDto(User user);

    User toUser(SignupRequest signupRequest);
}
