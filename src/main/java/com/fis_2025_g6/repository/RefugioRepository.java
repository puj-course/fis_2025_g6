package com.fis_2025_g6.repository;

import com.fis_2025_g6.entity.Refugio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefugioRepository extends JpaRepository<Refugio, Long> {}
