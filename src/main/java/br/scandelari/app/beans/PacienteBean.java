package br.scandelari.app.beans;

import br.scandelari.app.model.Paciente;
import br.scandelari.app.model.enums.SexoEnum;
import br.scandelari.app.repository.PacienteLazyDataMode;
import br.scandelari.app.services.PacienteService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RequestScoped
@Named
public class PacienteBean {

    @Inject
    private PacienteService pacienteService;

    @Inject
    private PacienteLazyDataMode listaPacientesPaginado;

    private Paciente novoPaciente;

    public PacienteBean() {
        novoPaciente = new Paciente();
    }

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

    public String addPaciente() {
        novoPaciente = new Paciente();
        return "pacienteCadastro.xhtml?faces-redirect=true";
    }

    public String salvar() {
//        novoPaciente.setDataNascimento(LocalDate.now());
        pacienteService.create(novoPaciente);
        novoPaciente = new Paciente();

        return "pacienteList.xhtml?faces-redirect=true";
    }

    public void delPaciente(Long id) {
        pacienteService.delete(id);
    }

    public SexoEnum[] getTipoSexo(){
        return SexoEnum.values();
    }

//    private void pacienteNovo() {
//        novoPaciente = new Paciente();
//        novoPaciente.setAtivo(true);
//        novoPaciente.setDtInclusao(LocalDateTime.now());
//    }

    public Paciente getNovoPaciente() {
        return novoPaciente;
    }

    public void setNovoPaciente(Paciente novoPaciente) {
        this.novoPaciente = novoPaciente;
    }
}
