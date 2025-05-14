package com.fis_2025_g6.state;

import com.fis_2025_g6.ApplicationStatus;
import com.fis_2025_g6.entity.Application;

public class ApprovedState implements ApplicationState {
    @Override
    public void approve(Application application) {
        throw new IllegalArgumentException("La solicitud ya está aprobada");
    }

    @Override
    public void reject(Application application) {
        throw new IllegalArgumentException("No se puede rechazar una solicitud aprobada");
    }

    @Override
    public void cancel(Application application) {
        application.setStatus(ApplicationStatus.CANCELED);
    }
}
