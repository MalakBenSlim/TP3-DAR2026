package serverPackage;

public class Compteur {
    private static int total = 0;

    // synchronized : un seul thread à la fois peut exécuter cette méthode
    public static synchronized int incrementerEtAfficher(int numeroClient) {
        total++;
        System.out.println("Compteur global = " + total + " (opération du client n°" + numeroClient + ")");
        return total;
    }
}