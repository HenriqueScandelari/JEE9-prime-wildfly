package br.scandelari.app.model.enums;

public enum ControladoEnum {
    SIM(1), NAO(0);

    private int valor;
    ControladoEnum(int valor){
        this.valor = valor;
    }

    int getValor() {
        return valor;
    }

}
