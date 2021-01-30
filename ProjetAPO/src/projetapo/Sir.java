/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetapo;

import java.util.ArrayList;

/**
 * Classe pour le modèle SIR
 * @author lenymetzger
 */
public class Sir extends Modele{
        
    
    /**
     * Constructeur par défaut
     */
    public Sir() {
        this(0.9,0.5,600,400,0,5);
    }

    /**
     * Constructeur avec pramètres (sans la spatialisation et les politiques publiques)
     * @param b paramètre de transmission
     * @param g paramètre de guérison
     * @param s personnes saines
     * @param i personnes infectées
     * @param r personnes retirées
     * @param t temps (en jours)
     */
    public Sir(double b, double g, double s, double i, double r, int t) {
        this(b,g,s,i,r,t,1,1,false,false,false,false,0,0);
    }
    
    /**
     * Constructeur avec pramètres (avec la spatialisation mais sans politiques publiques)
     * @param b paramètre de transmission
     * @param g paramètre de guérison
     * @param s personnes saines
     * @param i personnes infectées
     * @param r personnes retirées
     * @param t temps (en jours)
     * @param N longueur du monde
     * @param M largeur du monde
     */
    public Sir(double b, double g, double s, double i, double r, int t, int N, int M) {
        this(b,g,s,i,r,t,N,M,false,false,false,false,0,0);
    }
    
    /**
     * Constructeur avec pramètres (cas ou il n'y a pas la vaccination et de quarantaine)
     * @param b paramètre de transmission
     * @param g paramètre de guérison
     * @param s personnes saines
     * @param i personnes infectées
     * @param r personnes retirées
     * @param t temps (en jours)
     * @param N longueur du monde
     * @param M largeur du monde
     * @param c confinement
     * @param ma port du masque
     * @param q quarantaine
     * @param v vaccination 
     */
    public Sir(double b, double g, double s, double i, double r, int t, int N, int M, boolean c, 
            boolean ma, boolean q, boolean v) {
        this(b,g,s,i,r,t,N,M,c,ma,q,v,0,0);
    }
    
    /**
     * Constructeur avec tous les pramètres
     * @param b paramètre de transmission
     * @param g paramètre de guérison
     * @param s personnes saines
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
     * @param pv Probabilité qu'une personne saine se fasse vacciner
     */
    public Sir(double b, double g, double s, double i, double r, int t, int N, int M, boolean c, 
            boolean ma, boolean q, boolean v, int tq, double pv) {
        super(b,g,s,0,i,r,t,N,M,c,ma,q,v,tq,pv); // Appel du constructeur de Modele
    }

    
    
    /**
     * Méthode qui gère les nouvelles infections.
     * Test si une personnes saine et une infectée sont sur la même case, si c'est le cas, la personne saine à une probabilité beta de devenir infectée
     */
    public void infection(){
        Population p = m.getPopulation();
        ArrayList<Personne> Sain = p.getS();
        ArrayList<Personne> Inf = p.getI();
        double r, bet; 
        int nb_inf = Inf.size();    //Le nombre d'infecté avant que tout autre personne le devienne
        int i = 0, j = 0;
        boolean supr;               //égale à True lorsqu'une personne est infectée sur le test actuel
        
        //On compare la position de tous les sains avec tous les infectées
        while (i<Sain.size() && j<nb_inf){
                supr = false;
                if (!Inf.get(j).getQuarantaine()){ //Si la personne infecté n'est pas en quarantaine, elle peut infecter 1 personne, sinon non (donc le test n'est pas utile)
                    boolean b = Sain.get(i).comparePosition(Inf.get(j)); // Compare la position d'une personne saine et d'1 infectée
                    if(b){  // Si les 2 personnes sont sur la même case
                        if (Sain.get(i).getMasque()) // Si la personne porte le masque
                            bet = beta/10;        // Le parametre beta est divisé par 10
                        else 
                            bet = beta;         // Sinon, on garde le parametre beta de base
                        r = Math.random();   // r prend une valeur entre 0 et 1
                        if(r<=bet){             // Si r<bet, la personne saine devient infectée, c.a.d qu'on a une proba "bet" de devenir infectée 
                            Inf.add(Sain.get(i));
                            Sain.remove(Sain.get(i));
                            supr = true;
                        }
                    }
                }
                j++;    //Itération sur le parcours des infectées
                if (j == nb_inf && supr==false){ // Si on a parcouru tous les infectées, on passe à la personne saine suivante
                    i++;    //Itération sur le parcours des sains
                    j=0;
                }
                if (j == nb_inf && supr==true) // si on a parcouru tous les infectées, et qu'une personne saine a été infectée, on itère pas sur le tableau des sains car il y a une personne en moins
                    j=0;    
        }

    }

    
    
    /**
     * Procédure qui réalise la simulation du modèle SIR.
     * Affiche chaque jour le nouveau nombre de personnes pour chaque catégories
     */
    @Override
    public void simulation() {
        Population p = m.getPopulation();
        int S = p.getS().size(), I = p.getI().size(), R = p.getR().size();
        double new_R = 0, nbPersVaccin;
        double N = S+I+R;
        
        System.out.println("Jour 0 : Population = " + N + " S = " + S + " I = " + I + " R = " + R);

        for (int j = 1; j <= temps; j++) { // Boucle le nombre de jour choisi pour la simulation
            // Calcul du nombre d'ajout dans chaque population
            new_R += gamma * I - (int)new_R;
            
            // Afficher la population et le monde (utilisé pour vérifier que la spatialisation marche bien)
            /*p.afficherPopulation();
            m.afficherMonde();*/
            
            // Gère le déplacement de la population
            p.prochainDeplacement(confinement);
            p.deplacer();
            
            // Gère les personnes infectées qui sont en quarantaine
            if (quarantaine){
                quarantaine();
            }
            
            // Modifie les populations
            infection();
            retire((int)new_R);
            
            // Gere les vaccinations 
            if (vaccination){
                nbPersVaccin = S*probaVaccin;
                vaccination((int)nbPersVaccin);
            }
            
            // Nombre de personne dans chaque catégorie 
            S = p.getS().size();
            I = p.getI().size();
            R = p.getR().size();
            N = S+I+R;
            
            // Affichage
            System.out.println("Jour " + j + " Population = " + N + " S = " + + S + " I = " + I + " R = " + R);
        }
    }
}
