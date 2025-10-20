package br.scandelari.app.services;

import br.scandelari.app.interceptors.annotations.Timed;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GreetingService {

    @PostConstruct
    public void init(){

    }

//    @Timed
    public String getGreetingTemplate(String language) {
        return switch (language) {
            case "en" -> "Hello %s";
            case "fr" -> "Bonjour %s";
            case "de" -> "Willcommen %s";
            default -> "Ola %s";
        };
    }
}
