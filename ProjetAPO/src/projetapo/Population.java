/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetapo;

import java.util.ArrayList;

/**
 * Classe servant à représenter une population
 *
 * @author Antoine
 */
public class Population {

    /**
     * Liste des personnes saines
     */
    private ArrayList<Personne> S;
    
    /**
     * Liste des personnes exposées
     */
    private ArrayList<Personne> E; 
    
    /**
     * Liste des personnes infectées
     */
    private ArrayList<Personne> I;
    
    /**
     * Liste des personnes retirées
     */
    private ArrayList<Personne> R; 
 

    /**
     * Constructeur pour une simulation avec 3 catégories (SIR)
     *
     * @param s nombre de personnes saines
     * @param i nombre de personnes infectées
     * @param r nombre de personnes retirées
     */
    public Population(double s, double i, double r) {
        Personne pers;
        S = new ArrayList<Personne>();
        I = new ArrayList<Personne>();
        R = new ArrayList<Personne>();
        double rand; 
        boolean m = false;
        boolean q = false;
        
        for (int j=1; j<=s; j++){ //On ajoute s personnes dans la liste S
            if (Modele.masque){ // 80% de chance qu'une personne porte un masque
                rand = Math.random();
                m = rand < 0.8;
            }
            pers = new Personne('S',Monde.N,Monde.M,m,false);
            S.add(pers);
        }
        
        for (int j=1; j<=i; j++){ //On ajoute i personnes dans la liste I
            if (Modele.masque){ // 80% de chance qu'une personne porte un masque
                rand = Math.random();
                m = rand < 0.8;
            }
            if (Modele.quarantaine){ // 60% de chance qu'une personne infectée de la population initiale soit en quarantaine
                rand = Math.random();
                q = rand < 0.6;
            }
            pers = new Personne('I',Monde.N,Monde.M,m,q);
            I.add(pers);
        }
        
        for (int j=1; j<=r; j++){ //On ajoute r personnes dans la liste R
            if (Modele.masque){ // 80% de chance qu'une personne porte un masque
                rand = Math.random();
                m = rand < 0.8;
            }
            pers = new Personne('R',Monde.N,Monde.M,m,false);
            R.add(pers);
        }
    }

    /**
     * Constructeur pour une simulation avec 4 catégories (SEIR(N))
     *
     * @param s nombre de personnes saines
     * @param i nombre de personnes infectées
     * @param r nombre de personnes retirées
     * @param e nombre de personnes exposées
     */
    public Population(double s, double e, double i, double r) {
        Personne pers;
        S = new ArrayList<Personne>();
        E = new ArrayList<Personne>();
        I = new ArrayList<Personne>();
        R = new ArrayList<Personne>();
        double rand; 
        boolean m = false;
        boolean q = false;
            
        for (int j=1; j<=s; j++){   //On ajoute s personnes dans la liste S
            if (Modele.masque){ // 80% de chance qu'une personne porte un masque
                rand = Math.random();
                m = rand < 0.8;
            }
            pers = new Personne('S',Monde.N,Monde.M,m,false);
            S.add(pers);
        }
        
        for (int j=1; j<=i; j++){   //On ajoute e personnes dans la liste E
            if (Modele.masque){ // 80% de chance qu'une personne porte un masque
                rand = Math.random();
                m = rand < 0.8;
            }
            pers = new Personne('E',Monde.N,Monde.M,m,false);
            E.add(pers);
        }
        
        for (int j=1; j<=i; j++){   //On ajoute i personnes dans la liste I
            if (Modele.masque){ // 80% de chance qu'une personne porte un masque
                rand = Math.random();
                m = rand < 0.8;
            }
            if (Modele.quarantaine){ // 60% de chance qu'une personne infectée de la population initiale soit en quarantaine
                rand = Math.random();
                q = rand < 0.6;
            }
            pers = new Personne('I',Monde.N,Monde.M,m,q);
            I.add(pers);
        }
        
        for (int j=1; j<=r; j++){   //On ajoute r personnes dans la liste R
            if (Modele.masque){ // 80% de chance qu'une personne porte un masque
                rand = Math.random();
                m = rand < 0.8;
            }
            pers = new Personne('R',Monde.N,Monde.M,m,false);
            R.add(pers);
        }
    }

    
    
