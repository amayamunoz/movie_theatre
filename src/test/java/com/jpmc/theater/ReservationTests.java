package com.jpmc.theater;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReservationTests {

    @Test
    void specialMovieDiscount_ReturnsCorrectTotalFee() {
        var showing = new Showing(
                new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 12.5, 1),
                5,
                LocalDateTime.now()
        );
        assertEquals(30.0, new Reservation(showing, 3).totalFee());
    }
}
