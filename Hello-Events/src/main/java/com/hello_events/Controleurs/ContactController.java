package com.hello_events.Controleurs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.hello_events.Entites.Contact;
import com.hello_events.Services.ContactService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Contact createContact(@RequestParam String name, @RequestParam String email, @RequestParam String subject, @RequestParam String message) {
        return contactService.saveContact(name, email, subject, message);
    }
    @PreAuthorize("hasRole('USER')  or  hasRole('ADMIN')" )
    @GetMapping
    public List<Contact> getAllContacts() {
        return contactService.getAllContacts();
    }

    @PreAuthorize("hasRole('USER')  or  hasRole('ADMIN')" )
    @GetMapping("/{id}")
    public Optional<Contact> getContactById(@PathVariable Long id) {
        return contactService.getContactById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/status")
    public Contact updateContactStatus(@PathVariable Long id, @RequestParam Contact.ContactStatus status) {
        return contactService.updateContactStatus(id, status);

    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteContact(@PathVariable Long id) {
        contactService.deleteContact(id);
    }
}
