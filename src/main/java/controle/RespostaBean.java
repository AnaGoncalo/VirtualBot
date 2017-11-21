package controle;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
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
@RequestScoped
public class RespostaBean {
	
	private _Resposta resposta;
	private _RespostaDAO respostaDao;
	private _ElementoSDAO elementoSDao;
	private _AtividadeDAO atividadeDao;
	private _ElementoDAO elementoDao;
	private _OpcaoDAO opcaoDao;
	private _Atividade atividade;
	private List<_ElementoS> elementosResposta;
	List<_Elemento> elementosAtividade;
	private List<_Opcao> opcoes;
	
	public RespostaBean(){
		atividade = new _Atividade();
		resposta = new _Resposta();
		respostaDao = new _RespostaDAO();
		elementoSDao = new _ElementoSDAO();
		atividadeDao = new _AtividadeDAO();
		elementoDao = new _ElementoDAO();
		opcaoDao = new _OpcaoDAO();
		
		opcoes = opcaoDao.listarTodos();
		
		if( (_Atividade) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("atividade") != null){
			atividade = (_Atividade) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("atividade");
			resposta.setAtividade(atividade);
			elementosAtividade = elementoDao.getElementosPorAtividade(resposta.getAtividade());
			System.out.println("Atividade na sessao: " + atividade.getNome() + atividade.getId());
		}
		else{
			atividade.setNome("-");
			elementosAtividade = new ArrayList();
			for (int i = 0; i < 60; i++) {
				_Elemento _e = new _Elemento();
				_e.setPosicao(i);
				_e.setOpcao(opcoes.get(0));
				if (i == 0)
					_e.setOpcao(opcoes.get(1));
				elementosAtividade.add(_e);
			}
		}
		
		
		elementosResposta = new ArrayList();
		for (int i = 0; i < 60; i++) {
			_ElementoS _e = new _ElementoS();
			_e.setPosicao(i);
			_e.setOpcao(opcoes.get(0));
			if (i == 0)
				_e.setOpcao(opcoes.get(1));
			elementosResposta.add(_e);
		}
		limparCenario();
	}
	
	public String verResposta(_Resposta resposta){
		this.resposta = resposta;
		System.out.println("Bean Resposta: " + resposta.getResultado());
		System.out.println("ver resposta");
		elementosResposta = elementoSDao.getElementosPorResposta(resposta);
		return "/resposta.xhtml";
	}
	
	public void submeter(){
		simular();
		resposta.setId(null);
		respostaDao.inserir(resposta);
		resposta = respostaDao.ultimaResposta();
		System.out.println("id da ultima resposta " + resposta.getId());
		for (_ElementoS _ele : elementosResposta) {
			_ele.setResposta(resposta);
			elementoSDao.inserir(_ele);
		}
		resposta.setResultado(validar());
		System.out.println("Avaliador: " + resposta.getResultado());
		respostaDao.alterar(resposta);
	}
	
