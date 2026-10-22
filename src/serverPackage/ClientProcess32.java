package serverPackage;

import CommonPackage.Operation;
import java.io.*;
import java.net.Socket;

public class ClientProcess32 extends Thread {
    private final Socket socket;
    private final int numero;

    public ClientProcess32(Socket socket, int numero) {
        this.socket = socket;
        this.numero = numero;
    }

    public void run() {
        try (Socket s = socket;
             // d'abord le flux de sortie, puis le flux d'entrée (sinon blocage)
             OutputStream os =socket.getOutputStream();
             ObjectOutputStream out =new ObjectOutputStream(os);

             InputStream is =socket.getInputStream();
             ObjectInputStream in =new ObjectInputStream(is)
        ) {
            while (true) {
                Operation op;
                try {
                    op = (Operation) in.readObject();
                } catch (EOFException e) {
                    System.out.println("Le client n°" + numero + " s'est déconnecté");
                    break;
                }
                if (op.getOperateur().equals("FIN")) {
                    System.out.println("Le client n°" + numero + " termine la communication");
                    break;
                }

                double a = op.getOperande1();
                String o = op.getOperateur();
                double b = op.getOperande2();
                String res;

                if (o.equals("+")) {
                    res = "" +(a + b);
                } else if (o.equals("-")) {
                    res = "" +(a - b);
                } else if (o.equals("*")) {
                    res = "" +(a * b);
                } else if (o.equals("/")) {
                    if (b ==0) {
                        res ="Erreur : impossible de diviser par 0";
                    } else {
                        res ="" + (a / b);
                    }
                } else {
                    res = "Erreur : opérateur invalide";
                }
                System.out.println("Client n°" + numero + " : " + a + " " + o + " " + b + " = " + res);

                // accès concurrent : compteur global synchronisé
                Compteur.incrementerEtAfficher(numero);

                out.writeObject(res);  //envoyer res au serveur
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erreur avec le client n°" + numero + " : " + e.getMessage());
        }
    }
}