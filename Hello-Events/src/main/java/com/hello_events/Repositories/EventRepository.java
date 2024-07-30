package com.hello_events.Repositories;

import com.hello_events.Entites.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByDateTimeBetween(LocalDateTime start, LocalDateTime end);
    List<Event> findByLocation(String location);
    List<Event> findByNameContainingIgnoreCase(String keyword);

    @Query("SELECT e FROM Event e WHERE " +
            "(:date IS NULL OR e.dateTime >= :date) AND " +
            "(:location IS NULL OR e.location = :location) AND " +
            "(:keyword IS NULL OR LOWER(e.name) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<Event> searchEvents(@Param("date") LocalDateTime date,
                             @Param("location") String location,
                             @Param("keyword") String keyword);

}