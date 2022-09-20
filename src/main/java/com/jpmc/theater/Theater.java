package com.jpmc.theater;

import java.text.NumberFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Theater {

    LocalDateProvider provider;
    private List<Showing> schedule;

    public Theater(LocalDateProvider provider, List<Showing> schedule) {
        this.provider = provider;
        this.schedule = schedule;
    }

    public Reservation reserve(int sequence, int howManyTickets) {
        Showing showing;
        try {
            showing = schedule.get(sequence - 1);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            throw new IllegalStateException("Not able to find showing for given sequence " + sequence);
        }
        return new Reservation(showing, howManyTickets);
    }

    public String printScheduleJson() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.findAndRegisterModules();
            System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(schedule));
            return objectMapper.writeValueAsString(schedule);
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String printSchedule() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("h:mm a");
        NumberFormat numberFormatter = NumberFormat.getCurrencyInstance();
        StringBuilder str = new StringBuilder();
        str.append(provider.currentDate() + "\n");
        str.append("===================================================\n");
        schedule.forEach(s ->
            str.append(s.getSequenceOfTheDay() + ": " + s.getShowStartTime().format(dateFormatter) + " " + s.getMovie().getTitle() + " " + humanReadableDurationString(s.getMovie().getRunningTime()) + " " + numberFormatter.format(s.getMovieFee()) + "\n")
        );
        str.append("===================================================");
        System.out.println(str.toString());
        return str.toString();
    }

    private String humanReadableDurationString(Duration duration) {
        long hour = duration.toHours();
        long remainingMin = duration.toMinutes() - TimeUnit.HOURS.toMinutes(duration.toHours());

        return String.format("(%s hour%s %s minute%s)", hour, handlePlural(hour), remainingMin, handlePlural(remainingMin));
    }

    // (s) postfix should be added to handle plural correctly
    private String handlePlural(long value) {
        if (value == 1) {
            return "";
        } else {
            return "s";
        }
    }

    public static void main(String[] args) {
        LocalDateProvider provider = LocalDateProvider.singleton();
        Movie spiderMan = new Movie("Spider-Man: No Way Home", Duration.ofMinutes(90), 12.5, 1);
        Movie turningRed = new Movie("Turning Red", Duration.ofMinutes(85), 11, 0);
        Movie theBatMan = new Movie("The Batman", Duration.ofMinutes(95), 9, 0);
        Movie encanto = new Movie("Encanto", Duration.ofMinutes(61), 10, 0);
        List<Showing> schedule = List.of(
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
        Theater theater = new Theater(provider, schedule);
        theater.printSchedule();
    }
}
