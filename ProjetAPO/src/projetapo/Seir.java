/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetapo;

import java.util.ArrayList;

/**
 * Classe pour le modèle SEIR
 * 
 * @author lenymetzger
 */
public class Seir extends Modele{
    
    /* parametre alpha */
    private double alpha;

    /**
     * Constructeur par défaut
     */
    public Seir() {
        this(0.9,0.5,0.5,600,100,400,0,5);
    }

    /**
     * Constructeur avec pramètres (sans la spacialisation et les politiques publiques)
     * @param b paramètre de transmission
     * @param a paramètre d'incubation
     * @param g paramètre de guérison
     * @param s personnes saines
     * @param e personnes exposées
     * @param i personnes infectées
     * @param r personnes retirées
     * @param t temps (en jours)
     */
    public Seir(double b, double a, double g, double s, double e, double i, double r, int t) {
        this(b,a,g,s,e,i,r,t,1,1,false,false,false,false,0);
    }
    
    /**
     * Constructeur avec pramètres (avec la spacialisation mais sans politique publique)
     * @param b paramètre de transmission
     * @param a paramètre d'incubation
     * @param g paramètre de guérison
     * @param s personnes saines
     * @param e personnes exposées
     * @param i personnes infectées
     * @param r personnes retirées
     * @param t temps (en jours)
     * @param N longueur du monde
     * @param M largeur du monde
     */
    public Seir(double b, double a, double g, double s, double e, double i, double r, int t, int N, int M) {
        this(b,a,g,s,e,i,r,t,N,M,false,false,false,false,0);
    }
    
    /**
     * Constructeur avec pramètres (cas sans vaccination)
     * @param b paramètre de transmission
     * @param a paramètre d'incubation
     * @param g paramètre de guérison
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
     */
    public Seir(double b, double a, double g, double s, double e, double i, double r, int t, int N, int M, 
            boolean c, boolean ma, boolean q, boolean v) {
        this(b,a,g,s,e,i,r,t,N,M,c,ma,q,v,0);
    }
    
    /**
     * Constructeur avec tous les pramètres
     * @param b paramètre de transmission
     * @param a paramètre d'incubation
     * @param g paramètre de guérison
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
     * @param pv Probabilité qu'une personne saine se fasse vacciner
     */
    public Seir(double b, double a, double g, double s, double e, double i, double r, int t, int N, int M, boolean c, 
            boolean ma, boolean q, boolean v, double pv) {
        super(b,g,s,e,i,r,t,N,M,c,ma,q,v,pv); // Appel du constructeur de Modele
        alpha = a;
    }
    
    
    
    /**
     * Méthode qui gère les nouveaux exposés sans l'utilisation de la spacialisation.
     * @param exposées Nombre de nouvelles personnes exposées
     */
    public void exposition(int exposées){
        Population p = m.getPopulation();
        ArrayList<Personne> Exposé = p.getE();
        ArrayList<Personne> Sain = p.getS();
        
        for (int i=0; i<exposées; i++){
            Exposé.add(Sain.get(0));
            Sain.remove(0);
        }
    }
    
    /**
     * Méthode qui gère les nouvelles personnes exposées avec utilisation de la spacialisation.
     * Test si une personnes saine et une infectée sont sur la même case, si c'est le cas, la personne saine à une probabilité beta de devenir exposée
     */
    public void expositionSpacialisation(){
        Population p = m.getPopulation();
        ArrayList<Personne> Sain = p.getS();
        ArrayList<Personne> Exposé = p.getE();
        ArrayList<Personne> Inf = p.getI();
        double r, bet; 
        int nb_inf = Inf.size();    //Le nombre d'infecté avant que tout autre personne le devienne
        int i = 0, j = 0;
        boolean supr;       //égale à True lorsqu'une personne est infectée sur le test actuel
        
        //On compare la position de tous les sains avec tous les infectées
        while (i<Sain.size() && j<nb_inf){
                supr = false;
                boolean b = Sain.get(i).comparePosition(Inf.get(j));    // Compare la position d'une personne saine et d'1 infectée
                if(b){  // Si les 2 personnes sont sur la même case
                    if (Sain.get(i).getMasque()) // Si la personne porte le masque
                        bet = beta/10;        // Le parametre beta est divisé par 10
                    else 
                        bet = beta;         // Sinon, on garde le parametre beta de base
                    r = Math.random();      // r prend une valeur entre 0 et 1
                    if(r<=bet){             // Si r<bet, la personne saine devient exposée, c.a.d qu'on a une proba "bet" de devenir exposée 
                        Exposé.add(Sain.get(i));
                        Sain.remove(Sain.get(i));
                        supr = true;
                    }
                }
                j++;    //Itération sur le parcours des infectées
                if (j == nb_inf && supr==false){    // Si on a parcouru tous les infectées, on passe à la personne saine suivante
                    i++;    //Itération sur le parcours des sains
                    j=0;
                }
                if (j == nb_inf && supr==true) // si on a parcouru tous les infectées, et qu'une personne saine a été infectée, on itère pas sur le tableau des sains car il y a une personne en moins
                    j=0;    
        }
    }
    
