package br.scandelari.app.model;

import br.scandelari.app.model.enums.ControladoEnum;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Medicamento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMedicamento;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    @Enumerated
    private ControladoEnum controlado;
    private String posologia;
    @Column(nullable = false)
    private LocalDateTime dataCadastro;
    @ManyToMany(mappedBy = "medicamentos") // "projects" refers to the field in Employee
    private Set<Paciente> pacientes = new HashSet<>();

    public Medicamento() {
        ZoneId spZone = ZoneId.of("America/Sao_Paulo");
        this.dataCadastro = LocalDateTime.now(spZone);
    }

    public Long getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(Long idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ControladoEnum getControlado() {
        return controlado;
    }

    public void setControlado(ControladoEnum controlado) {
        this.controlado = controlado;
    }

    public String getPosologia() {
        return posologia;
    }

    public void setPosologia(String posologia) {
        this.posologia = posologia;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Set<Paciente> getPacientes() {
        return pacientes;
    }

    public void setPacientes(Set<Paciente> pacientes) {
        this.pacientes = pacientes;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Medicamento that = (Medicamento) o;
        return getIdMedicamento().equals(that.getIdMedicamento());
    }

    @Override
    public int hashCode() {
        return getIdMedicamento().hashCode();
    }
}
