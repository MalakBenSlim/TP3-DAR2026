package clientPackage;

import CommonPackage.Operation;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client32 {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println("Je suis un client pas encore connecté ... ");

        InetAddress adresse = InetAddress.getByName("localhost");  //connaitre l'@IP du client
        InetSocketAddress serveur = new InetSocketAddress(adresse, 1234); //@IP avec port

        Socket socket = new Socket();
        socket.connect(serveur,5000);
        try (socket;
             OutputStream os = socket.getOutputStream();
             ObjectOutputStream out = new ObjectOutputStream(os);
             InputStream is = socket.getInputStream();
             ObjectInputStream in = new ObjectInputStream(is);
             Scanner sc = new Scanner(System.in)) {

            System.out.println("je suis un client connecté");
            while (true) {
                System.out.println("Donner l'opérande 1 (ou fin pour quitter): ");
                String o1 = sc.next();
                if (o1.equalsIgnoreCase("fin")) {
                    out.writeObject(new Operation(0, "FIN", 0));
                    break;
                }
                System.out.println("Donner l'opérateur (+ - * /): ");
                String op = sc.next();
                System.out.println("Donner l'opérande 2: ");
                String o2 = sc.next();

                if (!(op.equals("+") || op.equals("-") || op.equals("*") || op.equals("/"))) {
                    System.out.println("Opérateur incorrect, réessayez.");
                    continue;
                }
                double a, b;
                try {
                    a = Double.parseDouble(o1);
                    b = Double.parseDouble(o2);
                } catch (NumberFormatException e) {
                    System.out.println("Opérande invalide, réessayez.");
                    continue;
                }
                // creer l'objet et envoyer au ClientProcess
                out.writeObject(new Operation(a, op, b));

                String res = (String) in.readObject(); //recoit resultat depuis ClientProcess
                System.out.println("Resultat: " + res);
            }
            System.out.println("Fin de la communication");
        }
    }
}