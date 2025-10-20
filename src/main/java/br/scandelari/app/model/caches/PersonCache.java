package br.scandelari.app.model.caches;

import br.scandelari.app.model.Person;
import br.scandelari.app.model.events.AddPersonEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class PersonCache {
    private List<Person> allPersons = new ArrayList<>();

    public List<Person> getAllPersons() {
        return allPersons;
    }

    public void addPerson(@Observes AddPersonEvent addPersonEvent) {
        allPersons.add(addPersonEvent.getPerson());
    }
}
