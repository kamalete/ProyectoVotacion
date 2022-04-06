package proyectoPSP;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Votante implements Serializable{

	private String nombre;
	private byte[] contrase�aHash;

	public Votante(String nombre, byte[] contrase�aHash) {
		super();
		this.nombre = nombre;
		this.contrase�aHash = contrase�aHash;
		this.contrase�aHash = setHash();
	}

	
	
	public Votante() {
		super();
	}

	 public byte[] setHash() {

	        MessageDigest messageDigest = null;
			try {
				messageDigest = MessageDigest.getInstance("SHA-256");
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        byte[] res = messageDigest.digest(this.contrase�aHash);
	        return res;
	    }

	    public boolean compararHash(byte[] novoHash) {

	        return (this.contrase�aHash == novoHash) ? true : false;
	    }
	    
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public byte[] getContrase�aHash() {
		return contrase�aHash;
	}

	public void setContrase�aHash(byte[] contrase�aHash) {
		this.contrase�aHash = contrase�aHash;
	}

	@Override
	public String toString() {
		return "Votante [nombre=" + nombre + ", contrase�aHash=" + Arrays.toString(contrase�aHash) + "]";
	}

}
