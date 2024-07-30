package com.hello_events.Entites;

import jakarta.persistence.*;
import lombok.Data;


import java.time.LocalDateTime;

@Entity
@Data
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String subject;
    private String message;

    private LocalDateTime submissionTime;

    @Enumerated(EnumType.STRING)
    private ContactStatus status;

    public enum ContactStatus {
        NEW, IN_PROGRESS, PROCESSED, RESOLVED
    }

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
}