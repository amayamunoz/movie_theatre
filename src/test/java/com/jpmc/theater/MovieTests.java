package com.jpmc.theater;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MovieTests {
    private LocalDateTime showStartDateTime_noDiscount = LocalDateTime.of(LocalDate.parse("2017-02-06"), LocalTime.parse("10:00:00"));
    private LocalDateTime showStartDateTime_daySeven = LocalDateTime.of(LocalDate.parse("2017-02-07"), LocalTime.parse("10:00:00"));
    
    @Test
    void normalMovie_noDiscounts() {
        Movie encanto = new Movie("Encanto", Duration.ofMinutes(61),10, 0);
        Showing showing = new Showing(encanto, 10, showStartDateTime_noDiscount);
        assertEquals(10, encanto.calculateTicketPrice(showing));
    }

    @Test
    void specialMovie_returns20PercentDiscount() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90),12.5, 1);
        Showing showing = new Showing(spiderMan, 5, showStartDateTime_noDiscount);
        assertEquals(10, spiderMan.calculateTicketPrice(showing));
    }

    @Test
    void movieAt11AM_returns25PercentDiscount() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90),20, 1);
        Showing showing = new Showing(spiderMan, 5, LocalDateTime.of(LocalDate.parse("2017-02-06"), LocalTime.parse("11:00:00")));
        assertEquals(15, spiderMan.calculateTicketPrice(showing));
    }

    @Test
    void movieOn7thDay_returns1DollarDiscount() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90),10, 0);
        Showing showing = new Showing(spiderMan, 5, showStartDateTime_daySeven);
        assertEquals(9, spiderMan.calculateTicketPrice(showing));
    }

    @Test
    void firstMovieOfDay_returns3DollarDiscount() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90),10, 0);
        Showing showing = new Showing(spiderMan, 1, showStartDateTime_noDiscount);
        assertEquals(7, spiderMan.calculateTicketPrice(showing));
    }

    @Test
    void secondMovieOfDay_returns2DollarDiscount() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90),10, 0);
        Showing showing = new Showing(spiderMan, 2, showStartDateTime_noDiscount);
        assertEquals(8, spiderMan.calculateTicketPrice(showing));
    }

    @Test
    void firstMovieOfDay_onThe7th_returns3DollarDiscount() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90),10, 0);
        Showing showing = new Showing(spiderMan, 1, showStartDateTime_daySeven);
        assertEquals(7, spiderMan.calculateTicketPrice(showing));
    }

    @Test
    void specialMovie_firstMovieOfDay_returns20PercentDiscount() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90),25, 1);
        Showing showing = new Showing(spiderMan, 1, showStartDateTime_noDiscount);
        assertEquals(20, spiderMan.calculateTicketPrice(showing));
    }

    @Test
    void specialMovie_firstMovieOfDay_returns3DollarDiscount() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90),5, 1);
        Showing showing = new Showing(spiderMan, 1, showStartDateTime_noDiscount);
        assertEquals(2, spiderMan.calculateTicketPrice(showing));
    }

    @Test
    void specialMovie_secondMovieOfDay_returns2DollarDiscount() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90),5, 1);
        Showing showing = new Showing(spiderMan, 2, showStartDateTime_noDiscount);
        assertEquals(3, spiderMan.calculateTicketPrice(showing));
    }

    @Test
    void specialMovie_secondMovieOfDay_onThe7th_showingAt4_returns25PercentDiscount() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90),20, 1);
        Showing showing = new Showing(spiderMan, 2, LocalDateTime.of(LocalDate.parse("2017-02-07"), LocalTime.parse("16:00:00")));
        assertEquals(15, spiderMan.calculateTicketPrice(showing));
    }
}
