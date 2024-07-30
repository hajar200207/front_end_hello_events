package com.hello_events.Services;

import com.hello_events.Entites.Dashboard;
import com.hello_events.Repositories.DashboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DashboardService {

    @Autowired
    private DashboardRepository dashboardRepository;

    public Dashboard getDashboardMetrics() {
        Dashboard dashboard = new Dashboard();
        dashboard.setTotalUsers(dashboardRepository.countTotalUsers());
        dashboard.setTotalEvents(dashboardRepository.countTotalEvents());
        dashboard.setTotalReservations(dashboardRepository.countTotalReservations());
        dashboard.setTotalRevenue(dashboardRepository.calculateTotalRevenue());
        dashboard.setLastUpdated(dashboardRepository.findLastUpdated());
        return dashboard;
    }
}
