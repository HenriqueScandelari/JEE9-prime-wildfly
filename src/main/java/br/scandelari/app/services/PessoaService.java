package br.scandelari.app.services;

import br.scandelari.app.model.Pessoa;
import br.scandelari.app.repository.PessoaRepository;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

@Stateless
public class PessoaService {

    @EJB
    private PessoaRepository pessoaRepository;

    public void create(Pessoa pessoa) {
        pessoaRepository.persist(pessoa);
    }
}
