package br.scandelari.app.model.enums;

public enum Sexo {
	M("Masculino"), F("Feminino"), O("Outro");
	
	public String valor;
	Sexo(String valor){
		this.valor = valor;
	}
}
