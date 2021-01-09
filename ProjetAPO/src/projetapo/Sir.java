/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetapo;

/**
 * Classe pour le modèle SIR
 *
 * @author Antoine
 */
public class Sir {

    private int temps;
    private double beta;
    private double gamma;
    private Population p;

    /**
     * Constructeur par défaut
     */
    public Sir() {
        p = new Population();
        temps = 100;
        beta = 2;
        gamma = 0.5;
    }

    /**
     * Constructeur avec tous les pramètres
     * @param b transmission
     * @param g guérison
     * @param s saines
     * @param i infectées
     * @param r retirées
     * @param t temps (en jours)
     */
    public Sir(double b, double g, double s, double i, double r, int t) {
        p = new Population(s, i, r);
        temps = t;
        beta = b;
        gamma = g;
    }

    /**
     * Procédure qui réalise la simulation du modèle SIR.
     * Affiche chaque jour le nouveau nombre de personnes pour chaque catégories
     */
    public void simulationSIR() {
        double S = p.getS(), I = p.getI(), R = p.getR();
        double new_infecte = 0;
        double N = p.getS() + p.getI() + p.getR();
        
        System.out.println("Jour 0 : S = " + S + " I = " + I + " R = " + R);

        for (int j = 1; j <= temps; j++) {
            new_infecte = beta * S * I;
            
            // Gère le cas d'une épidémie 'violente' pour ne pas aggrandir la population totale
            if (new_infecte > S){
                p.setS(0);
                p.setI(I + S - (gamma * I));
            }
            else {
                p.setS(S - new_infecte);
                p.setI(I + new_infecte - (gamma * I));
            }
            p.setR(R + gamma * I);
            
            S = p.getS();
            I = p.getI();
            R = p.getR();
            
            System.out.println("Jour " + j + " : S = " + S + " I = " + I + " R = " + R);
        }
    }

}
