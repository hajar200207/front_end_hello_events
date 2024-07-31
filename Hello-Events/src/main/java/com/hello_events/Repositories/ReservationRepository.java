

package com.hello_events.Repositories;

import com.hello_events.Entites.Reservation;
import com.hello_events.Entites.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUser(User user);
}
