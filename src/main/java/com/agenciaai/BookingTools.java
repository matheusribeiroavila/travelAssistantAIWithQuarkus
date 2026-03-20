package com.agenciaai;

import dev.langchain4j.agent.tool.Tool;
import jakarta.inject.Inject;

public class BookingTools {

    @Inject
    BookingService bookingService;

    @Tool("Obtém os detalhes completos de uma reserva com base em seu número de identificação (bookingId).")
    public String getBookingDetails(long bookingId){
        return bookingService.getBookingDatails(bookingId)
                .map(Booking::toString)
                .orElse("Reserva com ID "+bookingId+" não encontrada.");
    }

    @Tool("""
        Cancela uma reserva existente.
        Para confirmar o cancelamento, é necessário fornecer o ID da raserva (bookingId)
        e o último nome do cliente (customerLastName).
     """)
    public String cancelBooking(long bookingId, String customerLastName){
        return bookingService.cancelBooking(bookingId, customerLastName)
                .map(Booking::toString)
                .orElse("Não foi possível cancelar a reserva. Verifique se o ID da reserva ou o sobrenome do cliente está preenchido corretamente.");
    }
}
