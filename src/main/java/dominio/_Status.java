package dominio;

public enum _Status {
	
	OBRIGATORIO("Obrigatorio"),
	OPCIONAL("Opcional"),
	PROIBIDO("Proibido");
	
	private String label;
	
	private _Status(String label){
		this.label = label;
	}
	public String getLabel(){
		return label;
	}

}
