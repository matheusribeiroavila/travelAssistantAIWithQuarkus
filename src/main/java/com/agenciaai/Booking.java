package com.agenciaai;

import java.time.LocalDate;

public record Booking(Long id, String customerName, String destination, LocalDate startDate, LocalDate endDate, BookinStatus status){}
