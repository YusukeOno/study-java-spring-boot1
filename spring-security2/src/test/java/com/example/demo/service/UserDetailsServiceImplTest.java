package com.example.demo.service;

import com.example.demo.model.SiteUser;
import com.example.demo.repository.SiteUserRepository;
import com.example.demo.util.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserDetailsServiceImplTest {

    @Autowired
    SiteUserRepository repository;

    @Autowired
    UserDetailsServiceImpl service;

    @Test
    @DisplayName("ユーザ名が存在する時、ユーザ詳細を取得することを期待する")
    void whenUsernameExists_expectToGetUserDetails() {
        // Arrange
        var user = new SiteUser();
        user.setUsername("Harada");
        user.setPassword("password");
        user.setEmail("harada@example.com");
        user.setRole(Role.USER.name());
        repository.save(user);

        // Act
        var actual = service.loadUserByUsername("Harada");

        // Assert
        assertEquals(user.getUsername(), actual.getUsername());
    }

    @Test
    @DisplayName("ユーザ名が存在しない時、例外をスローします")
    void whenUsernameDoesNotExist_throwException() {
        // Act & Assert
        assertThrows(UsernameNotFoundException.class,
                () -> service.loadUserByUsername("Takeda"));
    }
}