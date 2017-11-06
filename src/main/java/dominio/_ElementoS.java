package dominio;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class _ElementoS {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private int posicao;
	
	@ManyToOne
	private _Opcao opcao;

	@ManyToOne
	private _Resposta resposta;

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

	public _Resposta getResposta() {
		return resposta;
	}

	public void setResposta(_Resposta resposta) {
		this.resposta = resposta;
	}

}
