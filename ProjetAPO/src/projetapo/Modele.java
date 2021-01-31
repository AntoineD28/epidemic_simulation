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
    
    /**
     * temps de la simulation (en jours)
     */
    protected int temps;

    /**
     * parametre beta
     */
    protected double beta;

    /**
     * parametre gamma
     */
    protected double gamma; 

    /**
     * Monde utilisé par le modele
     */
    protected Monde m; 

    /**
     * Est ce que le confinement est activé ?
     */
    protected boolean confinement;

    /**
     * Est ce que le port du masque est activé ?
     */
    public static boolean masque; 

    /**
     * Est ce que la mise en quarantaine est activé ?
     */
    public static boolean quarantaine; 

    /**
     * Est ce que la vaccination est activé ?
     */
    protected boolean vaccination; 
    
    /**
     * Durée de la mise en quarantaine
     */
    protected int tempsQuarantaine; 

    /**
     * Probabbilité de se faire vacciner
     */
    protected double probaVaccin; 

    /**
     * Utilisé pour avoir les données dans l'interface graphique
     */
    public Object[][] data; 

    
    /**
     * Constructeur avec tous les pramètres d'un modele
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
     * @param tq Durée de la mise en quarantaine
     * @param pv Probabilité d'être vacciné à un pas de temps
     */
    public Modele(double b, double g, double s, double e, double i, double r, int t, int N, int M, boolean c, 
            boolean ma, boolean q, boolean v, int tq, double pv) {
        temps = t;
        beta = b; 
        gamma = g;
        confinement = c;
        masque = ma;
        quarantaine = q;
        vaccination = v;
        tempsQuarantaine = tq;
        probaVaccin = pv;
        data = new Object[t+1][6];
        m = new Monde(s,e,i,r,N,M);
    }
    
    /**
     * 
     * @return le tableau data
     */
    public Object[][] getData(){
        return data;
    }
    
    
    /**
     * Méthode qui gère les nouvelles personnes retirées.
     * @param retiré Nombre de nouvelles personnes retires
     */
    public void retire(int retiré){
        Population p = m.getPopulation();
        ArrayList<Personne> Inf = p.getI(); // Liste des personnes infectées
        ArrayList<Personne> Recovered = p.getR(); // Liste des personnes retirées
        
        for (int i=0; i<retiré; i++){
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
     * Méthode qui gère la durée de quarantaine des personnes infectées.
     */
    public void quarantaine(){
        Population p = m.getPopulation();
        ArrayList<Personne> Inf = p.getI();    // Liste des personnes Infectées
        int nq;
        Personne pers;
        
        for (int i=0; i<Inf.size(); i++){ // Pour toutes les personnes infectées
            pers = Inf.get(i);
            if(pers.getQuarantaine()){  // Si la personne est en quarantaine
                nq = pers.getJourQuarantaine() + 1; // Nouvelle durée de la quarantaine
                if(nq >= tempsQuarantaine){   // Si elle est resté assez de temps en quarantaine, alors elle n'est plus en quarantaine
                    pers.setQuarantaine(false);
                    pers.setJourQuarantaine(0);
                }
                else 
                    pers.setJourQuarantaine(nq); // Si elle n'est pas resté assez de temps, on met à jour la durée de sa quarantaine
            }
        }
    }
    
    /**
     * Procédure qui réalise la simulation des différents modèles.
     * Affiche chaque jour le nouveau nombre de personnes pour chaque catégories
     */
    public abstract void simulation();
}
