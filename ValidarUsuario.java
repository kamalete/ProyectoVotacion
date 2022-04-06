package proyectoPSP;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class ValidarUsuario {

	private final String SHA = "SHA3-256";
	private String nome;
	private byte[] password;
	private byte[] hashedPassword;

	public ValidarUsuario(String nome, byte[] password) {
		this.nome = nome;
		this.password = password; // valor en claro
	}

	public byte[] hash() {

		byte[] base64Digest = null;
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(SHA);
			byte[] base64pass = Base64.getEncoder().encode(this.password);
			byte[] digest = messageDigest.digest(base64pass);
			base64Digest = Base64.getEncoder().encode(digest);
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return base64Digest;
	}

	public boolean verificar(byte[] hash2) {

		byte[] extHash2 = this.calculateExternalHash(hash2);
		String h1 = new String(this.hashedPassword, 0, this.hashedPassword.length).trim();
		String h2 = new String(extHash2, 0, extHash2.length).trim();
		if (h1.equals(h2)) {
			return true;
		} else {
			return false;
		}
	}
	

	private byte[] calculateExternalHash(byte[] password) {

		byte[] base64Digest = null;
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(SHA);
			byte[] base64pass = Base64.getEncoder().encode(password);
			byte[] digest = messageDigest.digest(base64pass);
			base64Digest = Base64.getEncoder().encode(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return base64Digest;
	}


	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public byte[] getHashedPassword() {
		this.hashedPassword = this.hash();
		return this.hashedPassword;
	}

	public void setPassword(byte[] password) {
		this.password = password;
	}

}