	public void limparCenario() {
		if( (_Atividade) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("atividade") != null){
			atividade = (_Atividade) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("atividade");
			resposta.setAtividade(atividade);
			elementosAtividade = elementoDao.getElementosPorAtividade(resposta.getAtividade());
			System.out.println("Atividade na sessao: " + atividade.getNome() + atividade.getId());
		}
		else{
			atividade.setNome("-");
			elementosAtividade = new ArrayList();
			for (int i = 0; i < 60; i++) {
				_Elemento _e = new _Elemento();
				_e.setPosicao(i);
				_e.setOpcao(opcoes.get(0));
				if (i == 0)
					_e.setOpcao(opcoes.get(1));
				elementosAtividade.add(_e);
			}
		}
		
		int i = 0;
		for (_ElementoS e : elementosResposta) {
			e.setOpcao(opcoes.get(0));
			System.out.println(elementosAtividade.get(i).getOpcao().getStatus());
			if (e.getPosicao() == 0)
				e.setOpcao(opcoes.get(1));
			else if(elementosAtividade.get(i).getOpcao().getStatus() == _Status.PROIBIDO){
				System.out.println("Mostrar obstaculo");
				e.setOpcao(elementosAtividade.get(i).getOpcao());
			}
			i++;
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
		for(int j = 0; j<codigo.length; j++){
			String comando = codigo[j];
			System.out.println(comando);
			/* inicio se(ultra()>5) entao{direita(1)} frente(2) fim */
			if(comando.equals("inicio")){
				elementosResposta.get(posicao).setOpcao(opcoes.get(1));
			}
			else if(comando.contains("se")){
				System.out.println("Achou comando se");
				String condicao = comando.replaceAll("se", "");
				condicao = condicao.substring(1, condicao.length()-1);
				System.out.println("Condição: " + condicao);
				if(condicao.contains("<")){
					String[] condi = condicao.split("<");
					if(condi[0].contains("ultra")){
						System.out.println("Elemento da atividade: "+elementosAtividade.get(posicao+Integer.parseInt(condi[1])).getOpcao().getStatus());
						if(elementosAtividade.get(posicao+Integer.parseInt(condi[1])).getOpcao().getStatus()
								.equals(_Status.PROIBIDO)){
							continue;
						}
						else{
							int x = 1;
							do{
								System.out.println("Nao vai executar " + codigo[j+x]);
								codigo[j+x] = "";
								x++;
							}while(!codigo[j+x].contains("}"));
								
						}
						
					}
				}
			}
			else if(comando.contains("frente")){
				System.out.println("Achou comando frente + " + posicao);
				String tempo = comando;
				tempo = tempo.replaceAll("frente", "");
				tempo = tempo.substring(1, tempo.length()-1);
				System.out.println("Tempo: " + tempo);
				for(int i = 0; i < Integer.parseInt(tempo); i++){
					posicao += incremento(direcao);
					if(direcao == 1 || direcao == 3)
						elementosResposta.get(posicao).setOpcao(opcoes.get(3));
					if(direcao == 2 || direcao == 4)
						elementosResposta.get(posicao).setOpcao(opcoes.get(4));
				}
			}
			else if(comando.contains("direita")){
				System.out.println("Achou comando direita + " + posicao);
				if(direcao == 1)
					direcao = 2;
				else if(direcao == 2)
					direcao = 3;
				else if(direcao == 3)
					direcao = 4;
				else if(direcao == 4)
					direcao = 1;
				
				if(direcao == 2 && elementosResposta.get(posicao).getOpcao() == opcoes.get(3) )
					elementosResposta.get(posicao).setOpcao(opcoes.get(5));
				if(direcao == 3 && elementosResposta.get(posicao).getOpcao() == opcoes.get(4) )
					elementosResposta.get(posicao).setOpcao(opcoes.get(6));
				if(direcao == 4 && elementosResposta.get(posicao).getOpcao() == opcoes.get(3) )
					elementosResposta.get(posicao).setOpcao(opcoes.get(7));
				if(direcao == 1 && elementosResposta.get(posicao).getOpcao() == opcoes.get(4) )
					elementosResposta.get(posicao).setOpcao(opcoes.get(8));
			}
			else if(comando.contains("esquerda")){
				System.out.println("Achou comando esquerda + " + posicao);
				if(direcao == 1)
					direcao = 4;
				else if(direcao == 2)
					direcao = 1;
				else if(direcao == 3)
					direcao = 2;
				else if(direcao == 4)
					direcao = 3;
				
				if(direcao == 2 && elementosResposta.get(posicao).getOpcao() == opcoes.get(3) )
					elementosResposta.get(posicao).setOpcao(opcoes.get(8));
				if(direcao == 3 && elementosResposta.get(posicao).getOpcao() == opcoes.get(4) )
					elementosResposta.get(posicao).setOpcao(opcoes.get(5));
				if(direcao == 4 && elementosResposta.get(posicao).getOpcao() == opcoes.get(3) )
					elementosResposta.get(posicao).setOpcao(opcoes.get(6));
				if(direcao == 1 && elementosResposta.get(posicao).getOpcao() == opcoes.get(4) )
					elementosResposta.get(posicao).setOpcao(opcoes.get(7));
			}
			else if(comando.equals("fim")){
				elementosResposta.get(posicao).setOpcao(opcoes.get(1));
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
					elementosResposta.get(i).getOpcao().getStatus() != _Status.OBRIGATORIO){
				return "Errado";
			}
			if(elementosAtividade.get(i).getOpcao().getStatus() == _Status.PROIBIDO && 
					elementosResposta.get(i).getOpcao().getStatus() == _Status.OBRIGATORIO){
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
		return elementosResposta;
	}

	public void set_elementos(List<_ElementoS> _elementos) {
		this.elementosResposta = _elementos;
	}

	public List<_Opcao> get_opcoes() {
		return opcoes;
	}

	public void set_opcoes(List<_Opcao> _opcoes) {
		this.opcoes = _opcoes;
	}

	public _ElementoSDAO getElementoSDao() {
		return elementoSDao;
	}

	public void setElementoSDao(_ElementoSDAO elementoSDao) {
		this.elementoSDao = elementoSDao;
	}

	public _ElementoDAO getElementoDao() {
		return elementoDao;
	}

	public void setElementoDao(_ElementoDAO elementoDao) {
		this.elementoDao = elementoDao;
	}

	public List<_ElementoS> getElementosResposta() {
		return elementosResposta;
	}

	public void setElementosResposta(List<_ElementoS> elementosResposta) {
		this.elementosResposta = elementosResposta;
	}

	public List<_Elemento> getElementosAtividade() {
		return elementosAtividade;
	}

	public void setElementosAtividade(List<_Elemento> elementosAtividade) {
		this.elementosAtividade = elementosAtividade;
	}

	public List<_Opcao> getOpcoes() {
		return opcoes;
	}

	public void setOpcoes(List<_Opcao> opcoes) {
		this.opcoes = opcoes;
	}
	
	

}
