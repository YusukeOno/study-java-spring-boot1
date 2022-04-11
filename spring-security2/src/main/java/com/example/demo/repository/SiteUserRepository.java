package com.example.demo.repository;

import com.example.demo.model.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SiteUserRepository extends JpaRepository<SiteUser, Long> {
    SiteUser findByUsername(String username);
    boolean existsByUsername(String username);
}
