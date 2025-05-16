package com.fis_2025_g6.state;

import com.fis_2025_g6.ApplicationStatus;
import com.fis_2025_g6.entity.Application;

public class PendingState implements ApplicationState {
    @Override
    public void approve(Application context) {
        context.setStatus(ApplicationStatus.APPROVED);
    }

    @Override
    public void reject(Application context) {
        context.setStatus(ApplicationStatus.REJECTED);
    }

    @Override
    public void cancel(Application context) {
        context.setStatus(ApplicationStatus.CANCELED);
    }
}
