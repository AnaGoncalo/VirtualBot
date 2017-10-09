package dominio;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Elemento implements Serializable{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@NotNull
    @Enumerated(EnumType.STRING)
    private Opcao obrigatoriedade;
	
	private String nome;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Opcao getObrigatoriedade() {
		return obrigatoriedade;
	}
	public void setObrigatoriedade(Opcao obrigatoriedade) {
		this.obrigatoriedade = obrigatoriedade;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
}