    /**
     * 
     * @return la liste des personnes saines 
     */
    public ArrayList<Personne> getS() {
        return S;
    }
    
    /**
     * 
     * @return la liste des personnes exposées 
     */
    public ArrayList<Personne> getE() {
        return E;
    }
    
    /**
     * 
     * @return la liste des personnes infectées 
     */
    public ArrayList<Personne> getI() {
        return I;
    }
    
    /**
     * 
     * @return la liste des personnes retirées 
     */
    public ArrayList<Personne> getR() {
        return R;
    }
    
    
    /**
     * Méthode qui génère le prochain déplacement de toute la population.
     * @param c vaut true si il y a confinement, false sinon
     */
    public void prochainDeplacement(boolean c){
        for (int j=0; j<S.size(); j++){ // Genere le deplacement pour les personnes saines
            if (c)
                S.get(j).genererDeplacementConfinement();
            else
                S.get(j).genererDeplacement();
        }
        
        for (int j=0; j<E.size(); j++){ // Genere le deplacement pour les personnes exposées
            if (c)
                E.get(j).genererDeplacementConfinement();
            else
                E.get(j).genererDeplacement();
        }
        
        for (int j=0; j<I.size(); j++){ // Genere le deplacement pour les personnes infectées
            if (c)
                I.get(j).genererDeplacementConfinement();
            else
                I.get(j).genererDeplacement();
        }
        
        for (int j=0; j<R.size(); j++){ // Genere le deplacement pour les personnes retirées
            if (c)
                R.get(j).genererDeplacementConfinement();
            else
                R.get(j).genererDeplacement();
        }
    }
    
    /**
     * Méthode qui déplace toute la population.
     */
    public void deplacer(){
        for (int j=0; j<S.size(); j++){
            S.get(j).deplacer();
        }
        
        for (int j=0; j<E.size(); j++){
            E.get(j).deplacer();
        }
        
        for (int j=0; j<I.size(); j++){
            I.get(j).deplacer();
        }
        
        for (int j=0; j<R.size(); j++){
            R.get(j).deplacer();
        }
    }
    
    /**
     * Méthode qui calcul le nombre de personnes dans une case du monde.
     * @param x abcisse de la case
     * @param y ordonnée de la case
     * @return le nombre de personnes dans cette case
     */
    public int nbPersonneCase(int x, int y){
        int nb = 0; 
        int abs, ord;
        for (int j=0; j<S.size(); j++){
            abs = S.get(j).getPosition_x();
            ord = S.get(j).getPosition_y();
            if ((abs == x) && (ord == y))
                nb++;
        }
        
        for (int j=0; j<E.size(); j++){
            abs = E.get(j).getPosition_x();
            ord = E.get(j).getPosition_y();
            if ((abs == x) && (ord == y))
                nb++;
        }
        
        for (int j=0; j<I.size(); j++){
            abs = I.get(j).getPosition_x();
            ord = I.get(j).getPosition_y();
            if ((abs == x) && (ord == y))
                nb++;
        }
        
        for (int j=0; j<R.size(); j++){
            abs = R.get(j).getPosition_x();
            ord = R.get(j).getPosition_y();
            if ((abs == x) && (ord == y))
                nb++;
        }
        return nb;
    } 
    
    /**
     * Méthode qui affiche la population.
     * Elle affiche chaque personne avec un retour à la ligne
     */
    public void afficherPopulation(){
        for (int j=0; j<S.size(); j++){
            S.get(j).afficherPersonne();
        }
        
        for (int j=0; j<E.size(); j++){
            E.get(j).afficherPersonne();
        }
        
        for (int j=0; j<I.size(); j++){
            I.get(j).afficherPersonne();
        }
        
        for (int j=0; j<R.size(); j++){
            R.get(j).afficherPersonne();
        }
    }
    
}
