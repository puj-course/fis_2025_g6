package com.fis_2025_g6.state;

import com.fis_2025_g6.entity.Application;

public interface ApplicationState {
    void approve(Application application);
    void reject(Application application);
    void cancel(Application application);
}
