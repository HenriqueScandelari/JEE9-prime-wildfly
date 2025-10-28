package br.scandelari.app.resource;

import br.scandelari.app.model.Paciente;
import br.scandelari.app.services.PacienteService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.hibernate.Hibernate;
import org.hibernate.Transaction;

import java.util.List;

@Path("/paciente")
@RequestScoped
public class PacienteResource {

    @Inject
    private PacienteService pacienteService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    @Transactional
    public Paciente getPaciente(@PathParam("id") Long id) {
        Paciente paciente = pacienteService.findById(id);
        Hibernate.initialize(paciente.getMedicamentos());
        return  paciente;
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
