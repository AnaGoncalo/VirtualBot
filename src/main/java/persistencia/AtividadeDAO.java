package persistencia;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import dominio.Atividade;
import dominio.Elemento;
import util.JpaUtil;

public class AtividadeDAO extends DAO{

	public AtividadeDAO() {
		super(Atividade.class);
	}
	
	public List<Elemento> getElementosPorAtividade(Atividade atividade){
		EntityManager em = JpaUtil.getEntityManager();
        Query query = em.createQuery("SELECT ele from Atividade_Elemento ati_ele INNER JOIN Elemento ele ON "
        		+ "(ati_ele.elementos_id = ele.id) "
        		+ "where ati_ele.Atividade_id =:idAtividade ").
        		setParameter("idAtividade", atividade.getId());
        List<Elemento> lista = query.getResultList();
        em.close();
        return lista;
	}

}
