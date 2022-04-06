package proyectoPSP;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.Date;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Deputada implements Serializable {
	private static final String HOST = "127.0.0.1";
	private static final int PORT = 9000;
	private static final String ALGORITHM = "RSA";

	public static void main(String[] args) throws IOException {
		Socket socket = new Socket(HOST, PORT);
		ObjectInputStream input;
		ObjectOutputStream output;
		try {
			
			input = new ObjectInputStream(socket.getInputStream());
			output = new ObjectOutputStream(socket.getOutputStream());

			// Hacemos una validacion del usuario, enviamos la contraseña hasheada y en el
			// servidor
			// la recibimos para compararla
			String nombre = "Manuel";
			String contraseña = "a";

			ValidarUsuario validacion = new ValidarUsuario(nombre, contraseña.getBytes());

			byte[] contraseñaHash = validacion.hash();

			Votante votante = new Votante(nombre, contraseñaHash);

			output.writeObject((Votante) votante);

//			NO SE PUEDE CREAR AQUI EL PAR DE LLAVES, TENGO QUE ENVIAR LA PUBLICA DESDE EL SERVIDOR 
//			PARA CODIFICAR EL VALOR DEL VOTO, ENVIARLO CODIFICADO Y DESCIFRARLO EN EL SERVIDOR
//			CUANDO LO TENGA DESCIFRADO, HAGO UNA LISTA DE VOTOS, RECORRO LA LISTA RECOGIENDO LOS VALORES
//			DE LOS VOTOS Y HAGO UN PRINT COMO EL DEL EJERCICIO

//			byte[] publicKeyBytes = (byte[]) input.readObject();
			PublicKey publicKey = (PublicKey) input.readObject();
			
			Integer valorVoto = new Random().nextInt(2);//(int) (Math.random() * 3);
			

			Voto voto = new Voto(HOST, PORT, 1, valorVoto, new Date());
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);

			byte[] cifrado = cipher.doFinal(String.valueOf(voto.getValor()).getBytes());
			
			Voto voto2 = new Voto(HOST, PORT, 1, new Date(), cifrado);
			output.writeObject(voto2);

		} catch (IOException | ClassNotFoundException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException  e) {
			e.printStackTrace();
		} finally {
			socket.close();
		}

	}

}
