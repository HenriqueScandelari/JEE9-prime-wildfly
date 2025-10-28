package br.scandelari.app.beans;

import br.scandelari.app.model.Medicamento;
import br.scandelari.app.model.enums.ControladoEnum;
import br.scandelari.app.model.enums.SexoEnum;
import br.scandelari.app.repository.MedicamentoLazyDataMode;
import br.scandelari.app.services.MedicamentoService;
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
public class MedicamentoBean {

    @Inject
    private MedicamentoService medicamentoService;

    @Inject
    private MedicamentoLazyDataMode listaMedicamentosPaginado;

    private Medicamento novoMedicamento;

    public MedicamentoBean() {
        novoMedicamento = new Medicamento();
    }

    public List<Medicamento> listarMedicamentos() {
        return medicamentoService.findAll();
    }

    public MedicamentoLazyDataMode getListaMedicamentosPaginado() {
        return listaMedicamentosPaginado;
    }

    public void onRowEdit(RowEditEvent<Medicamento> event) {
        Medicamento medicamento = event.getObject();
        medicamentoService.update(medicamento);
    }

    public void onRowCancel(RowEditEvent<Medicamento> event) {
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

    public String addMedicamento() {
        novoMedicamento = new Medicamento();
        return "medicamentoCadastro.xhtml?faces-redirect=true";
    }

    public String salvar() {
        medicamentoService.create(novoMedicamento);
        novoMedicamento = new Medicamento();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Medicamento Criado.", null));
        return "medicamentoList.xhtml?faces-redirect=true";
    }

    public void delMedicamento(Long id) {
        medicamentoService.delete(id);
    }

    public ControladoEnum[] getTipoControlado(){
        return ControladoEnum.values();
    }

    public Medicamento getNovoMedicamento() {
        return novoMedicamento;
    }

    public void setNovoMedicamento(Medicamento novoMedicamento) {
        this.novoMedicamento = novoMedicamento;
    }
}
