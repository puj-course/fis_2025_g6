package com.fis_2025_g6.repository;

import com.fis_2025_g6.entity.Refuge;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefugeRepository extends JpaRepository<Refuge, Long> {
    Optional<Refuge> findByUsername(String username);
    boolean existsByUsernameAndIdNot(String username, Long id);
    boolean existsByEmailAndIdNot(String email, Long id);
}
