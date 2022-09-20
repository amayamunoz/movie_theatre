package com.jpmc.theater;

public class Reservation {
    private Showing showing;
    private int audienceCount;

    public Reservation(Showing showing, int audienceCount) {
        this.showing = showing;
        this.audienceCount = audienceCount;
    }

    public double totalFee() {
        return showing.getMovieFee() * audienceCount;
    }
}