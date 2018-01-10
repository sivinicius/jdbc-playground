package modelo;

import dominio.Status;

public class Curso {

	private Integer id;
	private String nome;
	private Status ativo = Status.ATIVO;

	public Curso(Integer id, String nome, Status ativo) {
		this.id = id;
		this.nome = nome;
		this.ativo = ativo;
	}

	public Curso(String nome, Status ativo) {
		this.nome = nome;
		this.ativo = ativo;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String isAtivo() {
		return ativo.getValor();
	}

	@Override
	public String toString() {
		return "Id: " + this.getId() + ", Curso: " + this.getNome() + ", Ativo: " + this.isAtivo();
	}

}
