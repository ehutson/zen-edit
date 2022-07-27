package com.acoustic.zenedit.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.BeforeEach;
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
class UserIntegrationTest {
    @Autowired
    private TestEntityManager entityManager;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private User user1;
    private String user1Password;
    private User user2;
    private String user2Password;

    @BeforeEach
    void setup() {

        user1Password = passwordEncoder.encode("foo");
        user2Password = passwordEncoder.encode("foo2");

        user1 = new User();
        user1.setUsername("user1");
        user1.setPassword(user1Password);
        user1.setEmail("user1@foo.com");
        user1.setFirstName("user1First");
        user1.setLastName("user1Last");

        user2 = new User();
        user2.setUsername("user2");
        user2.setPassword(user2Password);
        user2.setEmail("user2@foo.com");
        user2.setFirstName("user2First");
        user2.setLastName("user2Last");
    }

    @Test
    void saveUsers() {
        User savedUser1 = entityManager.persist(user1);
        User savedUser2 = entityManager.persist(user2);

        assertThat(savedUser1).isSameAs(user1);
        assertNotNull(savedUser1.getId());

        assertThat(savedUser2).isSameAs(user2);
        assertNotNull(savedUser2.getId());
    }

    @Test
    void createUserNullUsernameException() {
        User user = new User();
        user.setPassword(user1Password);
        user.setEmail("user@foo.com");
        user.setFirstName("userFirst");
        user.setLastName("userLast");

        assertThrows(
                ConstraintViolationException.class,
                () -> entityManager.persist(user)
        );
    }

    @Test
    void createUserLongUsernameException() {
        User user = new User();
        user.setUsername("thisisatestofaverylongusernamethisisatestofaverylongusername");
        user.setPassword(user1Password);
        user.setEmail("user@foo.com");
        user.setFirstName("userFirst");
        user.setLastName("userLast");

        ConstraintViolationException e = assertThrows(
                ConstraintViolationException.class,
                () -> entityManager.persist(user)
        );

        assertTrue(e.getMessage().contains("size must be between 1 and 50"));
    }

    @Test
    void testDuplicateUsername() {
        entityManager.persist(user1);
        user2.setUsername("user1");

        assertThrows(
                PersistenceException.class,
                () -> entityManager.persist(user2)
        );
    }

    @Test
    void testDuplicateEmailAddress() {
        entityManager.persist(user1);
        user2.setEmail("user1@foo.com");

        assertThrows(
                PersistenceException.class,
                () -> entityManager.persist(user2)
        );
    }
}