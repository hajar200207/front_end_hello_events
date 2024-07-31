import com.hello_events.Entites.Reservation;
import com.hello_events.Entites.Event;
import com.hello_events.Entites.User;
import com.hello_events.Repositories.ReservationRepository;
import com.hello_events.Repositories.EventRepository;
import com.hello_events.Repositories.UserRepository;
import com.hello_events.Services.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateReservation() {
        // Arrange
        String userEmail = "test@example.com";
        Long eventId = 1L;
        int numberOfTickets = 2;
        LocalDateTime lastUpdated = LocalDateTime.now();

        User user = new User();
        user.setEmail(userEmail);

        Event event = new Event();
        event.setId(eventId);

        Reservation expectedReservation = new Reservation();
        expectedReservation.setUser(user);
        expectedReservation.setEvent(event);
        expectedReservation.setNumberOfTickets(numberOfTickets);
        expectedReservation.setStatus(Reservation.ReservationStatus.PENDING);

        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.of(user));
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(expectedReservation);

        // Act
        Reservation result = reservationService.createReservation(userEmail, eventId, numberOfTickets, lastUpdated);

        // Assert
        assertNotNull(result);
        assertEquals(user, result.getUser());
        assertEquals(event, result.getEvent());
        assertEquals(numberOfTickets, result.getNumberOfTickets());
        assertEquals(Reservation.ReservationStatus.PENDING, result.getStatus());

        verify(userRepository, times(1)).findByEmail(userEmail);
        verify(eventRepository, times(1)).findById(eventId);
        verify(reservationRepository, times(1)).save(any(Reservation.class));
    }

    @Test
    void testCreateReservationUserNotFound() {
        // Arrange
        String userEmail = "nonexistent@example.com";
        Long eventId = 1L;
        int numberOfTickets = 2;
        LocalDateTime lastUpdated = LocalDateTime.now();

        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () ->
                reservationService.createReservation(userEmail, eventId, numberOfTickets, lastUpdated)
        );

        verify(userRepository, times(1)).findByEmail(userEmail);
        verify(eventRepository, never()).findById(anyLong());
        verify(reservationRepository, never()).save(any(Reservation.class));
    }

    @Test
    void testCreateReservationEventNotFound() {
        // Arrange
        String userEmail = "test@example.com";
        Long eventId = 1L;
        int numberOfTickets = 2;
        LocalDateTime lastUpdated = LocalDateTime.now();

        User user = new User();
        user.setEmail(userEmail);

        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.of(user));
        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () ->
                reservationService.createReservation(userEmail, eventId, numberOfTickets, lastUpdated)
        );

        verify(userRepository, times(1)).findByEmail(userEmail);
        verify(eventRepository, times(1)).findById(eventId);
        verify(reservationRepository, never()).save(any(Reservation.class));
    }
}