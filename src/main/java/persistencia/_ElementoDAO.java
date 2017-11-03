package persistencia;

import java.util.ArrayList;
import java.util.List;

import dominio._Atividade;
import dominio._Elemento;

public class _ElementoDAO extends DAO{

	public _ElementoDAO() {
		super(_Elemento.class);
	}
	
	public List<_Elemento> getElementosPorAtividade(_Atividade _atividade){
		List<_Elemento> lista = new ArrayList();
		System.out.println("buscar elementos pela atividade: " + _atividade.getId());
		for(_Elemento _ele : (List<_Elemento>)listarTodos())
		{
			System.out.println(_ele.getAtividade().getId() + " - " + _atividade.getId());
			if(_ele.getAtividade().getId() == _atividade.getId()){
				lista.add(_ele);
				System.out.println("entra " + _ele.getPosicao() + _ele.getOpcao().getId());
			}
		}
		
		
//		EntityManager em = JpaUtil.getEntityManager();
//        Query query = em.createQuery("SELECT _ele from _Elemento _ele INNER JOIN _Atividade _ati ON "
//        		+ "(_ele.atividade_id = _ati.id) "
//        		+ "where _ele.atividade_id =:idAtividade ").
//        		setParameter("idAtividade", _atividade.getId());
//        List<_Elemento> lista = query.getResultList();
//        em.close();
        return lista;
	}
}
