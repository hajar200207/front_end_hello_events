package com.hello_events.DTO;

//import javax.validation.constraints.Email;
import com.hello_events.Entites.Contact;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    @NotBlank
    private String subject;

    @NotBlank
    private String message;
    private Contact.ContactStatus status;
}
