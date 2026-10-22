package serverPackage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server_MT32 {


    public static void main(String[] args) {
        int numero = 0;
        try (ServerSocket serverSocket = new ServerSocket(1234)) {
            System.out.println("Le serveur attend la connexion de clients…");
            while (true) {
                Socket socket = serverSocket.accept();
                numero++;
                System.out.println("Client n°" + numero + " connecté, adresse : " + socket.getRemoteSocketAddress());
                new ClientProcess32(socket, numero).start();
            }
        } catch (IOException e) {
            System.out.println("Erreur côté serveur : " + e.getMessage());
        }
    }
}