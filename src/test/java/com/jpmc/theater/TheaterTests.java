package com.jpmc.theater;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.lang.IllegalStateException;

public class TheaterTests {

    private List<Showing> defaultSchedule = getDefaultMovieSchedule();

    @Test
    void reserve_returnsCorrectTotalFee() {
        Theater theater = new Theater(LocalDateProvider.singleton(), defaultSchedule);
        Reservation reservation = theater.reserve(3, 4);
        assertEquals(27, reservation.totalFee());
    }

    @Test
    void reserve_returnsCorrectReservationObject() {
        Theater theater = new Theater(LocalDateProvider.singleton(), defaultSchedule);
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 12.5, 1);
        Reservation reservation = theater.reserve(5, 5);
        Reservation expected = new Reservation(new Showing(spiderMan, 5, LocalDateTime.of(LocalDateProvider.singleton().currentDate(), LocalTime.of(16, 10))), 5);
        assertEquals(expected.getAudienceCount(), reservation.getAudienceCount());
        assertEquals(expected.getShowing().getMovie(), reservation.getShowing().getMovie());
        assertEquals(expected.getShowing().getMovieFee(), reservation.getShowing().getMovieFee());
        assertEquals(expected.getShowing().getShowStartTime(), reservation.getShowing().getShowStartTime());
        assertEquals(expected.getShowing().getSequenceOfTheDay(), reservation.getShowing().getSequenceOfTheDay());
    }

    @Test
    void reserve_throwsErrorWhenShowingDoesNotExist() {
        Theater theater = new Theater(LocalDateProvider.singleton(), defaultSchedule);
        try {
            theater.reserve(11, 4);
        } catch (IllegalStateException e) {
            String expectedMessage = "Not able to find showing for given sequence 11";
            String actualMessage = e.getMessage();
        
            assertTrue(actualMessage.contains(expectedMessage));
        }
    }

    @Test
    void printMovieDefaultSchedule_printsCorrectly() {
        LocalDateProvider provider = LocalDateProvider.singleton();
        Theater theater = new Theater(provider, defaultSchedule);
        String expected = provider.currentDate() + "\n===================================================\n1: 9:00 AM Turning Red (1 hour 25 minutes) $8.00\n2: 11:00 AM Spider-Man: No Way Home (1 hour 30 minutes) $9.38\n3: 12:50 PM The Batman (1 hour 35 minutes) $6.75\n4: 2:30 PM Turning Red (1 hour 25 minutes) $8.25\n5: 4:10 PM Spider-Man: No Way Home (1 hour 30 minutes) $10.00\n6: 5:50 PM The Batman (1 hour 35 minutes) $9.00\n7: 7:30 PM Turning Red (1 hour 25 minutes) $11.00\n8: 9:10 PM Spider-Man: No Way Home (1 hour 30 minutes) $10.00\n9: 11:00 PM The Batman (1 hour 35 minutes) $9.00\n===================================================";
        assertEquals(expected, theater.printSchedule());
    }

    @Test
    void printMovieHandlePlural_printsCorrectly() {
        LocalDateProvider provider = LocalDateProvider.singleton();
        Theater theater = new Theater(provider, getScheduleSingular());
        String expected = provider.currentDate() + "\n===================================================\n1: 11:30 PM Encanto (1 hour 1 minute) $7.00\n===================================================";
        assertEquals(expected, theater.printSchedule());
    }

    @Test
    void printMovieDefaultScheduleJson_printsCorrectly() {
        LocalDateProvider provider = LocalDateProvider.singleton();
        Theater theater = new Theater(provider, getScheduleSingular());
        int day = provider.currentDate().getDayOfMonth();
        int month = provider.currentDate().getMonthValue();
        int year = provider.currentDate().getYear();
        String expected = "[{\"movie\":{\"title\":\"Encanto\",\"runningTime\":3660.000000000,\"ticketPrice\":10.0},\"sequenceOfTheDay\":1,\"showStartTime\":[" + year + "," + month + "," + day + ",23,30],\"movieFee\":7.0}]";
        assertEquals(expected, theater.printScheduleJson());
    }

    // helpers
    private List<Showing> getDefaultMovieSchedule() {
        LocalDateProvider provider = LocalDateProvider.singleton();
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 12.5, 1);
        Movie turningRed = new Movie("Turning Red", Duration.ofMinutes(85), 11, 0);
        Movie theBatMan = new Movie("The Batman", Duration.ofMinutes(95), 9, 0);
        List<Showing> schedule = List.of(
            new Showing(turningRed, 1, LocalDateTime.of(provider.currentDate(), LocalTime.of(9, 0))),
            new Showing(spiderMan, 2, LocalDateTime.of(provider.currentDate(), LocalTime.of(11, 0))),
            new Showing(theBatMan, 3, LocalDateTime.of(provider.currentDate(), LocalTime.of(12, 50))),
            new Showing(turningRed, 4, LocalDateTime.of(provider.currentDate(), LocalTime.of(14, 30))),
            new Showing(spiderMan, 5, LocalDateTime.of(provider.currentDate(), LocalTime.of(16, 10))),
            new Showing(theBatMan, 6, LocalDateTime.of(provider.currentDate(), LocalTime.of(17, 50))),
            new Showing(turningRed, 7, LocalDateTime.of(provider.currentDate(), LocalTime.of(19, 30))),
            new Showing(spiderMan, 8, LocalDateTime.of(provider.currentDate(), LocalTime.of(21, 10))),
            new Showing(theBatMan, 9, LocalDateTime.of(provider.currentDate(), LocalTime.of(23, 0)))
        );
        return schedule;
    }

    private List<Showing> getScheduleSingular() {
        LocalDateProvider provider = LocalDateProvider.singleton();
        Movie encanto = new Movie("Encanto", Duration.ofMinutes(61), 10, 0);
        List<Showing> schedule = List.of(
            new Showing(encanto, 1, LocalDateTime.of(provider.currentDate(), LocalTime.of(23, 30)))
        );
        return schedule;
    }
}
