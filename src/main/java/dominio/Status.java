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

	public static Status obterStatus(String ativo) {
		for (Status status : Status.values()) {
			if(status.valor.equalsIgnoreCase(ativo)) {
				return status;
			}
		}
		return null;
	}
	
}
