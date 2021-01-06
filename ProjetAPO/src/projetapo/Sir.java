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
        double s = 0;
        double i = 0;
        
        System.out.println("Jour 0 : S = " + p.getS() + " I = " + p.getI()
                + " R = " + p.getR());

        for (int j = 1; j <= temps; j++) {
            s = p.getS();
            i = p.getI();
            p.setS(s + (-beta * s * i));
            p.setI(i + (beta * s * i) - (gamma * i));
            p.setR(p.getR() + gamma * i);
            System.out.println("Jour " + j + " : S = " + p.getS() + " I = " + p.getI()
                    + " R = " + p.getR());
        }
    }

}
