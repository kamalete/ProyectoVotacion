package proyectoPSP;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Parlamento {
	private static final int NUM_DEPUTADAS = 7;
	private static ExecutorService pool = Executors.newFixedThreadPool(NUM_DEPUTADAS);
	private static final int PORT = 9000;
	private static int numDeputada = 0;
	private static final String ALGORITHM = "RSA";

	public static void main(String[] args) throws IOException {

		
		KeyPairGenerator keys;
		ArrayList<Voto> votos = new ArrayList<Voto>();
		try(ServerSocket serverSocket = new ServerSocket(PORT)) {
			keys = KeyPairGenerator.getInstance(ALGORITHM);
			KeyPair keyPair = keys.generateKeyPair();
			PrivateKey pKey = keyPair.getPrivate();
			PublicKey pubKey = keyPair.getPublic();


			while (numDeputada <= NUM_DEPUTADAS) {
				System.out.println("Servidor a esperar conexión");
				Socket socket = serverSocket.accept();
				System.out.println("Deputad@ conectad@");
				ClientHandler clientHandler = new ClientHandler(socket, numDeputada, votos, keyPair);
				pool.execute(clientHandler);
				numDeputada++;

			}
		} catch (NoSuchAlgorithmException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
		}
		int votosAFavor = 0;
		int votosEnContra = 0;
		int votosAbstenidos = 0;

		for (Voto v : votos) {
			if (v.getValor() == 0) {
				votosAFavor++;
			} else if (v.getValor() == 1) {
				votosEnContra++;
			} else if (v.getValor() == 2) {
				votosAbstenidos++;
			}
		}

		System.out.println("----------RESULTADO DE LAS VOTACIONES----------");
		System.out.println("Si: " + votosAFavor + " No: " + votosEnContra + " Abstenciones: " + votosAbstenidos);
	}
}
