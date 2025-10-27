package br.scandelari.app.model;

import br.scandelari.app.model.enums.SexoEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.UniqueElements;
import org.hibernate.validator.constraints.br.CPF;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "paciente")
public class Paciente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPaciente;
    @Column(nullable = false)
    private String nome;
    @Column(unique = true, nullable = false)
//    @Size(min = 11, max = 11)
    @CPF(message = "CPF inválido.")
    private String cpf;
    @Column(nullable = false)
    private LocalDate dataNascimento;
    @Enumerated(EnumType.STRING)
    private SexoEnum sexo;
    @Column(nullable = false)
    private LocalDateTime dtInclusao;
    @Column(nullable = false)
    private Boolean ativo;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "paciente_medicamento",
            joinColumns = @JoinColumn(name = "paciente_id"),
            inverseJoinColumns = @JoinColumn(name = "medicamento_id")
    )
    private Set<Medicamento> medicamentos = new HashSet<>();

    public Paciente() {
        ZoneId spZone = ZoneId.of("America/Sao_Paulo");
        this.ativo = true;
        this.dtInclusao = LocalDateTime.now(spZone);
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

    public String getFormattedCpf() {
        return cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public SexoEnum getSexo() {
        return sexo;
    }

    public void setSexo(SexoEnum sexoEnum) {
        this.sexo = sexoEnum;
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

    public Set<Medicamento> getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(Set<Medicamento> medicamentos) {
        this.medicamentos = medicamentos;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Paciente paciente = (Paciente) o;
        return getCpf().equals(paciente.getCpf());
    }

    @Override
    public int hashCode() {
        return getCpf().hashCode();
    }
}
