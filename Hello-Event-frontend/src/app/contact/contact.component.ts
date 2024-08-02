import { Component, OnInit } from '@angular/core';
import { Contact, ContactStatus } from '../contact.model';
import { ContactService } from '../contact.service';
import { HttpHeaders } from '@angular/common/http';
import { AuthService } from '../auth.service'; // Correction ici : AuthService avec "A" majuscule

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css']
})
export class ContactComponent implements OnInit {
  contacts: Contact[] = [];
  newContact: Contact = { name: '', email: '', subject: '', message: '' };
  selectedContact: Contact | null = null;
  contactStatuses = ContactStatus; // Utilisation directe de l'énumération dans le template
  currentView: string = 'contacts'; // Définir et initialiser une valeur par défaut

  constructor(private contactService: ContactService, private authService: AuthService) {} // Correction ici : AuthService avec "A" majuscule

  ngOnInit(): void {
    this.loadContacts();
  }

  loadContacts(): void {
    this.contactService.getAllContacts().subscribe(
      contacts => this.contacts = contacts,
      error => console.error('Error loading contacts', error)
    );
  }

  createContact(): void {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.authService.getToken()}`
    });

    this.contactService.createContact(this.newContact, { headers }).subscribe(
      contact => {
        this.contacts.push(contact);
        this.newContact = { name: '', email: '', subject: '', message: '' };
      },
      error => {
        console.error('Error creating contact', error);
      }
    );
  }



  updateContactStatus(contact: Contact, status: ContactStatus): void {
    if (contact.id !== undefined) {
      this.contactService.updateContactStatus(contact.id, status).subscribe(
        updatedContact => {
          const index = this.contacts.findIndex(c => c.id === contact.id);
          if (index !== -1) {
            this.contacts[index] = updatedContact;
          }
        },
        error => console.error('Error updating contact status', error)
      );
    } else {
      console.error('Contact ID is undefined');
    }
  }

  deleteContact(id: number | undefined): void {
    if (typeof id === 'number') {
      this.contactService.deleteContact(id).subscribe(
        () => this.contacts = this.contacts.filter(contact => contact.id !== id),
        error => console.error('Error deleting contact', error)
      );
    } else {
      console.error('Invalid contact id');
    }
  }

  selectContact(contact: Contact): void {
    this.selectedContact = contact;
  }

  deselectContact(): void {
    this.selectedContact = null;
  }
}
