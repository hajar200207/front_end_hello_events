package com.hello_events.Controleurs;

import java.time.LocalDateTime;

class ReservationRequest {
    private Long eventId;
    private int numberOfTickets;

    // Getters and setters
    public Long getEventId() { return eventId; }
    public void setEventId(Long eventId) { this.eventId = eventId; }
    public int getNumberOfTickets() { return numberOfTickets; }
    public void setNumberOfTickets(int numberOfTickets) { this.numberOfTickets = numberOfTickets; }
}