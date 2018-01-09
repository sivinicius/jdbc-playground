package dominio;

public enum Status {
	
	ATIVO("S"), INATIVO("N");
	
	String valor;
	
	private Status(String valor) {
		this.valor = valor;
	}

	public String getValor() {
		return valor;
	}
	
}
