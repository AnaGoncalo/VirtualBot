package controle;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import dominio._Atividade;
import dominio._Resposta;
import persistencia._AtividadeDAO;
import persistencia._RespostaDAO;

@ManagedBean
@SessionScoped
public class RespostaBean {
	
	private _Resposta resposta;
	private _RespostaDAO respostaDao;
	private _AtividadeDAO atividadeDao;
	private _Atividade atividade;
	
	public RespostaBean(){
		resposta = new _Resposta();
		respostaDao = new _RespostaDAO();
		atividadeDao = new _AtividadeDAO();
		atividade = (_Atividade) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("atividade");
		resposta.setAtividade(atividade);
		
	}

	public _Resposta getResposta() {
		return resposta;
	}

	public void setResposta(_Resposta resposta) {
		this.resposta = resposta;
	}
	
	public void submeter(){
		simular();
		_Atividade ati = (_Atividade) atividadeDao.pesquisarPorId(1l);
		
		//FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("atividade");
		//resposta.setAtividade(ati);
		respostaDao.inserir(resposta);
		
		resposta = new _Resposta();
	}
	
	public void simular(){
		resposta.setResultado(validar());
	}
	
	private String validar(){
		if(!resposta.getCodigo().isEmpty())
			return "Certo";
		return "Errado";
	}

}
