package com.fis_2025_g6.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fis_2025_g6.ApplicationStatus;
import com.fis_2025_g6.state.ApplicationState;
import com.fis_2025_g6.state.ApprovedState;
import com.fis_2025_g6.state.CanceledState;
import com.fis_2025_g6.state.PendingState;
import com.fis_2025_g6.state.RejectedState;

@Entity
@Table(name = "solicitud")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "solic_id")
    private Long id;

    @Column(name = "solic_fecha", nullable = false)
    private Date date;

    @Enumerated(EnumType.STRING)
    @Column(name = "solic_estado", nullable = false)
    private ApplicationStatus status;

    @ManyToOne
    @JoinColumn(name = "adopt_id", nullable = false)
    private Adoptant adoptant;

    @ManyToOne
    @JoinColumn(name = "masc_id", nullable = false)
    private Pet pet;

    @Transient
    @JsonIgnore
    private ApplicationState state;

    @PostLoad
    @PostPersist
    public void initState() {
        this.state = switch (this.status) {
            case PENDING -> new PendingState();
            case APPROVED -> new ApprovedState();
            case REJECTED -> new RejectedState();
            case CANCELED -> new CanceledState();
        };
    }

    public void approve() {
        state.approve(this);
    }

    public void reject() {
        state.reject(this);
    }

    public void cancel() {
        state.cancel(this);
    }

    public void setStatusAndSync(ApplicationStatus newStatus) {
        this.status = newStatus;
        initState();
    }
}
