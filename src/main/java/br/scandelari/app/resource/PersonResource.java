package br.scandelari.app.resource;

import br.scandelari.app.model.Person;
import br.scandelari.app.model.caches.PersonCache;
import br.scandelari.app.model.events.AddPersonEvent;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/person")
public class PersonResource {

    @Inject
    private PersonCache personCache;

    @Inject
    private Event<AddPersonEvent> addPersonEventEvent;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> getPerson() {
        return personCache.getAllPersons();
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public void handlePersonRequest(Person person) {
        addPersonEventEvent.fire(new AddPersonEvent(person));
    }
}
