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

class DocumentTest {
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    ValidatorFactory factor = Validation.buildDefaultValidatorFactory();
    final Validator validator = factor.getValidator();

    @Test
    void testNameValidation() {
        Document document = getValidDocument();

        document.setTitle("");
        Set<ConstraintViolation<Document>> violations1 = validator.validate(document);
        assertEquals(1, violations1.size());
        ConstraintViolation<Document> violation1 = violations1.stream().toList().get(0);
        assertEquals("size must be between 1 and 255", violation1.getMessage());

        RandomString rs = new RandomString(256);
        document.setTitle(rs.nextString());
        Set<ConstraintViolation<Document>> violations2 = validator.validate(document);
        assertEquals(1, violations2.size());
        ConstraintViolation<Document> violation2 = violations2.stream().toList().get(0);
        assertEquals("size must be between 1 and 255", violation2.getMessage());
    }

    @Test
    void testContentValidation() {
        Document document = getValidDocument();
        document.setContent(null);

        Set<ConstraintViolation<Document>> violations1 = validator.validate(document);
        assertEquals(1, violations1.size());
        ConstraintViolation<Document> violation1 = violations1.stream().toList().get(0);
        assertEquals("must not be null", violation1.getMessage());
    }

    @Test
    void testUserValidation() {
        Document document = getValidDocument();
        document.setUser(null);

        Set<ConstraintViolation<Document>> violations1 = validator.validate(document);
        assertEquals(1, violations1.size());
        ConstraintViolation<Document> violation1 = violations1.stream().toList().get(0);
        assertEquals("must not be null", violation1.getMessage());
    }

    private Document getValidDocument() {
        Document document = new Document();
        document.setTitle("test1");
        document.setContent("content 1");
        document.setUser(getValidUser());
        return document;
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