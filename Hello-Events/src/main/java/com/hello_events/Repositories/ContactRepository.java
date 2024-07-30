package com.hello_events.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hello_events.Entites.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}
