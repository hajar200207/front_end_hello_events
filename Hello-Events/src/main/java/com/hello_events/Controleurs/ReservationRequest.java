package com.hello_events.Controleurs;

import com.hello_events.Entites.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationRequest {

    private Long id;

    private Long eventId;

    private int numberOfTickets;

    private String event;

    private LocalDateTime lastUpdated;

    private String email;

}