package dominio;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class _Elemento implements Serializable {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private int posicao;
	
	@ManyToOne
	private _Opcao opcao;
	
	@ManyToOne
	private _Atividade atividade;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getPosicao() {
		return posicao;
	}

	public void setPosicao(int posicao) {
		this.posicao = posicao;
	}

	public _Opcao getOpcao() {
		return opcao;
	}

	public void setOpcao(_Opcao opcao) {
		this.opcao = opcao;
	}

	public _Atividade getAtividade() {
		return atividade;
	}

	public void setAtividade(_Atividade atividade) {
		this.atividade = atividade;
	}
}
