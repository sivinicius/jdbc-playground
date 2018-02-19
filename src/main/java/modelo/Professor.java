package modelo;

public class Professor {
	
	private Integer id;
	private String nome;
	
	public Professor() {
		
	}

	public Professor(Integer id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}

	public Professor(String nome) {
		this.nome = nome;
	}

	public Integer getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	@Override
	public String toString() {
		return "Professor: " + this.nome + ", ID: " + this.id;
	}
}
