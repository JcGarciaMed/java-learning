package pe.forjix.leccion09;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class TrackingHistoryTest {

    private TrackingHistory trackingHistory;

    @BeforeEach
    void setUp() {
        trackingHistory = new TrackingHistory("EV-001");
    }

    @Test
    void assertCurrentStatus() {
        trackingHistory.addEvent(new TrackingEvent("EV-001", ShipmentStatus.CREATED, "PE"));
        assertEquals(trackingHistory.currentStatus(), ShipmentStatus.CREATED);
    }

    @Test
    void assertInvalidStatus() {
        trackingHistory.addEvent(new TrackingEvent("EV-001", ShipmentStatus.CREATED, "AR"));
        assertThrows(IllegalArgumentException.class,
                () -> trackingHistory.addEvent(new TrackingEvent("EV-003", ShipmentStatus.DELIVERED, "PE")));
    }


}