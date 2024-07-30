package com.hello_events.Entites;

import com.hello_events.Entites.Event;
import com.hello_events.Entites.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int numberOfTickets;
    private LocalDateTime reservationTime;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private LocalDateTime lastUpdated;

    // Remove the userEmail field as it's now represented by the user relationship
    // private String userEmail;

    public enum ReservationStatus {
        PENDING, CONFIRMED, CANCELLED
    }

}