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
import dominio._Status;
import persistencia.ElementoDAO;
import persistencia._AtividadeDAO;
import persistencia._ElementoDAO;
import persistencia._ElementoSDAO;
import persistencia._OpcaoDAO;
import persistencia._RespostaDAO;

@ManagedBean
@SessionScoped
public class RespostaBean {
	
	private _Resposta resposta;
	private _RespostaDAO respostaDao;
	private _ElementoSDAO elementoSDao;
	private _AtividadeDAO atividadeDao;
	private _ElementoDAO elementoDao;
	private _OpcaoDAO opcaoDao;
	private _Atividade atividade;
	private List<_ElementoS> _elementosResposta;
	List<_Elemento> elementosAtividade;
	private List<_Opcao> _opcoes;
	
	public RespostaBean(){
		resposta = new _Resposta();
		respostaDao = new _RespostaDAO();
		elementoSDao = new _ElementoSDAO();
		atividadeDao = new _AtividadeDAO();
		elementoDao = new _ElementoDAO();
		opcaoDao = new _OpcaoDAO();
		
		
		if( (_Atividade) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("atividade") != null)
			atividade = (_Atividade) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("atividade");
		else
			atividade = null;
		
		resposta.setAtividade(atividade);
		System.out.println("Atividade na sessao: " + atividade.getNome());
		
		_opcoes = opcaoDao.listarTodos();
		elementosAtividade = elementoDao.getElementosPorAtividade(resposta.getAtividade());
		_elementosResposta = new ArrayList();
		for (int i = 0; i < 60; i++) {
			_ElementoS _e = new _ElementoS();
			_e.setPosicao(i);
			_e.setOpcao(_opcoes.get(0));
			if (i == 0)
				_e.setOpcao(_opcoes.get(1));
			else if(elementosAtividade.get(i).getOpcao().getStatus() == _Status.PROIBIDO)
			{
				_e.setOpcao(elementosAtividade.get(i).getOpcao());
				System.out.println();
			}
			_elementosResposta.add(_e);
		}
	}
	
	public String verResposta(){
		//this.resposta = resposta;
		//System.out.println("Bean Resposta: " + resposta.getResultado());
		System.out.println("ver resposta");
		return "/resposta.xhtml";
	}
	
	public void submeter(){
		simular();
		resposta.setId(null);
		respostaDao.inserir(resposta);
		resposta = respostaDao.ultimaResposta();
		System.out.println("id da ultima resposta " + resposta.getId());
		for (_ElementoS _ele : _elementosResposta) {
			_ele.setResposta(resposta);
			elementoSDao.inserir(_ele);
		}
		resposta.setResultado(validar());
		System.out.println("Avaliador: " + resposta.getResultado());
		respostaDao.alterar(resposta);
	}
	
	public void limparCenario() {
		int i = 0;
		for (_ElementoS e : _elementosResposta) {
			e.setOpcao(_opcoes.get(0));
			if (e.getPosicao() == 0)
				e.setOpcao(_opcoes.get(1));
			else if(elementosAtividade.get(i).getOpcao().getStatus() == _Status.PROIBIDO){
				System.out.println("Mostrar obstaculo");
				e.setOpcao(elementosAtividade.get(i).getOpcao());
			}
		}
	}
	
