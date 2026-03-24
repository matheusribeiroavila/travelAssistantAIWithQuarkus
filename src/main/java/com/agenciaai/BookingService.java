package com.agenciaai;

import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
public class BookingService {
    private final Map<Long, Booking> bookings = new HashMap<>();

    public BookingService(){
        bookings.put(12345L, new Booking(12345L, "Jhon Doe", "Aventura Amazônia", LocalDate.now().plusMonths(2), LocalDate.now().plusMonths(3).plusDays(10), BookinStatus.CONFIRMED, Category.ADVENTURE));
        bookings.put(56789L, new Booking(56789L, "Luna Miudinha", "Tesouros do Egito", LocalDate.now().plusMonths(2), LocalDate.now().plusMonths(3).plusDays(10), BookinStatus.PENDING, Category.TREASURES));
        bookings.put(56789L, new Booking(10112L, "Matheus Ávila", "Tesouros do Egito", LocalDate.now().plusMonths(2), LocalDate.now().plusMonths(3).plusDays(10), BookinStatus.PENDING, Category.TREASURES));
    }

    public List<Booking> findPackagesByCategory(Category category){
        return bookings.values().stream().filter(booking -> category.equals(booking.category())).toList();
    }

    public Optional<Booking> getBookingDatails(Long bookingId){
        return Optional.ofNullable(bookings.get(bookingId));
    }

    public Optional<Booking> cancelBooking(Long bookingId, String customerLastName){
        Booking bookingTarget = bookings.get(bookingId);

        if (bookingTarget.customerName().endsWith(customerLastName)){
            bookings.put(bookingTarget.id(), new Booking(bookingTarget.id(), bookingTarget.customerName(), bookingTarget.destination(), bookingTarget.startDate(), bookingTarget.endDate(), BookinStatus.CANCELLED, bookingTarget.category()));
            return Optional.of(bookings.get(bookingId));
        }

        return Optional.empty();

    }
}
