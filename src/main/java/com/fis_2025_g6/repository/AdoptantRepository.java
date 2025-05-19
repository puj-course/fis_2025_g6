package com.fis_2025_g6.repository;

import com.fis_2025_g6.entity.Adoptant;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdoptantRepository extends JpaRepository<Adoptant, Long> {
    Optional<Adoptant> findByUsername(String username);
    boolean existsByUsernameAndIdNot(String username, Long id);
    boolean existsByEmailAndIdNot(String email, Long id);
}
