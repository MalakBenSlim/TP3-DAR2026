package serverPackage;

import java.io.*;
import java.net.Socket;

public class ClientProcess31 extends Thread {
    private final Socket socket;
    private final int numero;

    public ClientProcess31(Socket socket, int numero){
        this.socket=socket;
        this.numero=numero;
    }
    public void run(){   //que fait le serveur
        try (
             InputStream is = socket.getInputStream();
             InputStreamReader isr = new InputStreamReader(is);
             BufferedReader in = new BufferedReader(isr);

             OutputStream os = socket.getOutputStream();
             PrintWriter out = new PrintWriter(os, true);) {
            // envoyer ce message au client
            out.println("Bienvenue, vous êtes le client n°" + numero);
            while (true) {
                String o1 = in.readLine();
                System.out.println("Le client n° "+numero +" a envoyé :"+o1);
                if (o1 == null) {
                    System.out.println("Le client n°"+numero +" s'est déconnecté");
                    break;
                }
                if (o1.equalsIgnoreCase("FIN")) {
                    System.out.println("Le client n° "+numero+" est déconnecté");
                    break;
                }
                String op = in.readLine();
                String o2 = in.readLine();
                if (op == null || o2 == null) {
                    System.out.println("Le client n°" + numero + " a coupé la connexion");
                    break;
                }
                String res;
                try {
                    // on va convertir o1 et o2 de chaines de caracters au réel pour calculer
                    double a = Double.parseDouble(o1);
                    double b = Double.parseDouble(o2);
                    if (op.equals("+")) {
                        res = "" + (a + b);
                    } else if (op.equals("-")) {
                        res = "" + (a - b);
                    } else if (op.equals("*")) {
                        res = "" + (a * b);
                    } else if (b == 0) {
                        res = "Erreur : impossible division par 0";
                    } else {
                        res = "" + (a / b);
                    }
                    System.out.println("Recu du client n° "+numero+": " + a + " " + op + " " + b + "= " + res);
                }catch(NumberFormatException e){
                    res="Erreur: opérande invalide";
                }
                out.println(res); // envoyer le resultat au client


            }
        } catch (IOException e) {
            System.out.println("Erreur avec le client n°" + numero + " : " + e.getMessage());
        }
    }
}
