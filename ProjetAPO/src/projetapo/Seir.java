/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetapo;

/**
 *
 * @author Antoine
 */
public class Seir {
    
    private int temps;
    private double beta;
    private double gamma;
    private double alpha;
    private Population p;

    /**
     * Constructeur par défaut
     */
    public Seir() {
        p = new Population();
        temps = 100;
        beta = 2;
        gamma = 0.5;
        alpha = 0.5;
    }

    /**
     * Constructeur avec tous les pramètres
     * @param b transmission
     * @param a incubation
     * @param g guérison
     * @param s saines
     * @param e exposé
     * @param i infectées
     * @param r retirées
     * @param t temps (en jours)
     */
    public Seir(double b, double a, double g, double s, double e, double i, double r, int t) {
        p = new Population(s, e, i, r);
        temps = t;
        beta = b;
        gamma = g;
        alpha = a;
    }

    /**
     * Procédure qui réalise la simulation du modèle SEIR.
     * Affiche chaque jour le nouveau nombre de personnes pour chaque catégories
     */
    public void simulationSEIR() {
        double S = p.getS(), E = p.getE(), I = p.getI(), R = p.getR();
        double new_expose;
        double N = p.getS() + p.getE() + p.getI() + p.getR();
      
        System.out.println("Jour 0 : S = " + S + " E = " + E + " I = " + I
                + " R = " + R);

        for (int j = 1; j <= temps; j++) {
            new_expose = beta * S * I;
            
            // Gère le cas d'une épidémie 'violente' pour ne pas aggrandir la population totale
            if (new_expose > S){
                p.setS(0);
                p.setE(E + S - (alpha * E));
            }
            else {
                p.setS(S - new_expose);
                p.setE(E + new_expose - (alpha * E));
            }
            p.setI(I + (alpha * E) - (gamma * I));
            p.setR(R + (gamma * I));
            
            S = p.getS();
            E = p.getE();
            I = p.getI();
            R = p.getR();
            
            System.out.println("Jour " + j +  " : S = " + S + " E = " + E + " I = " + I
                + " R = " + R);
        }
    }
    
}
