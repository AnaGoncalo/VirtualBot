package dominio;

public enum Opcao {
	
	OBRIGATORIO("Obrigatorio"),
	OPCIONAL("Opcional"),
	PROIBIDO("Proibido");
	
	private String label;
	
	private Opcao(String label){
		this.label = label;
	}
	public String getLabel(){
		return label;
	}

}
