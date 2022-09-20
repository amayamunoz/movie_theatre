package com.jpmc.theater;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class TheaterTests {

    private LocalDateProvider provider = LocalDateProvider.singleton();
    private Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 12.5, 1);
    private Movie turningRed = new Movie("Turning Red", Duration.ofMinutes(85), 11, 0);
    private Movie theBatMan = new Movie("The Batman", Duration.ofMinutes(95), 9, 0);
    private Movie encanto = new Movie("Encanto", Duration.ofMinutes(61), 10, 0);
    private List<Showing> defaultSchedule = List.of(
        new Showing(turningRed, 1, LocalDateTime.of(provider.currentDate(), LocalTime.of(9, 0))),
        new Showing(spiderMan, 2, LocalDateTime.of(provider.currentDate(), LocalTime.of(11, 0))),
        new Showing(theBatMan, 3, LocalDateTime.of(provider.currentDate(), LocalTime.of(12, 50))),
        new Showing(turningRed, 4, LocalDateTime.of(provider.currentDate(), LocalTime.of(14, 30))),
        new Showing(spiderMan, 5, LocalDateTime.of(provider.currentDate(), LocalTime.of(16, 10))),
        new Showing(theBatMan, 6, LocalDateTime.of(provider.currentDate(), LocalTime.of(17, 50))),
        new Showing(turningRed, 7, LocalDateTime.of(provider.currentDate(), LocalTime.of(19, 30))),
        new Showing(spiderMan, 8, LocalDateTime.of(provider.currentDate(), LocalTime.of(21, 10))),
        new Showing(theBatMan, 9, LocalDateTime.of(provider.currentDate(), LocalTime.of(23, 0))),
        new Showing(encanto, 10, LocalDateTime.of(provider.currentDate(), LocalTime.of(23, 30)))
    );

    @Test
    void totalFeeForCustomer() {
        Theater theater = new Theater(LocalDateProvider.singleton(), defaultSchedule);
        Reservation reservation = theater.reserve(3, 4);
        assertEquals(27, reservation.totalFee());
    }

    @Test
    void printMoviedefaultSchedule() {
        LocalDateProvider provider = LocalDateProvider.singleton();
        Theater theater = new Theater(provider, defaultSchedule);
        String expected = provider.currentDate() + "\n===================================================\n1: 9:00 AM Turning Red (1 hour 25 minutes) $8.00\n2: 11:00 AM Spider-Man: No Way Home (1 hour 30 minutes) $9.38\n3: 12:50 PM The Batman (1 hour 35 minutes) $6.75\n4: 2:30 PM Turning Red (1 hour 25 minutes) $8.25\n5: 4:10 PM Spider-Man: No Way Home (1 hour 30 minutes) $10.00\n6: 5:50 PM The Batman (1 hour 35 minutes) $9.00\n7: 7:30 PM Turning Red (1 hour 25 minutes) $11.00\n8: 9:10 PM Spider-Man: No Way Home (1 hour 30 minutes) $10.00\n9: 11:00 PM The Batman (1 hour 35 minutes) $9.00\n10: 11:30 PM Encanto (1 hour 1 minute) $10.00\n===================================================";
        assertEquals(expected, theater.printSchedule());
    }

    @Test
    void printMoviedefaultScheduleJson() {
        Theater theater = new Theater(LocalDateProvider.singleton(), defaultSchedule);
        theater.printScheduleJson();
    }
}
