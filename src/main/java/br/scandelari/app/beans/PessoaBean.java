package br.scandelari.app.beans;

import br.scandelari.app.model.Pessoa;
import br.scandelari.app.services.PessoaService;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named
@RequestScoped
public class PessoaBean {

    @EJB
    private PessoaService pessoaService;


    Pessoa pessoa = new Pessoa();

    public String gravar() {
        pessoa.setNome("Tobias");
        pessoaService.create(pessoa);
        return "";
    }
}
