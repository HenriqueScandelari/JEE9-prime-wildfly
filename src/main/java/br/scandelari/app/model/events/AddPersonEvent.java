package br.scandelari.app.model.events;

import br.scandelari.app.model.Person;

public class AddPersonEvent {
    private Person person;

    public AddPersonEvent(Person person) {
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }

}
