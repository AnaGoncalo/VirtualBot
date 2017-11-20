package persistencia;

import java.util.ArrayList;
import java.util.List;

import dominio._Atividade;
import dominio._Resposta;

public class _RespostaDAO extends DAO {
	
	public _RespostaDAO() {
		super(_Resposta.class);
	}
	
	public _Resposta ultimaResposta(){
		List<_Resposta> respostas = listarTodos();
		_Resposta ultima = null;
		for(_Resposta _res: respostas){
			ultima = _res;
		}
		return ultima;
	}
	
	public List<_Resposta> listarRespostaPorAtividade(_Atividade atividade){
		System.out.println("Resposta DAO: " + atividade.getId());
		List<_Resposta> respostas = new ArrayList();
		for(_Resposta res: (List<_Resposta>) listarTodos()){
			if(res.getAtividade().getId() == atividade.getId()){
				System.out.println("Entra na lista " + res.getResultado());
				respostas.add(res);
			}
		}
		
		return respostas;
	}

}
