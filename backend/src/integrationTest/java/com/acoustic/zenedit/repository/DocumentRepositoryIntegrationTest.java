package com.acoustic.zenedit.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.acoustic.zenedit.entity.Document;
import com.acoustic.zenedit.entity.User;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
class DocumentRepositoryIntegrationTest {

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private DocumentRepository documentRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void findAllByUser() {
        User user1 = entityManager.persistAndFlush(getValidUser(1));
        User user2 = entityManager.persistAndFlush(getValidUser(2));

        Document document11 = entityManager.persistAndFlush(getValidDocument(1, user1));
        Document document12 = entityManager.persistAndFlush(getValidDocument(2, user1));
        Document document13 = entityManager.persistAndFlush(getValidDocument(3, user1));

        Document document21 = entityManager.persistAndFlush(getValidDocument(4, user2));
        Document document22 = entityManager.persistAndFlush(getValidDocument(5, user2));

        List<Document> user1Documents = documentRepository.findAllByUser(user1);
        assertThat(user1Documents).hasSize(3).contains(document11, document12, document13);

        List<Document> user2Documents = documentRepository.findAllByUser(user2);
        assertThat(user2Documents).hasSize(2).contains(document21, document22);
    }

    private Document getValidDocument(int id, User user) {
        Document document = new Document();
        document.setTitle("test" + id);
        document.setContent("content " + id);
        document.setUser(user);
        return document;
    }

    private User getValidUser(int id) {
        User user = new User();
        user.setUsername("username" + id);
        user.setPassword(passwordEncoder.encode("password" + id));
        user.setEmail("user" + id + "@foo.com");
        user.setFirstName("user" + id + "First");
        user.setLastName("user" + id + "Last");

        return user;
    }
}
