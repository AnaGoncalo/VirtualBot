package persistencia;

import java.util.List;

import dominio.Atividade;
import dominio._Atividade;

public class _AtividadeDAO extends DAO {

	public _AtividadeDAO() {
		super(_Atividade.class);
	}
	
	public _Atividade buscarPorTitulo(String titulo){
		System.out.println("nome da atividade " + titulo);
		for(_Atividade _ati : (List<_Atividade>)listarTodos()){
			if(_ati.getNome().equals(titulo)){
				System.out.println("achou");
				return _ati;
			}
		}
		return null;
	}
	
	public _Atividade ultimaAtividade(){
		List<_Atividade> lista = listarTodos();
		_Atividade ultima = null;
		for(_Atividade _ati: lista){
			ultima = _ati;
		}
		return ultima;
	}
}
