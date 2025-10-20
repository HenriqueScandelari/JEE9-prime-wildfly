package br.scandelari.app.resource;

import br.scandelari.app.services.GreetingService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/hello")
public class HelloResource {
    @Inject
    GreetingService greetingService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String sayHello() {
        return "Hello World!!";
    }

    @GET
    @Path("/{name}")
    public String doGreeting(@PathParam("name") String name, @QueryParam("language") String lang) {
        return String.format(greetingService.getGreetingTemplate(lang), name);
//                greetingService.getGreetingTemplate(lang).formatted(name);
    }
}
