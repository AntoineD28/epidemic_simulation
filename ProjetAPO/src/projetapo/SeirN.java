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
public class SeirN {
    
    private int temps;
    private double beta;
    private double gamma;
    private double alpha;
    private double mu;
    private double eta;
    private Population p;

    /**
     * Constructeur par défaut
     */
    public SeirN() {
        p = new Population();
        temps = 100;
        beta = 2;
        gamma = 0.5;
        alpha = 0.5;
        mu = 0.05;
        eta = 0.07;
    }

    /**
     * Constructeur avec tous les pramètres
     * @param b transmission
     * @param a incubation
     * @param g guérison
     * @param u mort naturelle
     * @param n naissance
     * @param s saines
     * @param e exposé
     * @param i infectées
     * @param r retirées
     * @param t temps (en jours)
     */
    public SeirN(double b, double a, double g, double u, double n, double s, double e, double i, double r, int t) {
        p = new Population(s, e, i, r);
        temps = t;
        beta = b;
        gamma = g;
        alpha = a;
        mu = u;
        eta = n;
    }

    /**
     * Procédure qui réalise la simulation du modèle SEIR avec évolution de la population.
     * Affiche chaque jour le nouveau nombre de personnes pour chaque catégories
     */
    public void simulationSEIRN() {
        double S = p.getS(), E = p.getE(), I = p.getI(), R = p.getR();
        double new_expose;
        double N = p.getS() + p.getE() + p.getI() + p.getR();
      
        System.out.println("Jour 0 : Population = " + N + " S = " + S + " E = " + E + " I = " + I
                + " R = " + R);

        for (int j = 1; j <= temps; j++) {
            new_expose = beta * S * I;
            
            // Gère le cas d'une épidémie 'violente' pour ne pas aggrandir la population totale faussement
            if (new_expose > S){
                p.setS(0 + (eta * N) - (mu * S));
                p.setE(E + S - (alpha * E) - (mu * E));
            }
            else {
                p.setS(S - new_expose + (eta * N) - (mu * S));
                p.setE(E + new_expose - (alpha * E) - (mu * E));
            }
            p.setI(I + (alpha * E) - (gamma * I) - (mu * I));
            p.setR(R + (gamma * I) - (mu * R));
            
            S = p.getS();
            E = p.getE();
            I = p.getI();
            R = p.getR();
            N = p.getS() + p.getE() + p.getI() + p.getR();
            
            System.out.println("Jour " + j +  " Population = " + N + " S = " + S + " E = " + E + " I = " + I
                + " R = " + R);
        }
    }
}
