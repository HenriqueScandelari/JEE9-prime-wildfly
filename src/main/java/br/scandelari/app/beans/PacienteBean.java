package br.scandelari.app.beans;

import br.scandelari.app.model.Paciente;
import br.scandelari.app.repository.PacienteLazyDataMode;
import br.scandelari.app.services.PacienteService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

import java.util.List;

@RequestScoped
@Named
public class PacienteBean {

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

    public void onRowEdit(RowEditEvent<Paciente> event) {
        Paciente paciente = event.getObject();
        paciente.setCpf(paciente.getCpf().replaceAll("[^0-9]", ""));
        pacienteService.update(event.getObject());
        FacesMessage msg = new FacesMessage("Paciente Atualizado", String.valueOf(event.getObject().getNome()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent<Paciente> event) {
        FacesMessage msg = new FacesMessage("Atualizaçao cancelada.", String.valueOf(event.getObject().getNome()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if (newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Valor Atualizado", "Anterior: " + oldValue + ", Novo:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

}
