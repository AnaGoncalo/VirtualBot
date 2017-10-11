package controle;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import dominio.Atividade;
import dominio.Cenario;
import dominio.Elemento;
import dominio.Opcao;
import persistencia.AtividadeDAO;
import persistencia.CenarioDAO;
import persistencia.ElementoDAO;

@ManagedBean
@SessionScoped
public class AtividadeBean {

	private List<Atividade> atividades;
	private Atividade atividade;
	private List<Elemento> elementos;
	private Cenario cenarioT;
	
	private AtividadeDAO atividadeDao;
	private CenarioDAO cenarioDao;
	private ElementoDAO elementoDao;
	private String texto = "texto";
	
	public AtividadeBean(){
		atividade = new Atividade();
		cenarioT = new Cenario();
		
		atividadeDao = new AtividadeDAO();
		cenarioDao = new CenarioDAO();
		elementoDao = new ElementoDAO();
		
		elementos = elementoDao.listarTodos();
		for(Elemento e: elementos){
			System.out.println(e.getCor());
		}
		
		listarAtividades();
	}
	
	public void listarAtividades(){
		atividades = atividadeDao.listarTodos();
	}
	
	public String addAtividade(){
		cenarioT.setElementos(elementos);
		cenarioDao.inserir(atividade.getCenario());
		
//		atividadeDao.inserir(atividade);
//		atividade = new Atividade();
//		listarAtividades();
		return "atividades.xhtml";
	}
	
	public void mudarElemento(Elemento ele){
		texto = "novotexto";
		if(ele.getObrigatoriedade().equals(Opcao.OPCIONAL)){
			ele.setObrigatoriedade(Opcao.OBRIGATORIO);
			ele.setCor("btn-success");
		}
		else if(ele.getObrigatoriedade().equals(Opcao.OBRIGATORIO)){
			ele.setObrigatoriedade(Opcao.PROIBIDO);
			ele.setCor("btn-danger");
		}
		else {
			ele.setObrigatoriedade(Opcao.OPCIONAL);
			ele.setCor("btn-default");
		}
	}
	
	public void limparCenario(){
		//problema.clear();
		for(Elemento e: atividade.getCenario().getElementos()){
			e.setObrigatoriedade(Opcao.OPCIONAL);
			e.setCor("btn-default");
		}
	}
	
	public String verAtividade(Atividade atividade){
		this.atividade = atividade;
		return "atividade.xhtml";
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
	
	
}
