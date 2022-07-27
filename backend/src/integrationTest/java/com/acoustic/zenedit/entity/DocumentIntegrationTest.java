package com.acoustic.zenedit.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
class DocumentIntegrationTest {
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void saveDocument() {
        User user = getValidUser();
        User savedUser = entityManager.persist(user);
        Document document = getValidDocument(savedUser);

        Document savedDocument = entityManager.persist(document);

        assertNotNull(savedDocument.getId());
        assertThat(savedDocument).isSameAs(document);
    }

    @Test
    void createDocumentNullTitleException() {
        User user = getValidUser();
        User savedUser = entityManager.persist(user);
        Document document = getValidDocument(savedUser);
        document.setTitle(null);

        assertThrows(
                ConstraintViolationException.class,
                () -> entityManager.persist(document)
        );
    }

    @Test
    void createDocumentNullContentException() {
        User user = getValidUser();
        User savedUser = entityManager.persist(user);
        Document document = getValidDocument(savedUser);
        document.setContent(null);

        assertThrows(
                ConstraintViolationException.class,
                () -> entityManager.persist(document)
        );
    }

    @Test
    void createDocumentNullUserException() {
        User user = getValidUser();
        Document document = getValidDocument(user);
        document.setUser(null);

        assertThrows(
                ConstraintViolationException.class,
                () -> entityManager.persist(document)
        );
    }

    private Document getValidDocument(User user) {
        Document document = new Document();
        document.setTitle("test1");
        document.setContent("content 1");
        document.setUser(user);
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
