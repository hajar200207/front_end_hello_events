package com.hello_events.Controleurs;

import com.hello_events.Entites.User;
import com.hello_events.Entites.Event;
import com.hello_events.Repositories.UserRepository;
import com.hello_events.Repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.hello_events.Entites.Reservation;
import com.hello_events.Services.ReservationService;

import java.time.LocalDateTime;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/reservation")
    public ResponseEntity<Reservation> createReservation(@RequestBody ReservationRequest reservationRequest) {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String username = loggedInUser.getName();
        User user = userRepository.findByUsername(username);

        try {
            Event event = eventRepository.findById(reservationRequest.getEventId())
                    .orElseThrow(() -> new RuntimeException("Event not found"));

            Reservation reservation = new Reservation();
            reservation.setUser(user);
            reservation.setEvent(event);
            reservation.setNumberOfTickets(reservationRequest.getNumberOfTickets());
            reservation.setLastUpdated(LocalDateTime.now());
            reservation.setReservationTime(LocalDateTime.now());
            reservation.setStatus(Reservation.ReservationStatus.PENDING);

            Reservation savedReservation = reservationService.createReservation(reservation);
            return ResponseEntity.ok(savedReservation);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // New endpoint to get reservations by user
    @GetMapping("/reservations")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Reservation>> getUserReservations() {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String username = loggedInUser.getName();
        User user = userRepository.findByUsername(username);

        List<Reservation> reservations = reservationService.getReservationsByUser(user);
        return ResponseEntity.ok(reservations);
    }

}
