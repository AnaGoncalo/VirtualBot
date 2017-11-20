package controle;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import dominio.Atividade;
import dominio.Elemento;
import dominio.Opcao;
import dominio._Atividade;
import dominio._Elemento;
import dominio._Opcao;
import dominio._Resposta;
import persistencia.AtividadeDAO;
import persistencia.ElementoDAO;
import persistencia._AtividadeDAO;
import persistencia._ElementoDAO;
import persistencia._OpcaoDAO;
import persistencia._RespostaDAO;

@ManagedBean
@SessionScoped
public class AtividadeBean {

	private _AtividadeDAO _atividadeDao;
	private _OpcaoDAO _opcaoDao;
	private _ElementoDAO _elementoDao;
	private _RespostaDAO _respostaDao;
	private _Atividade _atividade;
	private List<_Atividade> _atividades;
	private List<_Opcao> _opcoes;
	private List<_Elemento> _elementos;
	private List<_Resposta> _respostas;

	public AtividadeBean() {
		_atividadeDao = new _AtividadeDAO();
		_opcaoDao = new _OpcaoDAO();
		_elementoDao = new _ElementoDAO();
		_respostaDao = new _RespostaDAO();
		_atividade = new _Atividade();
		_opcoes = _opcaoDao.listarTodos();
		_elementos = new ArrayList();
		_respostas = new ArrayList();
		_atividadeDao.listarTodos();
		for (int i = 0; i < 60; i++) {
			_Elemento _e = new _Elemento();
			_e.setPosicao(i);
			_e.setOpcao(_opcoes.get(0));
			if (i == 0)
				_e.setOpcao(_opcoes.get(1));
			_elementos.add(_e);
		}
		listarAtividades();
	}

	public void listarAtividades() {
		limparCenario();
		_atividade = new _Atividade();
		_atividades = _atividadeDao.listarTodos();
	}

	public String addAtividade() {
		_atividadeDao.inserir(_atividade);
		System.out.println("atividade : " + _atividade.getNome());
		_atividade = _atividadeDao.buscarPorTitulo(_atividade.getNome());
		System.out.println("id da atividade cadastrada : " + _atividade.getId());
		for (_Elemento _ele : _elementos) {
			_ele.setAtividade(_atividade);
			_elementoDao.inserir(_ele);
		}
		listarAtividades();
		return "atividades.xhtml";
	}

	public void mudarElemento(_Elemento ele) {
		System.out.println("id da opcao do elemento " + ele.getOpcao().getId() + ele.getOpcao().getImagem());
		for (int i = 0; i < _opcoes.size(); i++) {
			if (ele.getPosicao() != 0) {
				if (ele.getOpcao().getId() == _opcoes.get(i).getId()) {
					if (i + 1 == _opcoes.size())
						ele.setOpcao(_opcoes.get(0));
					else
						ele.setOpcao(_opcoes.get(i + 1));
					System.out.println("Troca elemento");
					break;
				}
			}
		}
	}

	public void limparCenario() {
		for (_Elemento e : _elementos) {
			e.setOpcao(_opcoes.get(0));
			if (e.getPosicao() == 0)
				e.setOpcao(_opcoes.get(1));
		}
	}

	public String verAtividade(_Atividade _ativ) {
		this._atividade = _ativ;
		this._elementos = _elementoDao.getElementosPorAtividade(_atividade);
		this._respostas = _respostaDao.listarRespostaPorAtividade(_atividade);
		System.out.println("lista ok");
		return "atividade.xhtml";
//		return "resposta.xhtml";
	}
	
	public String verResposta(){
		return "resposta.xhtml";
	}

	public String responderAtividade() {
		System.out.println(_atividade.getNome());
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("atividade", _atividade);
		return "responder.xhtml";
	}
	
	public _AtividadeDAO get_atividadeDao() {
		return _atividadeDao;
	}

	public void set_atividadeDao(_AtividadeDAO _atividadeDao) {
		this._atividadeDao = _atividadeDao;
	}

	public _OpcaoDAO get_opcaoDao() {
		return _opcaoDao;
	}

	public void set_opcaoDao(_OpcaoDAO _opcaoDao) {
		this._opcaoDao = _opcaoDao;
	}

	public _ElementoDAO get_elementoDao() {
		return _elementoDao;
	}

	public void set_elementoDao(_ElementoDAO _elementoDao) {
		this._elementoDao = _elementoDao;
	}

	public _Atividade get_atividade() {
		return _atividade;
	}

	public void set_atividade(_Atividade _atividade) {
		this._atividade = _atividade;
	}

	public List<_Opcao> get_opcoes() {
		return _opcoes;
	}

	public void set_opcoes(List<_Opcao> _opcoes) {
		this._opcoes = _opcoes;
	}

	public List<_Elemento> get_elementos() {
		return _elementos;
	}

	public void set_elementos(List<_Elemento> _elementos) {
		this._elementos = _elementos;
	}

	public List<_Atividade> get_atividades() {
		return _atividades;
	}

	public void set_atividades(List<_Atividade> _atividades) {
		this._atividades = _atividades;
	}

	public List<_Resposta> get_respostas() {
		return _respostas;
	}

	public void set_respostas(List<_Resposta> _respostas) {
		this._respostas = _respostas;
	}

}
