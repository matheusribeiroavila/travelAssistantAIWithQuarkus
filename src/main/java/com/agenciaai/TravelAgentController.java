package com.agenciaai;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.awt.*;

@Path("/travel")
public class TravelAgentController {

    @Inject
    TravelAgentAssistant assistant;

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String ask(String question, @HeaderParam("X-User-Name") String userName){
        if (userName != null && !userName.isEmpty()){
            try {
                return assistant.chat(userName, question, userName);
            } finally {

            }
        } else {
            return "Usuário precisa estar autenticado!";
        }
    }
}
