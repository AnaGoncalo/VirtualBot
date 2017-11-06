package controle;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import dominio._Atividade;
import dominio._Elemento;
import dominio._ElementoS;
import dominio._Opcao;
import dominio._Resposta;
import persistencia.ElementoDAO;
import persistencia._AtividadeDAO;
import persistencia._OpcaoDAO;
import persistencia._RespostaDAO;

@ManagedBean
@SessionScoped
public class RespostaBean {
	
	private _Resposta resposta;
	private _RespostaDAO respostaDao;
	private _AtividadeDAO atividadeDao;
	private _OpcaoDAO opcaoDao;
	private _Atividade atividade;
	private List<_ElementoS> _elementos;
	private List<_Opcao> _opcoes;
	
	public RespostaBean(){
		resposta = new _Resposta();
		respostaDao = new _RespostaDAO();
		atividadeDao = new _AtividadeDAO();
		opcaoDao = new _OpcaoDAO();
		atividade = (_Atividade) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("atividade");
		resposta.setAtividade(atividade);
		System.out.println("Atividade na sessao: " + atividade.getNome());
		
		_opcoes = opcaoDao.listarTodos();
		_elementos = new ArrayList();
		for (int i = 0; i < 60; i++) {
			_ElementoS _e = new _ElementoS();
			_e.setPosicao(i);
			_e.setOpcao(_opcoes.get(0));
			if (i == 0)
				_e.setOpcao(_opcoes.get(1));
			_elementos.add(_e);
		}
	}
	
	public void submeter(){
		simular();
		respostaDao.inserir(resposta);
		
		resposta = new _Resposta();
	}
	
	public void limparCenario() {
		for (_ElementoS e : _elementos) {
			e.setOpcao(_opcoes.get(0));
			if (e.getPosicao() == 0)
				e.setOpcao(_opcoes.get(1));
		}
	}
	
	public void simular(){
		limparCenario();
		String co = resposta.getCodigo();
		co = co.replace("\\n", " ");
		String[] codigo = co.split(" ");
		int posicao = 1;
		for(String comando: codigo){
			System.out.println(comando);
			if(comando.equals("inicio"))
				continue;
			if(comando.contains("frente")){
				System.out.println("Achou comando frente + " + posicao);
				String[] valores = comando.split(",");
				int tempo = Integer.parseInt(valores[2].substring(0, valores[2].length()-1));
				System.out.println("tempo: " + tempo);
				for(int i = 0; i < tempo/1000; i++){
					_elementos.get(posicao).setOpcao(_opcoes.get(3));
					posicao++;
				}
			}
				
		}
		
		resposta.setResultado(validar());
	}
	
	private String validar(){
		if(!resposta.getCodigo().isEmpty())
			return "Certo";
		return "Errado";
	}
	
	public _Resposta getResposta() {
		return resposta;
	}

	public void setResposta(_Resposta resposta) {
		this.resposta = resposta;
	}

	public _RespostaDAO getRespostaDao() {
		return respostaDao;
	}

	public void setRespostaDao(_RespostaDAO respostaDao) {
		this.respostaDao = respostaDao;
	}

	public _AtividadeDAO getAtividadeDao() {
		return atividadeDao;
	}

	public void setAtividadeDao(_AtividadeDAO atividadeDao) {
		this.atividadeDao = atividadeDao;
	}

	public _OpcaoDAO getOpcaoDao() {
		return opcaoDao;
	}

	public void setOpcaoDao(_OpcaoDAO opcaoDao) {
		this.opcaoDao = opcaoDao;
	}

	public _Atividade getAtividade() {
		return atividade;
	}

	public void setAtividade(_Atividade atividade) {
		this.atividade = atividade;
	}

	public List<_ElementoS> get_elementos() {
		return _elementos;
	}

	public void set_elementos(List<_ElementoS> _elementos) {
		this._elementos = _elementos;
	}

	public List<_Opcao> get_opcoes() {
		return _opcoes;
	}

	public void set_opcoes(List<_Opcao> _opcoes) {
		this._opcoes = _opcoes;
	}
	

}
