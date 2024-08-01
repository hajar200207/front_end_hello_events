

package com.hello_events.Entites;
import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDateTime;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Event {


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
