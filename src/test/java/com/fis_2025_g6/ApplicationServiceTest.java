package com.fis_2025_g6;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.List;

import com.fis_2025_g6.entity.Application;
import com.fis_2025_g6.repository.ApplicationRepository;
import com.fis_2025_g6.service.ApplicationService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ApplicationServiceTest {

    @Mock
    private ApplicationRepository applicationRepository;

    @InjectMocks
    private ApplicationService applicationService;

    @Test
    void testCreateApplication() {
        Application app = new Application();
        app.setStatus(ApplicationStatus.PENDING);

        when(applicationRepository.save(app)).thenReturn(app);

        Application result = applicationService.create(app);

        assertNotNull(result);
        assertEquals(ApplicationStatus.PENDING, result.getStatus());
    }

    @Test
    void testFindAll() {
        List<Application> applications = List.of(new Application(), new Application());

        when(applicationRepository.findAll()).thenReturn(applications);

        List<Application> result = applicationService.findAll();

        assertEquals(2, result.size());
    }
}
