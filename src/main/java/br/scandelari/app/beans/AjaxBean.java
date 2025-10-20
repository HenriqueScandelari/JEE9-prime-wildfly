package br.scandelari.app.beans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

import java.time.LocalDateTime;

@RequestScoped
@Named
public class AjaxBean extends HelloBean {

    public String getNow() {
        return LocalDateTime.now().toString();
    }
}
