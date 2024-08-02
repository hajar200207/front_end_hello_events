import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { EventService } from '../event.service';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-event',
  templateUrl: './create-event.component.html',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  styleUrls: ['./create-event.component.scss']
})
export class EventCreateComponent implements OnInit {
  eventForm!: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private eventService: EventService,
    private router: Router
  ) {}

  ngOnInit() {
    this.eventForm = this.formBuilder.group({
      name: ['', Validators.required],
      dateTime: ['', Validators.required],
      location: ['', Validators.required],
      description: ['', Validators.required]
    });
  }

  onSubmit() {
    if (this.eventForm.valid) {
      this.eventService.createEvent(this.eventForm.value).subscribe(
        response => {
          console.log('Event created successfully', response);
          this.router.navigate(['/events']);
        },
        error => {
          console.error('Error creating event', error);
        }
      );
    }
  }
}
