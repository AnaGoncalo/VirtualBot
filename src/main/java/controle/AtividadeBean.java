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
	private Elemento e;
	
	private AtividadeDAO atividadeDao;
	private ElementoDAO elementoDao;
	private String texto = "texto";
	
	public AtividadeBean(){
		atividade = new Atividade();
		
		atividadeDao = new AtividadeDAO();
		elementoDao = new ElementoDAO();
		
//		elementos = elementoDao.listarTodos();
//		for(Elemento e: elementos){
//			System.out.println(e.getCor());
//		}
//		
		elementos = new ArrayList();
		for(int i = 0; i<24; i++){
			e = new Elemento(); 
			e.setId( ((Elemento) elementoDao.pesquisarPorId(1l)).getId());
			e.setCor( ((Elemento) elementoDao.pesquisarPorId(1l)).getCor());
			e.setObrigatoriedade( ((Elemento) elementoDao.pesquisarPorId(1l)).getObrigatoriedade());
			elementos.add(e);
		}
		
		listarAtividades();
	}
	
	public void listarAtividades(){
		atividades = atividadeDao.listarTodos();
	}
	
	public String addAtividade(){
		atividade.setElementos(elementos);
		atividadeDao.inserir(atividade);
		atividade = new Atividade();
		listarAtividades();
		return "atividades.xhtml";
	}
	
	public void mudarElemento(Elemento ele){
		texto = "novotexto";
		if(ele.getObrigatoriedade().equals(Opcao.OPCIONAL)){
			ele.setId(3l);
			ele.setObrigatoriedade(Opcao.OBRIGATORIO);
			ele.setCor("btn-success");
		}
		else if(ele.getObrigatoriedade().equals(Opcao.OBRIGATORIO)){
			ele.setId(2l);
			ele.setObrigatoriedade(Opcao.PROIBIDO);
			ele.setCor("btn-danger");
		}
		else {
			ele.setId(1l);
			ele.setObrigatoriedade(Opcao.OPCIONAL);
			ele.setCor("btn-default");
		}
	}
	
	public void limparCenario(){
		for(Elemento e: elementos){
			e.setId(1l);
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
