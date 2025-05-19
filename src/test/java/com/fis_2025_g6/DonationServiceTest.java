package com.fis_2025_g6;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import com.fis_2025_g6.entity.Donation;
import com.fis_2025_g6.repository.DonationRepository;
import com.fis_2025_g6.service.DonationService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DonationServiceTest {

    @Mock
    private DonationRepository donationRepository;

    @InjectMocks
    private DonationService donationService;

    @Test
    void testFindById() {
        Donation donation = new Donation();
        donation.setId(1L);
        donation.setAmount(100.0);

        when(donationRepository.findById(1L)).thenReturn(Optional.of(donation));

        Optional<Donation> result = donationService.findById(1L);
        assertTrue(result.isPresent());
        assertEquals(100.0, result.get().getAmount());
    }

    @Test
    void testDelete() {
        Long donationId = 1L;

        when(donationRepository.existsById(donationId)).thenReturn(true);

        boolean deleted = donationService.delete(donationId);

        assertTrue(deleted);
        verify(donationRepository).deleteById(donationId);
    }

    @Test
    void testFindAll() {
        Donation donation1 = new Donation();
        donation1.setId(1L);
        donation1.setAmount(50.0);

        Donation donation2 = new Donation();
        donation2.setId(2L);
        donation2.setAmount(75.0);

        when(donationRepository.findAll()).thenReturn(List.of(donation1, donation2));

        List<Donation> result = donationService.findAll();

        assertEquals(2, result.size());
        assertEquals(50.0, result.get(0).getAmount());
    }

    @Test
    void testCreateDonation() {
        Donation donation = new Donation();
        donation.setAmount(120.0);

        when(donationRepository.save(donation)).thenReturn(donation);

        Donation result = donationService.create(donation);

        assertEquals(120.0, result.getAmount());
    }

    @Test
    void testDelete_NotExists() {
        Long donationId = 99L;

        when(donationRepository.existsById(donationId)).thenReturn(false);

        boolean deleted = donationService.delete(donationId);

        assertTrue(!deleted);
        verify(donationRepository, never()).deleteById(donationId);
    }

    @Test
    void testFindByAdoptantId() {
        Long adoptantId = 10L;
        Donation donation = new Donation();
        donation.setId(1L);
        donation.setAmount(100.0);

        when(donationRepository.findByAdoptantId(adoptantId)).thenReturn(List.of(donation));

        List<Donation> result = donationService.findByAdoptantId(adoptantId);

        assertEquals(1, result.size());
        assertEquals(100.0, result.get(0).getAmount());
    }
}
