package com.fis_2025_g6.service;

import com.fis_2025_g6.entity.Donation;
import com.fis_2025_g6.repository.DonationRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DonationService {
    private final DonationRepository donationRepository;

    public DonationService(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
    }

    public List<Donation> findAll() {
        return donationRepository.findAll();
    }

    public Optional<Donation> findById(Long id) {
        return donationRepository.findById(id);
    }

    public List<Donation> findByAdoptantId(Long adoptantId) {
        return donationRepository.findByAdoptantId(adoptantId);
    }

    public List<Donation> findByRefugeId(Long refugeId) {
        return donationRepository.findByRefugeId(refugeId);
    }

    public Donation create(Donation donation) {
        return donationRepository.save(donation);
    }

    public boolean delete(Long id) {
        boolean exists = donationRepository.existsById(id);
        if (exists) {
            donationRepository.deleteById(id);
        }
        return exists;
    }
}
