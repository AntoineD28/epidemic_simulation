/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetapo;

import java.util.ArrayList;

/**
 * Classe représentant un monde
 * 
 * @author lenymetzger
 */
public class Monde {
    
    /**
     * Largeur du monde
     */
    public static int N;

    /**
     * Longueur du monde
     */
    public static int M;
    
    /**
     * Population utilisé
     */
    private Population p; 
    
    
    /**
     * Constructeur pour un Monde possédant une Population divisée en 3 catégories (SIR)
     *
     * @param s nombre de personnes saines
     * @param i nombre de personnes infectées
     * @param r nombre de personnes retirées
     * @param N Longueur du monde
     * @param M largeur du monde
     */
    public Monde(double s, double i, double r, int N, int M){
        this.N = N;
        this.M = M;
        p = new Population(s,i,r);       
    } 

    /**
     * Constructeur pour un Monde possédant une Population divisée en 4 catégories (SEIR(N))
     *
     * @param s nombre de personnes saines
     * @param e nombre de personnes exposées
     * @param i nombre de personnes infectées
     * @param r nombre de personnes retirées
     * @param N Longueur du monde
     * @param M largeur du monde
     */
    public Monde(double s, double e, double i, double r, int N, int M){
        this.N = N;
        this.M = M;
        p = new Population(s,e,i,r);       
    } 
    
    
    /**
     * 
     * @return la Population du monde 
     */
    public Population getPopulation(){
        return p;
    }
    
    /**
     * Méthode qui affiche le monde.
     * Pour chaque case du monde, cela affiche le nombre de personne présente dans cette case
     */
    public void afficherMonde(){
        ArrayList<Personne> Sain = p.getS();
        ArrayList<Personne> Exposé = p.getE();
        ArrayList<Personne> Inf = p.getI();
        ArrayList<Personne> Recovered = p.getR();
        for (int i = 0; i<N; i++){
            for (int j = 0; j<M; j++){
                int nb_pers = p.nbPersonneCase(i,j);
                System.out.print(nb_pers + "/");
            }
            System.out.println();
        } 
    }
    
}
