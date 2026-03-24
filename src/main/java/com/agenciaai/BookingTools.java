package com.agenciaai;

import dev.langchain4j.agent.tool.Tool;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
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
        Cancela uma reserva existente com base no seu ID (bookingId). O usuário deve estar autenticado.
     """)
    public String cancelBooking(long bookingId){
        return bookingService.cancelBooking(bookingId)
                .map(b -> "Reserva "+b.id()+" cancelada com sucesso")
                .orElse("Não foi possível cancelar a reserva. Verifique se o ID da reserva esta correto ou se você tem permissão");
    }

    @Tool("""
            Lista de pacotes de viagem disponíveis para uma determinada categoria (ex de opções: ADVENTURE, TREASURE)
            Se um pacote possui no titulo a categoria ADVENTURE (AVENTURA) então ela não é um Tesouro e vice versa
            Sempre traca apenas o que foi pedido, não adapte titulos
            """)
    public String listPackagesByCategory(Category category){
        List<Booking> packages = bookingService.findPackagesByCategory(category);
        if (packages.isEmpty()){
            return "Nenhum pacote encontrado para a categoria";
        }

        return "Pacotes encontrados para a categoria "+category+": "+packages.stream().map(Booking::destination).toList().toString();
    }
}