    /**
     * Méthode qui gère les nouvelles personnes infectées.
     * @param inf Nombre de nouvelles personnes infectées
     */
    @Override
    public void infection(int inf){
        Population p = m.getPopulation();
        ArrayList<Personne> Inf = p.getI();
        ArrayList<Personne> Exposé = p.getE();
        
        for (int i=0; i<inf; i++){
            Inf.add(Exposé.get(0));
            Exposé.remove(0);
        }
    }

    
    
    
    /**
     * Procédure qui réalise la simulation du modèle SEIR sans spacialisation.
     * Affiche chaque jour le nouveau nombre de personnes pour chaque catégories
     */
    @Override
    public void simulation() {
        Population p = m.getPopulation();
        int S = p.getS().size(), E = p.getE().size(), I = p.getI().size(), R = p.getR().size();
        double new_I = 0, new_R = 0, new_E = 0;
      
        System.out.println("Jour 0 : S = " + S + " E = " + E + " I = " + I + " R = " + R); // Affichage du jour 0

        for (int j = 1; j <= temps; j++) { // Boucle le nombre de jour choisi pour la simulation
            // Calcul du nombre d'ajout dans chaque population
            new_E += beta * S * I - (int)new_E;
            new_I += alpha * E - (int)new_I;
            new_R += gamma * I - (int)new_R;
            
            // Modifie la population exposée
            if (new_E > S){ // Gère le cas d'une épidémie 'violente' pour ne pas aggrandir la population totale
                exposition(S);
            }
            else {
                exposition((int)new_E);
            }
            
            // Modifie la population
            infection((int)new_I);
            guerison((int)new_R);
            
            // Nombre de personne dans chaque catégories
            S = p.getS().size();
            E = p.getE().size();
            I = p.getI().size();
            R = p.getR().size();
            
            // Affichage
            System.out.println("Jour " + j +  " : S = " + S + " E = " + E + " I = " + I + " R = " + R);
        }
    }
    
    /**
     * Procédure qui réalise la simulation du modèle SEIR avec spacialisation.
     * Affiche chaque jour le nouveau nombre de personnes pour chaque catégories
     */
    @Override
    public void simulationSpacialisation() {
        Population p = m.getPopulation();
        int S = p.getS().size(), E = p.getE().size(), I = p.getI().size(), R = p.getR().size();
        double new_R = 0, new_I = 0, nbPersVaccin;
        
        System.out.println("Jour 0 : S = " + S + " E = " + E + " I = " + I + " R = " + R); // Affichage du jour 0

        for (int j = 1; j <= temps; j++) { // Boucle le nombre de jour choisi pour la simulation
            // Calcul du nombre d'ajout dans chaque population
            new_R += gamma * I - (int)new_R;
            new_I += alpha * E - (int)new_I;
            
            // Afficher la population et le monde (utilisé pour vérifier que la spacialisation marche bien)
            /*p.afficherPopulation();
            m.afficherMonde();*/
            
            // Gère le déplacement de la population
            p.prochainDeplacement(confinement);
            p.deplacer();
            
            // Modifie la population
            expositionSpacialisation();
            infection((int)new_I);
            guerison((int)new_R);
                   
            // Gere les vaccinations 
            if (vaccination){
                nbPersVaccin = S*probaVaccin;
                vaccination((int)nbPersVaccin);
            }
            
            // Nombre de personne dans chaque catégorie 
            S = p.getS().size();
            E = p.getE().size();
            I = p.getI().size();
            R = p.getR().size();
            
            // Affichage
            System.out.println("Jour " + j +  " : S = " + S + " E = " + E + " I = " + I + " R = " + R);
        }
    }
    
}
