package modelo;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

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
	
	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("ID", this.getId()).append("Nome", this.getNome()).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.getId()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Curso other = (Curso) obj;
		return new EqualsBuilder().append(this.getId(), other.getId()).isEquals();
	}
}
