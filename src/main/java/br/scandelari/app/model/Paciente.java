package br.scandelari.app.model;

import br.scandelari.app.model.enums.Sexo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "paciente")
public class Paciente implements Serializable {

    @Id
    private Long idPaciente;
    private String nome;

    @Column(unique = true)
    private String cpf;
    private LocalDate dataNasciemnto;
    private Sexo sexo;
    private LocalDateTime dtInclusao;
    private Boolean ativo;

    public Paciente() {
    }

    public Long getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDataNasciemnto() {
        return dataNasciemnto;
    }

    public void setDataNasciemnto(LocalDate dataNasciemnto) {
        this.dataNasciemnto = dataNasciemnto;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public LocalDateTime getDtInclusao() {
        return dtInclusao;
    }

    public void setDtInclusao(LocalDateTime dtInclusao) {
        this.dtInclusao = dtInclusao;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}
