package CommonPackage;

import java.io.Serializable;

// Objet échangé entre le client et le serveur.
// "implements Serializable" permet de le transformer en octets pour l'envoyer sur le réseau.
public class Operation implements Serializable {
    private static final long serialVersionUID = 1L; //version de la classe, identique des deux cotes

    private double operande1;
    private String operateur;
    private double operande2;

    public Operation(double operande1, String operateur, double operande2) {
        this.operande1 = operande1;
        this.operateur = operateur;
        this.operande2 = operande2;
    }
    public double getOperande1() {
        return operande1;
    }
    public String getOperateur() {
        return operateur;
    }
    public double getOperande2() {
        return operande2;
    }
}