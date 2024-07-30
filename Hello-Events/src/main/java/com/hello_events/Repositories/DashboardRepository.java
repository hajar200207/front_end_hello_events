package com.hello_events.Repositories;

import com.hello_events.Entites.Dashboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface DashboardRepository extends JpaRepository<Dashboard, Long> {

    @Query("SELECT COUNT(u) FROM User u")
    int countTotalUsers();

    @Query("SELECT COUNT(e) FROM Event e")
    int countTotalEvents();

    @Query("SELECT COUNT(r) FROM Reservation r")
    int countTotalReservations();

    @Query("SELECT SUM(r.numberOfTickets * e.price) FROM Reservation r JOIN r.event e")
    double calculateTotalRevenue();

    @Query("SELECT MAX(r.lastUpdated) FROM Reservation r")
    LocalDateTime findLastUpdated();
}
