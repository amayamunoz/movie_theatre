package com.jpmc.theater;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class LocalDateProviderTests {
    @Test
    void instanceOfLocalDateProviderCanBeCreated() {
        LocalDateProvider provider = LocalDateProvider.singleton();
        assertNotNull(provider);
    }

    @Test
    void currentDate_ReturnsCorrectDate() {
        assertEquals(LocalDate.now(), LocalDateProvider.singleton().currentDate());
    }
}
