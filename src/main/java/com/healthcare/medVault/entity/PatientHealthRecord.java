package com.healthcare.medVault.entity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "patient_health_records")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientHealthRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @Column(name = "record_date", nullable = false)
    private LocalDateTime recordDate;

    @Column(columnDefinition = "TEXT")
    private String diagnosis;

    @Column(columnDefinition = "TEXT")
    private String symptoms;

    @Column(columnDefinition = "TEXT")
    private String vitals;

    @Column(columnDefinition = "TEXT")
    private String prescriptions;

    @Column(name = "lab_results", columnDefinition = "TEXT")
    private String labResults;

    @Column(columnDefinition = "TEXT")
    private String comments;

    private byte[] attachments;
}
