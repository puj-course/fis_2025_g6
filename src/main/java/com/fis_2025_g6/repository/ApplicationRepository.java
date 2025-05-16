package com.fis_2025_g6.repository;

import com.fis_2025_g6.entity.Application;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByAdoptantId(Long adoptantId);
}
