package proyectoPSP;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

public class Voto implements Serializable{
	private String ip;
	private int port;
	private int numDeputado;
	private int valor;
	private Date data;
	private byte[] valorVotoEncriptado;

	public Voto() {
		super();
	}

	public Voto(String ip, int port, int numDeputado, int valor, Date data) {
		super();
		this.ip = ip;
		this.port = port;
		this.numDeputado = numDeputado;
		this.valor = valor;
		this.data = data;
	}

	public Voto(String ip, int port, int numDeputado, Date data, byte[] valorVotoEncriptado) {
		super();
		this.ip = ip;
		this.port = port;
		this.numDeputado = numDeputado;
		this.data = data;
		this.valorVotoEncriptado = valorVotoEncriptado;
	}

	public byte[] getValorVotoEncriptado() {
		return valorVotoEncriptado;
	}

	public void setValorVotoEncriptado(byte[] valorVotoEncriptado) {
		this.valorVotoEncriptado = valorVotoEncriptado;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getNumDeputado() {
		return numDeputado;
	}

	public void setNumDeputado(int numDeputado) {
		this.numDeputado = numDeputado;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Voto [ip=" + ip + ", port=" + port + ", numDeputado=" + numDeputado + ", valor=" + valor + ", data="
				+ data + ", valorVotoEncriptado=" + Arrays.toString(valorVotoEncriptado) + "]";
	}

	

}
