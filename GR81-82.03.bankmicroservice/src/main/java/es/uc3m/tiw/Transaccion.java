package es.uc3m.tiw;

public class Transaccion {

	private long numeroTarjeta;
	private int cvv;
	private String fechaTarjeta;
	
	public String getFechaTarjeta() {
		return fechaTarjeta;
	}
	public void setFechaTarjeta(String fechaTarjeta) {
		this.fechaTarjeta = fechaTarjeta;
	}
	public long getNumeroTarjeta() {
		return numeroTarjeta;
	}
	public void setNumeroTarjeta(long numeroTarjeta) {
		this.numeroTarjeta = numeroTarjeta;
	}
	public int getCvv() {
		return cvv;
	}
	public void setCvv(int cvv) {
		this.cvv = cvv;
	}	
}
