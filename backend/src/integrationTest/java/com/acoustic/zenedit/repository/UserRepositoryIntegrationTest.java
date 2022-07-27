package com.acoustic.zenedit.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.acoustic.zenedit.entity.User;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
class UserRepositoryIntegrationTest {

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    UserRepository userRepository;

    @Test
    void findByUsername() throws NoSuchElementException {
        User user1 = entityManager.persistAndFlush(getValidUser(1));
        User user2 = entityManager.persistAndFlush(getValidUser(2));

        Optional<User> optionalFoundUser1 = userRepository.findByUsername("username1");
        User foundUser = optionalFoundUser1.orElseThrow();
        assertThat(foundUser).isSameAs(user1);

        Optional<User> optionalFoundUser2 = userRepository.findByUsername("username2");
        User foundUser2 = optionalFoundUser2.orElseThrow();
        assertThat(foundUser2).isSameAs(user2);
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