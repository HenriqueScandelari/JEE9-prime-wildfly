package br.scandelari.app.beans;

import br.scandelari.app.model.Medicamento;
import br.scandelari.app.model.Paciente;
import br.scandelari.app.model.enums.SexoEnum;
import br.scandelari.app.repository.PacienteLazyDataMode;
import br.scandelari.app.services.PacienteService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.application.ViewHandler;
import jakarta.faces.component.UIViewRoot;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

import java.io.Serializable;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SessionScoped
@Named
public class PacienteBean implements Serializable {

    @Inject
    private PacienteService pacienteService;

    @Inject
    private PacienteLazyDataMode listaPacientesPaginado;

    private Paciente novoPaciente;

    private Paciente pacienteSelecionado;

    private Boolean editavel;

    public PacienteBean() {
//        novoPaciente = new Paciente();
        editavel = true;
    }

    public List<Paciente> listarPacientes() {
        return pacienteService.findAll();
    }

    public PacienteLazyDataMode getListaPacientesPaginado() {
        return listaPacientesPaginado;
    }

    public Paciente getPacienteSelecionado() {
        return pacienteSelecionado;
    }

    public void setPacienteSelecionado(Paciente pacienteSelecionado) {
        this.pacienteSelecionado = pacienteSelecionado;
    }

    public Boolean getEditavel() {
        return editavel;
    }

    public void setEditavel(Boolean editavel) {
        this.editavel = editavel;
    }

    public void onRowEdit(RowEditEvent<Paciente> event) {
        novoPaciente = event.getObject();
        salvar();
//        FacesContext context = FacesContext.getCurrentInstance();
//        String viewId = context.getViewRoot().getViewId();
//        ViewHandler handler = context.getApplication().getViewHandler();
//        UIViewRoot root = handler.createView(context, viewId);
//        root.setViewId(viewId);
//        context.setViewRoot(root);
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
        try {
            pacienteService.createOrUpdate(novoPaciente);
            novoPaciente = new Paciente();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Paciente Atualizado.", null));
            return "pacienteList.xhtml?faces-redirect=true";
        } catch (ConstraintViolationException | PersistenceException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "CPF deve ser único.", null));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
        }
        return null;
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

    @Transactional
    public String atribuirMedicamentos(Paciente paciente) {
        novoPaciente = pacienteService.findById(paciente.getIdPaciente());
        if (novoPaciente.getAtivo()) {
            System.out.println(novoPaciente);
            return "pacienteCadastro.xhtml?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro!", "Paciente " + novoPaciente.getNome() + " está inativo."));
            return null;
        }
    }
    public void atribuirMedicamento(Medicamento medicamento) {

        if (novoPaciente.getMedicamentos().add(medicamento)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Medicamento preescrito com sucesso!", null));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Erro!", "Medicamento já preescrito."));
        }

    }

    public void removerMedicamento(Medicamento medicamento) {
        if (novoPaciente.getMedicamentos().remove(medicamento)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso",  "Medicamento removido com sucesso!"));
        }
//        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Medicamento preescrito com sucesso!.", null));
//        return "pacienteCadastro.xhtml?faces-redirect=true";
    }

    public Paciente getNovoPaciente() {
        return novoPaciente;
    }

    public void setNovoPaciente(Paciente novoPaciente) {
        this.novoPaciente = novoPaciente;
    }
}
