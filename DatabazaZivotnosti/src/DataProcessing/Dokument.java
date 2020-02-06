package DataProcessing;

public class Dokument {
	String dokument_nazov;
	String relativna_cesta;
	String typ_suboru;
	Integer Verzia;

	public Dokument(String dokumentnazov,String relativnacesta, String typsuboru, Integer verzia) {
		dokument_nazov = dokumentnazov;
		relativna_cesta = relativnacesta;
		typ_suboru = typsuboru;
		Verzia = verzia;
	}
	public String getDokument_nazov() {
		return dokument_nazov;
	}
	public void setDokument_nazov(String dokument_nazov) {
		this.dokument_nazov = dokument_nazov;
	}
	public String getRelativna_cesta() {
		return relativna_cesta;
	}
	public void setRelativna_cesta(String relativna_cesta) {
		this.relativna_cesta = relativna_cesta;
	}
	public String getTyp_suboru() {
		return typ_suboru;
	}
	public void setTyp_suboru(String typ_suboru) {
		this.typ_suboru = typ_suboru;
	}
	public Integer getVerzia() {
		return Verzia;
	}
	public void setVerzia(Integer verzia) {
		Verzia = verzia;
	}
	
}
