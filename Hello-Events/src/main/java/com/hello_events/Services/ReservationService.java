package com.hello_events.Services;

import com.hello_events.Controleurs.ReservationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hello_events.Entites.Reservation;
import com.hello_events.Entites.Event;
import com.hello_events.Entites.User;
import com.hello_events.Repositories.ReservationRepository;
import com.hello_events.Repositories.EventRepository;
import com.hello_events.Repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    public Reservation createReservation(String userEmail, Long eventId, int numberOfTickets, LocalDateTime lastUpdated) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + userEmail));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setEvent(event);
        reservation.setNumberOfTickets(numberOfTickets);
        reservation.setReservationTime(LocalDateTime.now());
        reservation.setLastUpdated(lastUpdated);
        reservation.setStatus(Reservation.ReservationStatus.PENDING);

        return reservationRepository.save(reservation);
    }
    public Reservation createReservation(Reservation reservation) {
        // Add any business logic here if needed
        return reservationRepository.save(reservation);
    }

    public List<ReservationRequest> getReservationsByUser(User user) {
        return reservationRepository.findByUser(user).stream().map(reservation -> {
            Event event = eventRepository.findById(reservation.getEvent().getId()).get();
            return new ReservationRequest(reservation.getId(), event.getId(),reservation.getNumberOfTickets(), event.getName(), reservation.getLastUpdated(), user.getEmail());
        }).toList();
    }


}