package br.scandelari.app.beans;

import br.scandelari.app.model.Paciente;
import br.scandelari.app.repository.PacienteLazyDataMode;
import br.scandelari.app.services.PacienteService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;

@RequestScoped
@Named
public class PacienteListBean {

    @Inject
    private PacienteService pacienteService;

    @Inject
    private PacienteLazyDataMode listaPacientesPaginado;

    public List<Paciente> listarPacientes() {
        return pacienteService.findAll();
    }

    public PacienteLazyDataMode getListaPacientesPaginado() {
        return listaPacientesPaginado;
    }

}
