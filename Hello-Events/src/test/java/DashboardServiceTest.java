
import com.hello_events.Entites.Dashboard;
import com.hello_events.Repositories.DashboardRepository;
import com.hello_events.Services.DashboardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DashboardServiceTest {

    @Mock
    private DashboardRepository dashboardRepository;

    @InjectMocks
    private DashboardService dashboardService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetDashboardMetrics() {
        // Arrange
        long totalUsers = 100;
        long totalEvents = 50;
        long totalReservations = 200;
        double totalRevenue = 5000.0;
        LocalDateTime lastUpdated = LocalDateTime.now();

        when(dashboardRepository.countTotalUsers()).thenReturn((int) totalUsers);
        when(dashboardRepository.countTotalEvents()).thenReturn((int) totalEvents);
        when(dashboardRepository.countTotalReservations()).thenReturn((int) totalReservations);
        when(dashboardRepository.calculateTotalRevenue()).thenReturn(totalRevenue);
        when(dashboardRepository.findLastUpdated()).thenReturn(lastUpdated);

        // Act
        Dashboard result = dashboardService.getDashboardMetrics();

        // Assert
        assertNotNull(result);
        assertEquals(totalUsers, result.getTotalUsers());
        assertEquals(totalEvents, result.getTotalEvents());
        assertEquals(totalReservations, result.getTotalReservations());
        assertEquals(totalRevenue, result.getTotalRevenue());
        assertEquals(lastUpdated, result.getLastUpdated());

        // Verify that all repository methods were called
        verify(dashboardRepository, times(1)).countTotalUsers();
        verify(dashboardRepository, times(1)).countTotalEvents();
        verify(dashboardRepository, times(1)).countTotalReservations();
        verify(dashboardRepository, times(1)).calculateTotalRevenue();
        verify(dashboardRepository, times(1)).findLastUpdated();
    }
}