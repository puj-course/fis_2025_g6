package com.fis_2025_g6.state;

import com.fis_2025_g6.entity.Application;

public class CanceledState implements ApplicationState {
    @Override
    public void approve(Application application) {
        throw new IllegalArgumentException("No se puede aprobar una solicitud cancelada");
    }

    @Override
    public void reject(Application application) {
        throw new IllegalArgumentException("No se puede rechazar una solicitud cancelada");
    }

    @Override
    public void cancel(Application application) {
        throw new IllegalArgumentException("La solicitud ya está cancelada");
    }
}
