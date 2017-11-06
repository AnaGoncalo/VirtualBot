package persistencia;

import java.util.ArrayList;
import java.util.List;

import dominio._ElementoS;
import dominio._Resposta;

public class _ElementoSDAO extends DAO {

	public _ElementoSDAO() {
		super(_ElementoS.class);
	}
	
	public List<_ElementoS> getElementosPorResposta(_Resposta _resposta){
		List<_ElementoS> lista = new ArrayList();
		System.out.println("buscar elementos pela resposta: " + _resposta.getId());
		for(_ElementoS _ele : (List<_ElementoS>)listarTodos())
		{
			System.out.println(_ele.getResposta().getId() + " - " + _resposta.getId());
			if(_ele.getResposta().getId() == _resposta.getId()){
				lista.add(_ele);
				System.out.println("entra " + _ele.getPosicao() + _ele.getOpcao().getId());
			}
		}
		
        return lista;
	}
}
