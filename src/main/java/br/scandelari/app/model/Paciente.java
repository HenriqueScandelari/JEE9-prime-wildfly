package br.scandelari.app.model;

import br.scandelari.app.model.enums.Sexo;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Paciente implements Serializable {

    private Long idPaciente;
    private String nome;
    private String cpf;
    private LocalDate dataNasciemnto;
    private Sexo sexo;
    private LocalDateTime dtInclusao;
    private Boolean ativo;
}
