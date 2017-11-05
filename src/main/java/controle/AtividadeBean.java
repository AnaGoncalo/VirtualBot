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
import persistencia.AtividadeDAO;
import persistencia.ElementoDAO;
import persistencia._AtividadeDAO;
import persistencia._ElementoDAO;
import persistencia._OpcaoDAO;

@ManagedBean
@SessionScoped
public class AtividadeBean {

	private List<Atividade> atividades;
	private Atividade atividade;
	private List<Elemento> elementos;
	private Elemento e;
	
	private AtividadeDAO atividadeDao;
	private ElementoDAO elementoDao;
	private String texto = "texto";
	
	private _AtividadeDAO _atividadeDao;
	private _OpcaoDAO _opcaoDao;
	private _ElementoDAO _elementoDao;
	private _Atividade _atividade;
	private List<_Atividade> _atividades;
	private List<_Opcao> _opcoes;
	private List<_Elemento> _elementos;
	
	public AtividadeBean(){
		_atividadeDao = new _AtividadeDAO();
		_opcaoDao = new _OpcaoDAO();
		_elementoDao = new _ElementoDAO();
		_atividade = new _Atividade();
		_opcoes = _opcaoDao.listarTodos();
		_elementos = new ArrayList();
		_atividadeDao.listarTodos();
		for(int i = 0; i < 60; i++){
			_Elemento _e = new _Elemento();
			_e.setPosicao(i);
			_e.setOpcao( _opcoes.get(0));
			_elementos.add(_e);
		}
		
		atividade = new Atividade();
		
		atividadeDao = new AtividadeDAO();
		elementoDao = new ElementoDAO();
		
		listarAtividades();
	}
	
	public void listarAtividades(){
		//limparCenario();
		_atividade = new _Atividade();
		atividades = atividadeDao.listarTodos();
		_atividades = _atividadeDao.listarTodos();
	}
	
	public String addAtividade(){
		_atividadeDao.inserir(_atividade);
		System.out.println("atiidade : " + _atividade.getNome());
		_atividade = _atividadeDao.buscarPorTitulo(_atividade.getNome());
		System.out.println("id da atividade cadastrada : " + _atividade.getId());
		for(_Elemento _ele : _elementos){
			_ele.setAtividade(_atividade);
			_elementoDao.inserir(_ele);
		}
		listarAtividades();
		return "atividades.xhtml";
	}
	
	public void mudarElemento(_Elemento ele){
		System.out.println("id da opcao do elemento " + ele.getOpcao().getId() + ele.getOpcao().getImagem());
		for(int i = 0; i < _opcoes.size(); i ++){
			if(ele.getOpcao().getId() == _opcoes.get(i).getId()){
				if(i+1 == _opcoes.size())
					ele.setOpcao(_opcoes.get(0));
				else
					ele.setOpcao(_opcoes.get(i+1));
				System.out.println("Troca elemento");
				break;
			}
		}		
	}
	
	public void limparCenario(){
		for(_Elemento e: _elementos){
			_Elemento _e = new _Elemento();
			_e.setOpcao( _opcoes.get(0));
			_elementos.add(_e);
		}
	}
	
	public String verAtividade(_Atividade _ativ){
		this._atividade = _ativ;
		this._elementos = _elementoDao.getElementosPorAtividade(_atividade);
		System.out.println("lista ok");
		return "atividade.xhtml";
	}
	
	public String responderAtividade(_Atividade ati){
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("atividade", ati);
		return "responder.xhtml";
	}
	
	public Atividade getAtividade(){
		return atividade;
	}
	
	public void setAtividade(Atividade atividade){
		this.atividade = atividade;
	}

	public List<Atividade> getAtividades() {
		return atividades;
	}

	public void setAtividades(List<Atividade> atividades) {
		this.atividades = atividades;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public List<Elemento> getElementos() {
		return elementos;
	}

	public void setElementos(List<Elemento> elementos) {
		this.elementos = elementos;
	}

	public AtividadeDAO getAtividadeDao() {
		return atividadeDao;
	}

	public void setAtividadeDao(AtividadeDAO atividadeDao) {
		this.atividadeDao = atividadeDao;
	}

	public ElementoDAO getElementoDao() {
		return elementoDao;
	}

	public void setElementoDao(ElementoDAO elementoDao) {
		this.elementoDao = elementoDao;
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
		
}
