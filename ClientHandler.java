package proyectoPSP;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class ClientHandler implements Runnable {
	private static final String ALGORITHM = "RSA";
	private int numDeputada;
	private Socket socket;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private KeyPair keyPair;
	private List<Voto> votos;
	private List<Votante> votantes;

	public ClientHandler(Socket socket, int numDeputada, List<Voto> votos, KeyPair keyPair) {
		this.socket = socket;
		this.numDeputada = numDeputada;
		this.votos = votos;
		this.keyPair = keyPair;
		try {
			output = new ObjectOutputStream(this.socket.getOutputStream());
			input = new ObjectInputStream(this.socket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private List<Votante> rellenarLista() {
		votantes = new ArrayList<>();
		ValidarUsuario validacion = new ValidarUsuario("votante", "a".getBytes());
		byte[] contraseñaHash = validacion.hash();
		votantes.add(new Votante("Manuel", contraseñaHash));
		votantes.add(new Votante("Ana", contraseñaHash));
		return votantes;
	}

	@Override
	public void run() {
		try {
			List<Votante> votantes = this.rellenarLista();
			Votante votante = (Votante) input.readObject();
//				ValidarUsuario validacion = new ValidarUsuario(votanteComparacion.getNombre(), votanteComparacion.getContraseñaHash());
//				byte[] contraseñaHash = validacion.hash();
//					Arrays.equals(byte[], byte[])
//		        votante.getNombre().equals(votanteComparacion.getNombre())
				// modificar os if's para que busque deputadas
				boolean encontrado = buscarVotante(votante);
				if (encontrado == true) {
					PublicKey publicKey = keyPair.getPublic();
					output.writeObject((PublicKey) publicKey);
					// Recibir un obxecto Voto do cliente
					// Voto voto = (Voto) input.readObject();
					// Abro voto, recollo o valor encriptado e desencríptoo
					// Xa teño 0, 1 ou 2
					// Actualizo o obxecto voto co "ovo" Valor
					// Gárdoo na lista para calcular ao final

					Voto voto = (Voto) input.readObject();

					Cipher cipher = Cipher.getInstance(ALGORITHM);
					cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
					byte[] decifrado = cipher.doFinal(voto.getValorVotoEncriptado());
					String votoEnString = new String(decifrado, 0, decifrado.length).trim();

					voto.setValor(Integer.valueOf(votoEnString));

					votos.add(voto);
					// remover despois cando valide correctamente
				}
			

		} catch (ClassNotFoundException | IOException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private boolean buscarVotante(Votante votanteBuscado) {
		boolean encontrado = false;
		for (Votante votante : votantes) {
			if (votante.getNombre().equals(votanteBuscado.getNombre())) {
				// verificar hashes
				if (Arrays.equals(votanteBuscado.getContraseñaHash(), votante.getContraseñaHash())) {
					encontrado = true;
					break;
				}
			}

		}

		return encontrado;
	}
}
