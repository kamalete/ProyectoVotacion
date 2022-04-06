package proyectoPSP;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Votante implements Serializable{

	private String nombre;
	private byte[] contraseñaHash;

	public Votante(String nombre, byte[] contraseñaHash) {
		super();
		this.nombre = nombre;
		this.contraseñaHash = contraseñaHash;
		this.contraseñaHash = setHash();
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
	        byte[] res = messageDigest.digest(this.contraseñaHash);
	        return res;
	    }

	    public boolean compararHash(byte[] novoHash) {

	        return (this.contraseñaHash == novoHash) ? true : false;
	    }
	    
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public byte[] getContraseñaHash() {
		return contraseñaHash;
	}

	public void setContraseñaHash(byte[] contraseñaHash) {
		this.contraseñaHash = contraseñaHash;
	}

	@Override
	public String toString() {
		return "Votante [nombre=" + nombre + ", contraseñaHash=" + Arrays.toString(contraseñaHash) + "]";
	}

}
