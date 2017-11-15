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
				_elementos.get(posicao).setOpcao(_opcoes.get(1));
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
						_elementos.get(posicao).setOpcao(_opcoes.get(3));
					if(direcao == 2 || direcao == 4)
						_elementos.get(posicao).setOpcao(_opcoes.get(4));
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
				
				if(direcao == 2 && _elementos.get(posicao).getOpcao() == _opcoes.get(3) )
					_elementos.get(posicao).setOpcao(_opcoes.get(5));
				if(direcao == 3 && _elementos.get(posicao).getOpcao() == _opcoes.get(4) )
					_elementos.get(posicao).setOpcao(_opcoes.get(6));
				if(direcao == 4 && _elementos.get(posicao).getOpcao() == _opcoes.get(3) )
					_elementos.get(posicao).setOpcao(_opcoes.get(7));
				if(direcao == 1 && _elementos.get(posicao).getOpcao() == _opcoes.get(4) )
					_elementos.get(posicao).setOpcao(_opcoes.get(8));
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
				
				if(direcao == 2 && _elementos.get(posicao).getOpcao() == _opcoes.get(3) )
					_elementos.get(posicao).setOpcao(_opcoes.get(8));
				if(direcao == 3 && _elementos.get(posicao).getOpcao() == _opcoes.get(4) )
					_elementos.get(posicao).setOpcao(_opcoes.get(5));
				if(direcao == 4 && _elementos.get(posicao).getOpcao() == _opcoes.get(3) )
					_elementos.get(posicao).setOpcao(_opcoes.get(6));
				if(direcao == 1 && _elementos.get(posicao).getOpcao() == _opcoes.get(4) )
					_elementos.get(posicao).setOpcao(_opcoes.get(7));
			}
			if(comando.equals("fim")){
				_elementos.get(posicao).setOpcao(_opcoes.get(1));
			}
				
		}
		
		resposta.setResultado(validar());
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
