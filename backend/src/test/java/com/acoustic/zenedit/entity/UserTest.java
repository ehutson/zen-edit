package com.acoustic.zenedit.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import net.bytebuddy.utility.RandomString;


class UserTest {
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    ValidatorFactory factor = Validation.buildDefaultValidatorFactory();
    final Validator validator = factor.getValidator();

    @Test
    void testUsernameValidation() {
        User user = getValidUser();
        user.setUsername(getRandomStringOfLength(51));
        Set<ConstraintViolation<User>> violations1 = validator.validate(user);
        assertEquals(1, violations1.size());
        ConstraintViolation<User> violation1 = violations1.stream().toList().get(0);
        assertEquals("size must be between 1 and 50", violation1.getMessage());

        user.setUsername("");
        Set<ConstraintViolation<User>> violations2 = validator.validate(user);
        assertEquals(1, violations2.size());
        ConstraintViolation<User> violation2 = violations2.stream().toList().get(0);
        assertEquals("size must be between 1 and 50", violation2.getMessage());
    }

    @Test
    void testPasswordValidation() {
        User user = getValidUser();
        user.setPassword("password");
        Set<ConstraintViolation<User>> violations1 = validator.validate(user);
        assertEquals(1, violations1.size());
        ConstraintViolation<User> violation1 = violations1.stream().toList().get(0);
        assertEquals("size must be between 60 and 60", violation1.getMessage());

        user = getValidUser();
        user.setPassword(null);
        Set<ConstraintViolation<User>> violations2 = validator.validate(user);
        assertEquals(1, violations2.size());
        ConstraintViolation<User> violation2 = violations2.stream().toList().get(0);
        assertEquals("must not be null", violation2.getMessage());
    }

    @Test
    void testEmailAddressValidation() {
        User user = getValidUser();
        user.setEmail("invalid_email_address");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        ConstraintViolation<User> violation = violations.stream().toList().get(0);
        assertEquals("must be a well-formed email address", violation.getMessage());
    }

    @Test
    void testFirstNameValidation() {
        User user = getValidUser();
        user.setFirstName("");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(0, violations.size());

        user = getValidUser();
        user.setFirstName(getRandomStringOfLength(51));
        Set<ConstraintViolation<User>> violations2 = validator.validate(user);
        assertEquals(1, violations2.size());
        ConstraintViolation<User> violation2 = violations2.stream().toList().get(0);
        assertEquals("size must be between 0 and 50", violation2.getMessage());
    }

    @Test
    void testLastNameValidation() {
        User user = getValidUser();
        user.setLastName("");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(0, violations.size());

        user = getValidUser();
        user.setLastName(getRandomStringOfLength(51));
        Set<ConstraintViolation<User>> violations2 = validator.validate(user);
        assertEquals(1, violations2.size());
        ConstraintViolation<User> violation2 = violations2.stream().toList().get(0);
        assertEquals("size must be between 0 and 50", violation2.getMessage());
    }

    private String getRandomStringOfLength(int length) {
        RandomString rs = new RandomString(length);
        return rs.nextString();
    }

    private User getValidUser() {
        User user = new User();
        user.setUsername("username");
        user.setPassword(passwordEncoder.encode("password"));
        user.setEmail("user@foo.com");
        user.setFirstName("userFirst");
        user.setLastName("userLast");

        return user;
    }
}