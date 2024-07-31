

import com.hello_events.Entites.Contact;
import com.hello_events.Repositories.ContactRepository;
import com.hello_events.Services.ContactService;
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

class ContactServiceTest {

    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    private ContactService contactService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveContact() {
        String name = "chaimae hajar";
        String email = "chaimaehajar@example.com";
        String subject = "Test Subject";
        String message = "Test Message";

        Contact savedContact = new Contact();
        savedContact.setId(1L);
        savedContact.setName(name);
        savedContact.setEmail(email);
        savedContact.setSubject(subject);
        savedContact.setMessage(message);
        savedContact.setSubmissionTime(LocalDateTime.now());
        savedContact.setStatus(Contact.ContactStatus.NEW);

        when(contactRepository.save(any(Contact.class))).thenReturn(savedContact);

        Contact result = contactService.saveContact(name, email, subject, message);

        assertNotNull(result);
        assertEquals(name, result.getName());
        assertEquals(email, result.getEmail());
        assertEquals(subject, result.getSubject());
        assertEquals(message, result.getMessage());
        assertEquals(Contact.ContactStatus.NEW, result.getStatus());
        assertNotNull(result.getSubmissionTime());

        verify(contactRepository, times(1)).save(any(Contact.class));
    }

    @Test
    void testGetAllContacts() {
        List<Contact> contacts = Arrays.asList(
                new Contact(), new Contact()
        );
        when(contactRepository.findAll()).thenReturn(contacts);

        List<Contact> result = contactService.getAllContacts();

        assertEquals(2, result.size());
        verify(contactRepository, times(1)).findAll();
    }

    @Test
    void testGetContactById() {
        Long id = 1L;
        Contact contact = new Contact();
        contact.setId(id);
        when(contactRepository.findById(id)).thenReturn(Optional.of(contact));

        Optional<Contact> result = contactService.getContactById(id);

        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
        verify(contactRepository, times(1)).findById(id);
    }

    @Test
    void testUpdateContactStatus() {
        Long id = 1L;
        Contact contact = new Contact();
        contact.setId(id);
        contact.setStatus(Contact.ContactStatus.NEW);

        when(contactRepository.findById(id)).thenReturn(Optional.of(contact));
        when(contactRepository.save(any(Contact.class))).thenReturn(contact);

        Contact result = contactService.updateContactStatus(id, Contact.ContactStatus.PROCESSED);

        assertNotNull(result);
        assertEquals(Contact.ContactStatus.PROCESSED, result.getStatus());
        verify(contactRepository, times(1)).findById(id);
        verify(contactRepository, times(1)).save(contact);
    }

    @Test
    void testUpdateContactStatusNotFound() {
        Long id = 1L;
        when(contactRepository.findById(id)).thenReturn(Optional.empty());

        Contact result = contactService.updateContactStatus(id, Contact.ContactStatus.PROCESSED);

        assertNull(result);
        verify(contactRepository, times(1)).findById(id);
        verify(contactRepository, never()).save(any(Contact.class));
    }

    @Test
    void testDeleteContact() {
        Long id = 1L;
        doNothing().when(contactRepository).deleteById(id);

        contactService.deleteContact(id);

        verify(contactRepository, times(1)).deleteById(id);
    }
}