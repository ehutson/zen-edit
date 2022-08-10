package com.acoustic.zenedit.rest.controller;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.acoustic.zenedit.entity.User;
import com.acoustic.zenedit.exception.DuplicateUserInfoException;
import com.acoustic.zenedit.rest.dto.SignupRequest;
import com.acoustic.zenedit.rest.dto.UserDto;
import com.acoustic.zenedit.rest.dto.UserUpdateRequest;
import com.acoustic.zenedit.rest.mapper.UserMapper;
import com.acoustic.zenedit.service.UserService;

import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public List<UserDto> getUsers() {
        return userService.getUsers().stream()
                .map(userMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{username}")
    public UserDto getUser(@PathVariable String username) {
        return userMapper.toUserDto(userService.validateAndGetUserByUsername(username));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public UserDto signUp(@Valid @RequestBody SignupRequest signupRequest) {
        if (userService.hasUserWithUsername(signupRequest.getUsername())) {
            throw new DuplicateUserInfoException(String.format("Username %s has already been used", signupRequest.getUsername()));
        }
        if (userService.hasUserWithEmail(signupRequest.getEmail())) {
            throw new DuplicateUserInfoException(String.format("Email %s has already been used", signupRequest.getEmail()));
        }

        // temporarily make the password 60 chars... until we implement security.
        RandomString rs = new RandomString(60 - signupRequest.getPassword().length());
        signupRequest.setPassword(signupRequest.getPassword() + rs.nextString());

        User user = userService.saveUser(userMapper.toUser(signupRequest));
        return userMapper.toUserDto(user);
    }

    @PutMapping("/{id}")
    public UserDto updateUser(@RequestBody UserUpdateRequest userRequest, @PathVariable Long id) {
        User user = userService.validateAndGetUserById(id);
        if (userRequest.getEmail() != null) {
            user.setEmail(userRequest.getEmail());
        }
        if (userRequest.getPassword() != null) {
            user.setPassword(userRequest.getPassword());
        }
        if (userRequest.getFirstName() != null) {
            user.setFirstName(userRequest.getFirstName());
        }
        if (userRequest.getLastName() != null) {
            user.setLastName(userRequest.getLastName());
        }
        userService.saveUser(user);
        return userMapper.toUserDto(user);
    }

    @DeleteMapping("/{id}")
    public UserDto deleteUser(@PathVariable Long id) {
        User user = userService.validateAndGetUserById(id);
        userService.deleteUser(user);
        return userMapper.toUserDto(user);
    }
}
