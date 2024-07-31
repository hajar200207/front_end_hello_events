package com.hello_events.Controleurs;

import com.hello_events.Entites.Contact;
import com.hello_events.Entites.Event;
import com.hello_events.Services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200")

@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        Event createdEvent = eventService.createEvent(event);
        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody Event eventDetails) {
        Event updatedEvent = eventService.updateEvent(id, eventDetails);
        return ResponseEntity.ok(updatedEvent);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getEventDetails(@PathVariable Long id) {
        return eventService.getEventDetails(id)
                .map(event -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("location", event.getLocation());
                    map.put("description", event.getDescription());
                    return ResponseEntity.ok(map);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('USER')  or  hasRole('ADMIN')" )
    @GetMapping("/search")
    public ResponseEntity<List<Event>> searchEvents(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String keyword) {
        List<Event> events = eventService.searchEvents(date, location, keyword);
        return ResponseEntity.ok(events);
    }
    @PreAuthorize("hasRole('USER')  or  hasRole('ADMIN')" )
    @GetMapping("/about")
    public ResponseEntity<String> getTeamAndValuesInfo() {
        String info = eventService.getTeamAndValuesInfo();
        return ResponseEntity.ok(info);
    }
    @PreAuthorize("hasRole('USER')  or  hasRole('ADMIN')" )
    @GetMapping("/{id}/contacts")
    public ResponseEntity<List<Contact>> getEventContacts(@PathVariable Long id) {
        List<Contact> contacts = eventService.getEventContacts(id);
        return ResponseEntity.ok(contacts);
    }
    @PreAuthorize("hasRole('USER')  or  hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAllEvents() {
        List<Event> events = eventService.getAllEvents();
        List<Map<String, Object>> simplifiedEvents = events.stream()
                .map(event -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", event.getId());
                    map.put("name", event.getName());
                    map.put("dateTime", event.getDateTime().toString());
                    map.put("location", event.getLocation());
                    return map;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(simplifiedEvents);
    }

}