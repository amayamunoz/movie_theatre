package com.jpmc.theater;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class Movie {
    final private static int MOVIE_CODE_SPECIAL = 1;
    final private static LocalTime firstCutoff = LocalTime.parse("11:00:00");
    final private static LocalTime secondCutoff = LocalTime.parse("16:00:00");

    private String title;
    private String description;
    private Duration runningTime;
    private double ticketPrice;
    private int specialCode;

    public Movie(String title, Duration runningTime, double ticketPrice, int specialCode) {
        this.title = title;
        this.runningTime = runningTime;
        this.ticketPrice = ticketPrice;
        this.specialCode = specialCode;
    }

    public String getTitle() {
        return title;
    }

    public Duration getRunningTime() {
        return runningTime;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public double calculateTicketPrice(Showing showing) {
        return ticketPrice - getDiscount(showing.getSequenceOfTheDay(), showing.getStartTime());
    }

    private double getDiscount(int showSequence, LocalDateTime showStartTime) {
        LocalTime showStart = showStartTime.toLocalTime();
        double percentDiscount = 0, dayDiscount = 0, sequenceDiscount = 0;
        if (MOVIE_CODE_SPECIAL == specialCode) {
            percentDiscount = ticketPrice * 0.2;  // 20% discount for special movie
        }

        if (showStart.isAfter(firstCutoff.minusMinutes(1)) && 
                showStart.isBefore(secondCutoff.plusMinutes(1))) {
                    percentDiscount = ticketPrice * 0.25; // 25% discount if show time in between 11 and 4
        }

        if (showStartTime.getDayOfMonth() == 7) {
            dayDiscount = 1; // $1 discount if the 7th of the month
        }

        if (showSequence == 1) {
            sequenceDiscount = 3; // $3 discount for 1st show
        } else if (showSequence == 2) {
            sequenceDiscount = 2; // $2 discount for 2nd show
        }

        // biggest discount wins
        if (percentDiscount > sequenceDiscount) {
            if (percentDiscount > dayDiscount) {
                return percentDiscount;
            } else {
                return dayDiscount;
            }
        } else {
            if (sequenceDiscount > dayDiscount) {
                return sequenceDiscount;
            } else {
                return dayDiscount;
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Double.compare(movie.ticketPrice, ticketPrice) == 0
                && Objects.equals(title, movie.title)
                && Objects.equals(description, movie.description)
                && Objects.equals(runningTime, movie.runningTime)
                && Objects.equals(specialCode, movie.specialCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, runningTime, ticketPrice, specialCode);
    }
}