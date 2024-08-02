package com.hello_events.Controleurs;

import com.hello_events.DTO.ContactDTO;
import com.hello_events.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.hello_events.Entites.Contact;
import com.hello_events.Services.ContactService;

import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "http://localhost:4200")

@RestController
@RequestMapping("/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;
    @PreAuthorize("hasRole('USER')")
    @PostMapping
    public ResponseEntity<Contact> createContact(
            @Valid @RequestBody ContactDTO contactDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Contact contact = contactService.saveContact(contactDTO.getName(), contactDTO.getEmail(), contactDTO.getSubject(), contactDTO.getMessage());
        return new ResponseEntity<>(contact, HttpStatus.CREATED);
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


    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/{id}/status")
    public ResponseEntity<Contact> updateContactStatus(
            @PathVariable Long id,
            @RequestParam("status") Contact.ContactStatus status) {
        try {
            Contact updatedContact = contactService.updateContactStatus(id, status);
            return new ResponseEntity<>(updatedContact, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PreAuthorize("hasRole('USER')  or  hasRole('ADMIN')" )
    @DeleteMapping("/{id}")
    public void deleteContact(@PathVariable Long id) {
        contactService.deleteContact(id);
    }
}
