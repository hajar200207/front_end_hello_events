

package com.hello_events.Entites;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.Id;


import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Event {

    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private LocalDateTime dateTime;
    private String location;
    private double price;

    @OneToMany(mappedBy = "event")
    private List<Reservation> reservations;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "event_id")
    private List<Contact> contacts;
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