	public void simular(){
		/* inicio frente(11) direita(1) frente(4) fim */
		
		limparCenario();
		String co = resposta.getCodigo();
		System.out.println(co);
		co = co.replaceAll("\n", "");
		System.out.println(co);
		String[] codigo = co.split(" ");
		int posicao = 0;
		int direcao = 1;
		for(String comando: codigo){
			System.out.println(comando);
			if(comando.equals("inicio")){
				_elementosResposta.get(posicao).setOpcao(_opcoes.get(1));
			}
			if(comando.contains("frente")){
				System.out.println("Achou comando frente + " + posicao);
				String tempo = comando;
				tempo = tempo.replaceAll("frente", "");
				tempo = tempo.substring(1, tempo.length()-1);
				System.out.println("Tempo: " + tempo);
				for(int i = 0; i < Integer.parseInt(tempo); i++){
					posicao += incremento(direcao);
					if(direcao == 1 || direcao == 3)
						_elementosResposta.get(posicao).setOpcao(_opcoes.get(3));
					if(direcao == 2 || direcao == 4)
						_elementosResposta.get(posicao).setOpcao(_opcoes.get(4));
				}
			}
			if(comando.contains("direita")){
				System.out.println("Achou comando direita + " + posicao);
				if(direcao == 1)
					direcao = 2;
				else if(direcao == 2)
					direcao = 3;
				else if(direcao == 3)
					direcao = 4;
				else if(direcao == 4)
					direcao = 1;
				
				if(direcao == 2 && _elementosResposta.get(posicao).getOpcao() == _opcoes.get(3) )
					_elementosResposta.get(posicao).setOpcao(_opcoes.get(5));
				if(direcao == 3 && _elementosResposta.get(posicao).getOpcao() == _opcoes.get(4) )
					_elementosResposta.get(posicao).setOpcao(_opcoes.get(6));
				if(direcao == 4 && _elementosResposta.get(posicao).getOpcao() == _opcoes.get(3) )
					_elementosResposta.get(posicao).setOpcao(_opcoes.get(7));
				if(direcao == 1 && _elementosResposta.get(posicao).getOpcao() == _opcoes.get(4) )
					_elementosResposta.get(posicao).setOpcao(_opcoes.get(8));
			}
			if(comando.contains("esquerda")){
				System.out.println("Achou comando esquerda + " + posicao);
				if(direcao == 1)
					direcao = 4;
				else if(direcao == 2)
					direcao = 1;
				else if(direcao == 3)
					direcao = 2;
				else if(direcao == 4)
					direcao = 3;
				
				if(direcao == 2 && _elementosResposta.get(posicao).getOpcao() == _opcoes.get(3) )
					_elementosResposta.get(posicao).setOpcao(_opcoes.get(8));
				if(direcao == 3 && _elementosResposta.get(posicao).getOpcao() == _opcoes.get(4) )
					_elementosResposta.get(posicao).setOpcao(_opcoes.get(5));
				if(direcao == 4 && _elementosResposta.get(posicao).getOpcao() == _opcoes.get(3) )
					_elementosResposta.get(posicao).setOpcao(_opcoes.get(6));
				if(direcao == 1 && _elementosResposta.get(posicao).getOpcao() == _opcoes.get(4) )
					_elementosResposta.get(posicao).setOpcao(_opcoes.get(7));
			}
			if(comando.equals("fim")){
				_elementosResposta.get(posicao).setOpcao(_opcoes.get(1));
			}
				
		}
	}
	
	private int incremento(int direcao){
		/* direcao = 1 => incremento +1
		 * direcao = 2 => incremento +12
		 * direcao = 3 => incremento -1
		 * direcao = 4 => incremento -12
		 * */
		if(direcao == 1)
			return 1;
		else if(direcao == 2)
			return 12;
		else if(direcao == 3)
			return -1;
		else if(direcao == 4)
			return -12;
		else
			return 0;
	}
	
	private String validar(){
		
		for(int i=0; i<elementosAtividade.size();i++){
			if(elementosAtividade.get(i).getOpcao().getStatus() == _Status.OBRIGATORIO && 
					_elementosResposta.get(i).getOpcao().getStatus() != _Status.OBRIGATORIO){
				return "Errado";
			}
			if(elementosAtividade.get(i).getOpcao().getStatus() == _Status.PROIBIDO && 
					_elementosResposta.get(i).getOpcao().getStatus() == _Status.OBRIGATORIO){
				return "Errado";
			}
		}
		return "Certo";
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
		return _elementosResposta;
	}

	public void set_elementos(List<_ElementoS> _elementos) {
		this._elementosResposta = _elementos;
	}

	public List<_Opcao> get_opcoes() {
		return _opcoes;
	}

	public void set_opcoes(List<_Opcao> _opcoes) {
		this._opcoes = _opcoes;
	}
	

}
