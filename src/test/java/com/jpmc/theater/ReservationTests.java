package com.jpmc.theater;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReservationTests {
    private LocalDateTime showStartDateTime_noDiscount = LocalDateTime.of(LocalDate.parse("2017-02-06"), LocalTime.parse("10:00:00"));

    @Test
    void noMovieDiscount_ReturnsCorrectTotalFee() {
        var showing = new Showing(
                new Movie("Soul", Duration.ofMinutes(70), 15, 0),
                10,
                showStartDateTime_noDiscount
        );
        assertEquals(30.0, new Reservation(showing, 2).totalFee());
    }
    
    @Test
    void specialMovieDiscount_ReturnsCorrectTotalFee() {
        var showing = new Showing(
                new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 12.5, 1),
                5,
                showStartDateTime_noDiscount
        );
        assertEquals(30.0, new Reservation(showing, 3).totalFee());
    }

    @Test
    void firstMovieDiscount_ReturnsCorrectTotalFee() {
        var showing = new Showing(
                new Movie("Soul", Duration.ofMinutes(70), 10, 0),
                1,
                showStartDateTime_noDiscount
        );
        assertEquals(49.0, new Reservation(showing, 7).totalFee());
    }
}
