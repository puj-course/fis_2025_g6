package com.fis_2025_g6;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import com.fis_2025_g6.entity.Application;
import com.fis_2025_g6.repository.ApplicationRepository;
import com.fis_2025_g6.service.ApplicationService;
import com.fis_2025_g6.state.PendingState;

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

    @Test
    void testFindById_Exists() {
        Application app = new Application();
        when(applicationRepository.findById(1L)).thenReturn(Optional.of(app));

        var result = applicationService.findById(1L);

        assertNotNull(result);
        assertEquals(app, result.get());
    }

    @Test
    void testFindByAdoptantId() {
        List<Application> apps = List.of(new Application());
        when(applicationRepository.findByAdoptantId(5L)).thenReturn(apps);

        List<Application> result = applicationService.findByAdoptantId(5L);

        assertEquals(1, result.size());
    }

    @Test
    void testDelete_Exists() {
        when(applicationRepository.existsById(1L)).thenReturn(true);

        boolean result = applicationService.delete(1L);

        assertEquals(true, result);
    }

    @Test
    void testDelete_NotExists() {
        when(applicationRepository.existsById(99L)).thenReturn(false);

        boolean result = applicationService.delete(99L);

        assertEquals(false, result);
    }

    @Test
    void testApproveApplication() {
        Application app = new Application();
        app.setState(new PendingState());
        when(applicationRepository.findById(1L)).thenReturn(Optional.of(app));
        when(applicationRepository.save(app)).thenReturn(app);

        Application result = applicationService.approve(1L);

        assertNotNull(result);
        assertEquals(ApplicationStatus.APPROVED, result.getStatus());
    }

    @Test
    void testRejectApplication() {
        Application app = new Application();
        app.setState(new PendingState());
        when(applicationRepository.findById(2L)).thenReturn(Optional.of(app));
        when(applicationRepository.save(app)).thenReturn(app);

        Application result = applicationService.reject(2L);

        assertNotNull(result);
        assertEquals(ApplicationStatus.REJECTED, result.getStatus());
    }

    @Test
    void testCancelApplication() {
        Application app = new Application();
        app.setState(new PendingState());
        when(applicationRepository.findById(3L)).thenReturn(Optional.of(app));
        when(applicationRepository.save(app)).thenReturn(app);

        Application result = applicationService.cancel(3L);

        assertNotNull(result);
        assertEquals(ApplicationStatus.CANCELED, result.getStatus());
    }

    @Test
    void testUpdateStatus_Approved() {
        Application app = new Application();
        app.setState(new PendingState());
        when(applicationRepository.findById(4L)).thenReturn(Optional.of(app));
        when(applicationRepository.save(app)).thenReturn(app);

        Application result = applicationService.updateStatus(4L, ApplicationStatus.APPROVED);

        assertNotNull(result);
        assertEquals(ApplicationStatus.APPROVED, result.getStatus());
    }

    @Test
    void testUpdateStatus_Invalid() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            applicationService.updateStatus(5L, null);
        });

        assertEquals("Estado no soportado o transición inválida", exception.getMessage());
    }
}
