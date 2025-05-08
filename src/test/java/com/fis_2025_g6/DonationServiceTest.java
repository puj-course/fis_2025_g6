package com.fis_2025_g6;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

}
