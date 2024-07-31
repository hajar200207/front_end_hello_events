package com.hello_events.Repositories;

import com.hello_events.Entites.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    // Additional query methods if needed
}
