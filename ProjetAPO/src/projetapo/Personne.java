/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetapo;

import java.util.Random;

/**
 * Classe représentant une personne
 * 
 * @author lenymetzger
 */
public class Personne {
    
    /**
     * id unique d'une personne
     */
    private int id;
    
    /**
     * etat de la personnes (S, E, I ou R)
     */
    private char etat;  
    
    /**
     * position en x de la personne
     */
    private int position_x; 
    
    /**
     * position en y de la personne
     */
    private int position_y; 
    
    /**
     * sens de déplacement (f,r,l,t,b)
     */
    private char deplacement; 
    
    /**
     * Utilisé pour savoir si la personne porte le masque ou non
     */
    private boolean masque;         
    
    /**
     *  Utilisé pour savoir si la personne est en quarantaine ou non
     */
    private boolean quarantaine;  
    
    /**
     * Utilisé pour savoir depuis combien de jour la personne est en quarantaine
     */
    private int nbJourQuarantaine;
    
    /**
     * prochain id à attribuer
     */
    private static int numero = 1;
    
    /**
     * Constructeur avec pramètres
     * @param e etat
     * @param N largeur du monde
     * @param M longueur du monde
     * @param m est ce que la personne porte un masque
     * @param q est ce que la personne est en quarantaine
     */
    public Personne(char e, int N, int M, boolean m, boolean q) {
        Random r = new Random();
        
        id = Personne.numero;
        Personne.numero++;
        etat = e;
        // On place la personne sur une position du Monde
        position_x = r.nextInt(N);
        position_y = r.nextInt(M);
        // Déplacement par defaut fixe 
        deplacement = 'f';
        masque = m;
        quarantaine = q;
        nbJourQuarantaine = 0; //Lorsqu'une personne est créée, elle a passé 0 jour en quarantaine
    }
    
    
    /**
     * 
     * @return l'id de la personne 
     */
    public int getId(){
        return id;
    }
    
    /**
     * 
     * @return l'etat de la personne 
     */
    public char getEtat(){
        return etat;
    }
    
    /**
     * 
     * @return la position en x de la personne 
     */
    public int getPosition_x(){
        return position_x;
    }
    
    /**
     * 
     * @return la position en y de la personne 
     */
    public int getPosition_y(){
        return position_y;
    }
    
    /**
     * 
     * @return le déplacement de la personne 
     */
    public char getDeplacement(){
        return deplacement;
    }
    
    /**
     * 
     * @return si la personne porte un masque ou non 
     */
    public boolean getMasque(){
        return masque;
    }
    
    /**
     * 
     * @return si la personne est en quarantaine ou non
     */
    public boolean getQuarantaine(){
        return quarantaine;
    }
    
    /**
     * 
     * @return le nombre de jour passé en quarantaine par la personne 
     */
    public int getJourQuarantaine(){
        return nbJourQuarantaine;
    }
    
    /**
     * 
     * @param e mettre à jour l'etat de la personne
     */
    public void setEtat(char e){
        etat = e;
    }
    
    /**
     * 
     * @param x mettre à jour la position en x de la personne
     */
    public void setPosition_x(int x){
        position_x = x;
    }
    
    /**
     * 
     * @param y mettre à jour la position en y de la personne
     */
    public void setPosition_y(int y){
        position_y = y;
    }
    
    /**
     * 
     * @param c mettre à jour l'orientation de déplacement de la personne
     */
    public void setDeplacement(char c){
        deplacement = c;
    }
    
    /**
     * 
     * @param q Mettre à jour si la personne est en quarantaine ou non
     */
    public void setQuarantaine(boolean q){
        quarantaine = q;
    }
    
    /**
     * 
     * @param j Mettre à jour le nombre de jour passé en quarantaine par la personne 
     */
    public void setJourQuarantaine(int j){
        nbJourQuarantaine = j;
    }
    
    
    
    /**
     * Méthode qui compare la position de 2 personnes.
     * @param p 2eme personne de la comparaison
     * @return true si les 2 personnes sont au même endroit, false sinon
     */
    public boolean comparePosition(Personne p){
        boolean b = false;
        if((this.position_x==p.position_x)&&(this.position_y==p.position_y)){
            b = true;
        }
        return b;
    }
    
    /**
     * Méthode qui génère le prochain déplacement d'une personne.
     */
    public void genererDeplacement(){
        Random r = new Random();
        int i = r.nextInt(5); //On prend un entier entre 0 et 4
        switch(i){
            case(0) : 
                deplacement = 'f'; // fixe
                break;
            case(1) : 
                deplacement = 'r';  // droite 
                break;
            case(2) : 
                deplacement = 'l';  // gauche
                break;
            case(3) : 
                deplacement = 't';  // haut 
                break;
            case(4) : 
                deplacement = 'b';  // bas
                break;
            default : break;
        }
    }
    
    /**
     * Méthode qui génère le prochain déplacement d'une personne en cas de confinement.
     */
    public void genererDeplacementConfinement(){
        Random r = new Random();
        int i = r.nextInt(50); // On prend en entier entre 0 et 49
        switch(i){
            default : 
                deplacement = 'f'; // fixe (probabilité très importante)
                break;
            case(1) : 
                deplacement = 'r';  // droite
                break;
            case(2) : 
                deplacement = 'l';  // gauche
                break;
            case(3) : 
                deplacement = 't';  // haut
                break;
            case(4) : 
                deplacement = 'b';  // bas 
                break;
        }
    }
    
    /**
     * Méthode qui déplace une personne.
     */
    public void deplacer(){
        switch(deplacement){
            case('f') : break;  //fixe
            case('r') :   // droite
                if (position_x < Monde.N - 1)
                    position_x++;
                break;
            case('l') :     // gauche
                if (position_x > 0)
                    position_x--;
                break;
            case('t') :     // haut
                if (position_y < Monde.M - 1)
                    position_y++;
                break;
            case('b') :     // bas 
                if (position_y > 0)
                    position_y--;
                break;
            default : break;
        }
    }

    /**
     * Méthode qui affiche une personne.
     * Elle affiche tous les attributs qui définissent une personne
     */
    public void afficherPersonne(){
        System.out.println("id : " + id + " etat : " + etat + " position_x : " + position_x + " position_y : " + 
                position_y + " deplacement : " + deplacement + " masque : " + masque + " quanrantaine : " + 
                quarantaine + " Durée quarantaine : " + nbJourQuarantaine);
    }
    
}
