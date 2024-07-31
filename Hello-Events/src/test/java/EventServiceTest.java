
import com.hello_events.Entites.Contact;
import com.hello_events.Entites.Event;
import com.hello_events.Repositories.EventRepository;
import com.hello_events.Services.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateEvent() {
        Event event = new Event();
        event.setName("Test Event");
        when(eventRepository.save(any(Event.class))).thenReturn(event);

        Event result = eventService.createEvent(event);

        assertNotNull(result);
        assertEquals("Test Event", result.getName());
        verify(eventRepository, times(1)).save(event);
    }

    @Test
    void testUpdateEvent() {
        Long id = 1L;
        Event existingEvent = new Event();
        existingEvent.setId(id);
        existingEvent.setName("Old Event");

        Event updatedEventDetails = new Event();
        updatedEventDetails.setName("Updated Event");

        when(eventRepository.findById(id)).thenReturn(Optional.of(existingEvent));
        when(eventRepository.save(any(Event.class))).thenReturn(updatedEventDetails);

        Event result = eventService.updateEvent(id, updatedEventDetails);

        assertNotNull(result);
        assertEquals("Updated Event", result.getName());
        verify(eventRepository, times(1)).findById(id);
        verify(eventRepository, times(1)).save(existingEvent);
    }

    @Test
    void testUpdateEventNotFound() {
        Long id = 1L;
        when(eventRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> eventService.updateEvent(id, new Event()));
    }

    @Test
    void testDeleteEvent() {
        Long id = 1L;
        doNothing().when(eventRepository).deleteById(id);

        eventService.deleteEvent(id);

        verify(eventRepository, times(1)).deleteById(id);
    }

    @Test
    void testGetEventDetails() {
        Long id = 1L;
        Event event = new Event();
        event.setId(id);
        when(eventRepository.findById(id)).thenReturn(Optional.of(event));

        Optional<Event> result = eventService.getEventDetails(id);

        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
        verify(eventRepository, times(1)).findById(id);
    }

    @Test
    void testSearchEvents() {
        LocalDateTime date = LocalDateTime.now();
        String location = "Test Location";
        String keyword = "Test Keyword";
        List<Event> events = Arrays.asList(new Event(), new Event());

        when(eventRepository.searchEvents(date, location, keyword)).thenReturn(events);

        List<Event> result = eventService.searchEvents(date, location, keyword);

        assertEquals(2, result.size());
        verify(eventRepository, times(1)).searchEvents(date, location, keyword);
    }

    @Test
    void testGetTeamAndValuesInfo() {
        String result = eventService.getTeamAndValuesInfo();

        assertNotNull(result);
        assertTrue(result.contains("Notre équipe"));
        assertTrue(result.contains("Nos valeurs"));
    }

    @Test
    void testGetEventContacts() {
        Long eventId = 1L;
        Event event = new Event();
        event.setId(eventId);
        List<Contact> contacts = Arrays.asList(new Contact(), new Contact());
        event.setContacts(contacts);

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        List<Contact> result = eventService.getEventContacts(eventId);

        assertEquals(2, result.size());
        verify(eventRepository, times(1)).findById(eventId);
    }

    @Test
    void testGetEventContactsEventNotFound() {
        Long eventId = 1L;
        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> eventService.getEventContacts(eventId));
    }
}