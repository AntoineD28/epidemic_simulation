/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetapo;

import java.util.ArrayList;

/**
 * Classe abstraite modélisant un modèle 
 * @author lenymetzger
 */
public abstract class Modele {
    
    /* temps de la simulation */
    protected int temps;
    /* parametre beta */
    protected double beta;
    /* parametre gamma */
    protected double gamma;
    /* Monde utilisé par le modele */
    protected Monde m;
    protected boolean confinement;
    public static boolean masque;
    protected boolean quarantaine;
    protected boolean vaccination;
    /* Proba de se faire vacciner si ce dernier est activé */
    protected double probaVaccin;

    
    /**
     * Constructeur avec tous les pramètres du modele SEIR  (spacialisation, politique publique et vaccination)
     * @param b parametre de transmission
     * @param g parametre de guérison
     * @param s personnes saines
     * @param e personnes exposées
     * @param i personnes infectées
     * @param r personnes retirées
     * @param t temps (en jours)
     * @param N longueur du monde
     * @param M largeur du monde
     * @param c confinement
     * @param ma port du masque
     * @param q quarantaine
     * @param v vaccination 
     * @param pv Probabilité d'être vacciné à un pas de temps
     */
    public Modele(double b, double g, double s, double e, double i, double r, int t, int N, int M, boolean c, 
            boolean ma, boolean q, boolean v, double pv) {
        temps = t;
        beta = b; 
        gamma = g;
        confinement = c;
        masque = ma;
        quarantaine = q;
        vaccination = v;
        probaVaccin = pv;
        m = new Monde(s,e,i,r,N,M);
    }
    
    
    
    
    /**
     * Méthode qui gère les nouvelles guérisons (marche avec ou sans l'utilisation de la spacialisation).
     * @param gueris Nombre de nouvelles personnes guéris
     */
    public void guerison(int gueris){
        Population p = m.getPopulation();
        ArrayList<Personne> Inf = p.getI(); // Liste des personnes infectées
        ArrayList<Personne> Recovered = p.getR(); // Liste des personnes retirées
        
        for (int i=0; i<gueris; i++){
            Recovered.add(Inf.get(0)); //On fait passer 1 personne infectée dans le tableau des retirées
            Inf.remove(0); //On supprime cet infecté
        }
    }
    
    /**
     * Méthode qui gère les nouvelles vaccinations.
     * @param nbVaccins nombre de nouvelles personnes vaccinées
     */
    public void vaccination(int nbVaccins){
        Population p = m.getPopulation();
        ArrayList<Personne> Sain = p.getS();    // Liste des personnes saines
        ArrayList<Personne> Recovered = p.getR();   // Liste des personnes retirées
        
        for (int i=0; i<nbVaccins; i++){
            Recovered.add(Sain.get(0)); //On fait passer 1 personne saine dans les personnes guéris
            Sain.remove(0); //On supprime cette personne saine
        }
    }
    
    /**
     * Méthode qui gère les nouvelles infections.
     * @param inf Nombre de nouvelles personnes infectées
     */
    public abstract void infection(int inf);
    
    /**
     * Procédure qui réalise la simulation du modèle.
     * Affiche chaque jour le nouveau nombre de personnes pour chaque catégories
     */
    public abstract void simulation();
    
    /**
     * Procédure qui réalise la simulation du modèle SIR avec la spacialisation.
     * Affiche chaque jour le nouveau nombre de personnes pour chaque catégories
     */
    public abstract void simulationSpacialisation();
}
