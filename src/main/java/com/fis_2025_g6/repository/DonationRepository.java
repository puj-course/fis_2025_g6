package com.fis_2025_g6.repository;

import com.fis_2025_g6.entity.Donation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {
    List<Donation> findByAdoptantId(Long adoptantId);
}
