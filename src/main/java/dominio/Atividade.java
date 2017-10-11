package dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Atividade implements Serializable {
	
private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nome;
    private String dificuldade;
    private String descricao;
//    private Cenario cenario;
    
    @ManyToMany
    private List<Elemento> elementos;
    
    public Atividade(){
//    	cenario = new Cenario();
    	elementos = new ArrayList();
//		for(int i = 0; i<3; i++){
//			Elemento e = new Elemento();
//			e.setId(1l);
//			e.setObrigatoriedade(Opcao.OPCIONAL);
//			e.setCor("btn-default");
//			elementos.add(e);
//		}
    }
    
    public int getQtdRespostas(){
    	return 0;
    }
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDificuldade() {
		return dificuldade;
	}
	public void setDificuldade(String dificuldade) {
		this.dificuldade = dificuldade;
	}
	public String getDescricao(){
		return descricao;
	}
	public void setDescricao(String descricao){
		this.descricao = descricao;
	}
//	public Cenario getCenario() {
//		return cenario;
//	}
//	public void setCenario(Cenario cenario) {
//		this.cenario = cenario;
//	}
	public List<Elemento> getElementos() {
		return elementos;
	}
	public void setElementos(List<Elemento> elementos) {
		this.elementos = elementos;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
