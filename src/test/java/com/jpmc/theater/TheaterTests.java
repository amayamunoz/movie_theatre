package com.jpmc.theater;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TheaterTests {

    @Test
    void totalFeeForCustomer() {
        Theater theater = new Theater(LocalDateProvider.singleton());
        Reservation reservation = theater.reserve(3, 4);
        assertEquals(27, reservation.totalFee());
    }

    @Test
    void printMovieSchedule() {
        LocalDateProvider provider = LocalDateProvider.singleton();
        Theater theater = new Theater(provider);
        String expected = provider.currentDate() + "\n===================================================\n1: 9:00 AM Turning Red (1 hour 25 minutes) $8.00\n2: 11:00 AM Spider-Man: No Way Home (1 hour 30 minutes) $9.38\n3: 12:50 PM The Batman (1 hour 35 minutes) $6.75\n4: 2:30 PM Turning Red (1 hour 25 minutes) $8.25\n5: 4:10 PM Spider-Man: No Way Home (1 hour 30 minutes) $10.00\n6: 5:50 PM The Batman (1 hour 35 minutes) $9.00\n7: 7:30 PM Turning Red (1 hour 25 minutes) $11.00\n8: 9:10 PM Spider-Man: No Way Home (1 hour 30 minutes) $10.00\n9: 11:00 PM The Batman (1 hour 35 minutes) $9.00\n10: 11:30 PM Encanto (1 hour 1 minute) $10.00\n===================================================";
        assertEquals(expected, theater.printSchedule());
    }

    @Test
    void printMovieScheduleJson() {
        Theater theater = new Theater(LocalDateProvider.singleton());
        theater.printScheduleJson();
    }
}
