package persistencia;

import java.util.List;

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

}
