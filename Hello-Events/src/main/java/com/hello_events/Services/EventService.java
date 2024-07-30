package com.hello_events.Services;

import com.hello_events.Entites.Contact;
import com.hello_events.Entites.Event;
import com.hello_events.Repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    public Event updateEvent(Long id, Event eventDetails) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Événement non trouvé avec l'id : " + id));

        event.setName(eventDetails.getName());
        event.setDescription(eventDetails.getDescription());
        event.setDateTime(eventDetails.getDateTime());
        event.setLocation(eventDetails.getLocation());

        return eventRepository.save(event);
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    public Optional<Event> getEventDetails(Long id) {
        return eventRepository.findById(id);
    }


    public List<Event> searchEvents(LocalDateTime date, String location, String keyword) {
        return eventRepository.searchEvents(date, location, keyword);
    }

    public String getTeamAndValuesInfo() {

        return "Notre équipe est composée de professionnels passionnés par l'organisation d'événements. " +
                "Nos valeurs principales sont l'innovation, la qualité et la satisfaction client.";
    }

    public List<Contact> getEventContacts(Long eventId) {
        Optional<Event> event = eventRepository.findById(eventId);
        if (event.isPresent()) {
            return event.get().getContacts();
        } else {
            throw new RuntimeException("Événement non trouvé avec l'id : " + eventId);
        }
    }

}