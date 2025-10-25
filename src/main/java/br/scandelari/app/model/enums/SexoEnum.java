package br.scandelari.app.model.enums;

public enum SexoEnum {
	M("Masculino"), F("Feminino"), O("Outro");
	
	private String valor;
	SexoEnum(String valor){
		this.valor = valor;
	}

    public String getValor() {
        return valor;
    }
}
