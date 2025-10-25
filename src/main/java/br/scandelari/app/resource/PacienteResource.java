package br.scandelari.app.resource;

import br.scandelari.app.model.Paciente;
import br.scandelari.app.model.Person;
import br.scandelari.app.model.caches.PersonCache;
import br.scandelari.app.model.events.AddPersonEvent;
import br.scandelari.app.services.PacienteService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/paciente")
@RequestScoped
public class PacienteResource {

    @Inject
    private PacienteService pacienteService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Paciente getPaciente(@PathParam("id") Long id) {

        return pacienteService.findById(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Paciente> getPacientes()
    {
        return pacienteService.findAll();
    }

//    @POST
//    @Produces(MediaType.TEXT_PLAIN)
//    @Consumes(MediaType.APPLICATION_JSON)
//    public void handlePersonRequest(Person person) {
//        addPersonEventEvent.fire(new AddPersonEvent(person));
//    }
}
