/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetapo;

import java.util.ArrayList;

/**
 *
 * Classe pour le modèle SEIR avec naissances et décés
 * 
 * @author lenymetzger
 */
public class SeirN extends Modele{
    
    /* parametre beta */
    private double alpha;
    /* parametre mu */
    private double mu;
    /* parametre eta */
    private double eta;

    /**
     * Constructeur par défaut
     */
    public SeirN() {
        this(0.9,0.5,0.5,0.05,0.07,600,100,400,0,5);
    }

    /**
     * Constructeur avec pramètres (sans la spacialisation et les politiques publiques)
     * @param b paramètre de transmission
     * @param a paramètre d'incubation
     * @param g paramètre de guérison
     * @param u paramètre de mort naturelle
     * @param n paramètre de naissance
     * @param s personnes saines
     * @param e personnes exposées
     * @param i personnes infectées
     * @param r personnes retirées
     * @param t temps (en jours)
     */
    public SeirN(double b, double a, double g, double u, double n, double s, double e, double i, double r, int t) {
        this(b,a,g,u,n,s,e,i,r,t,1,1,false,false,false,false,0);
    }
    
    /**
     * Constructeur avec tous les pramètres (avec la spacialisation mais sans les politiques publiques)
     * @param b paramètre de transmission
     * @param a paramètre d'incubation
     * @param g paramètre de guérison
     * @param u paramètre de mort naturelle
     * @param n paramètre de naissance
     * @param s personnes saines
     * @param e personnes exposées
     * @param i personnes infectées
     * @param r personnes retirées
     * @param t temps (en jours)
     * @param N longueur du monde
     * @param M largeur du monde
     */
    public SeirN(double b, double a, double g, double u, double n, double s, double e, double i, double r, 
            int t, int N, int M) {
        this(b,a,g,u,n,s,e,i,r,t,N,M,false,false,false,false,0);
    }
    
    /**
     * Constructeur avec pramètres (cas sans vaccination)
     * @param b paramètre de transmission
     * @param a paramètre d'incubation
     * @param g paramètre de guérison
     * @param u paramètre de mort naturelle
     * @param n paramètre de naissance
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
    public SeirN(double b, double a, double g, double u, double n, double s, double e, double i, double r, 
            int t, int N, int M, boolean c, boolean ma, boolean q, boolean v) {
        this(b,a,g,u,n,s,e,i,r,t,N,M,c,ma,q,v,0);
    }
    
    /**
     * Constructeur avec tous les pramètres
     * @param b paramètre de transmission
     * @param a paramètre d'incubation
     * @param g paramètre de guérison
     * @param u paramètre de mort naturelle
     * @param n paramètre de naissance
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
    public SeirN(double b, double a, double g, double u, double n, double s, double e, double i, double r, 
            int t, int N, int M, boolean c, boolean ma, boolean q, boolean v, double pv) {
        super(b,g,s,e,i,r,t,N,M,c,ma,q,v,pv); // Appel du constructeur de Modele
        alpha = a;
        mu = u;
        eta = n;
    }
    
    
    
    /**
     * Méthode qui gère les nouvelles personnes exposées sans l'utilisation de la spacialisation.
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
        boolean supr;           //égale à True lorsqu'une personne est infectée sur le test actuel
        
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
                j++;        //Itération sur le parcours des infectées
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
     * Méthode qui gère les nouvelles naissances.
     * @param naissances Nombre de nouvelles naissances
     */
    public void naissance(int naissances){
        Population p = m.getPopulation();
        ArrayList<Personne> Sain = p.getS();
        boolean portMasque = false; 
        double rand; 
        
        for (int i=0; i<naissances; i++){
            if (Modele.masque){
                rand = Math.random();
                portMasque = rand < 0.8; // 80% de chance qu'une personne porte un masque
            }
            Personne pers = new Personne('S',Monde.N,Monde.N,portMasque);
            Sain.add(pers);
        }
    }
    
    /**
     * Méthode qui gère les nouveaux décés.
     * @param DC_S Nombre de personnes décédées parmis les personnes saines 
     * @param DC_E Nombre de personnes décédées parmis les personnes exposées 
     * @param DC_I Nombre de personnes décédées parmis les personnes infectées 
     * @param DC_R Nombre de personnes décédées parmis les personnes retirées 
     */
    public void décés(int DC_S, int DC_E, int DC_I, int DC_R){
        Population p = m.getPopulation();
        ArrayList<Personne> Sain = p.getS();
        ArrayList<Personne> Exposé = p.getE();
        ArrayList<Personne> Inf = p.getI();
        ArrayList<Personne> Recovered = p.getR();
        
        for (int i=0; i<DC_S; i++){
            Sain.remove(0);
        }
        for (int i=0; i<DC_E; i++){
            Exposé.remove(0);
        }
        for (int i=0; i<DC_I; i++){
            Inf.remove(0);
        }
        for (int i=0; i<DC_R; i++){
            Recovered.remove(0);
        }
    }
    
    
    
    /**
     * Procédure qui réalise la simulation du modèle SEIR avec évolution de la population (sans la spacialisation).
     * Affiche chaque jour le nouveau nombre de personnes pour chaque catégories
     */
    @Override
    public void simulation() {
        Population p = m.getPopulation();
        int S = p.getS().size(), E = p.getE().size(), I = p.getI().size(), R = p.getR().size();
        double new_I = 0, new_R = 0, new_E = 0, new_S = 0, DC_S = 0, DC_E = 0, DC_I = 0, DC_R = 0;
        double N = S+E+I+R;
      
        System.out.println("Jour 0 : Population = " + N + " S = " + S + " E = " + E + " I = " + I
                + " R = " + R); // Affichage du Jour 0

        for (int j = 1; j <= temps; j++) { // Boucle le nombre de jour choisi pour la simulation
            // Calcul du nombre d'ajout dans chaque population
            new_E += beta * S * I - (int)new_E;
            new_I += alpha * E - (int)new_I;
            new_R += gamma * I - (int)new_R;
            
            // Modifie la population exposée
            if (new_E > S){ // Gère le cas d'une épidémie 'violente' pour ne pas aggrandir la population totale faussement
                exposition(S);
            }
            else {
                exposition((int)new_E);
            }
            
            // Modifie la population
            infection((int)new_I);
            guerison((int)new_R);
            
            // Nombre de personne dans chaque catégorie (il faut les calculer ici pour avoir un nombre cohérant de naissance et de décés)
            S = p.getS().size();
            E = p.getE().size();
            I = p.getI().size();
            R = p.getR().size();
            
            // Nombre de naissance et de décés
            new_S += eta * N - (int)new_S;
            DC_S += mu * S - (int)DC_S;
            DC_E += mu * E - (int)DC_E;
            DC_I += mu * I - (int)DC_I;
            DC_R += mu * R - (int)DC_R;
            
            // Gère les naissances et les décés
            naissance((int)new_S);
            décés((int)DC_S,(int)DC_E,(int)DC_I,(int)DC_R);
            
            // Nombre de personne dans chaque catégorie 
            S = p.getS().size();
            E = p.getE().size();
            I = p.getI().size();
            R = p.getR().size();
            N = S+E+I+R;
            
            // Affichage
            System.out.println("Jour " + j +  " Population = " + N + " S = " + S + " E = " + E + " I = " + I
                + " R = " + R);
        }
    }
    
    
    
    /**
     * Procédure qui réalise la simulation du modèle SEIR avec écolution de la population (avec spacialisation).
     * Affiche chaque jour le nouveau nombre de personnes pour chaque catégories
     */
    @Override
    public void simulationSpacialisation() { // Boucle le nombre de jour choisi pour la simulation
        Population p = m.getPopulation();
        int S = p.getS().size(), E = p.getE().size(), I = p.getI().size(), R = p.getR().size();
        double new_I = 0, new_R = 0, new_S = 0, DC_S = 0, DC_E = 0, DC_I = 0, DC_R = 0, nbPersVaccin;
        double N = S+E+I+R;
        
        System.out.println("Jour 0 : Population = " + N + " S = " + S + " E = " + E + " I = " + I
                + " R = " + R);// Affichage du Jour 0

        for (int j = 1; j <= temps; j++) { // Boucle le nombre de jour choisi pour la simulation
            // Calcul du nombre d'ajout dans les populations
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
            
            // Nombre de personne dans chaque catégorie (il faut les calculer ici pour avoir un nombre cohérant de naissance et de décés)
            S = p.getS().size();
            E = p.getE().size();
            I = p.getI().size();
            R = p.getR().size();
            
            // Nombre de naissance et de décés
            new_S += eta * N - (int)new_S;
            DC_S += mu * S - (int)DC_S;
            DC_E += mu * E - (int)DC_E;
            DC_I += mu * I - (int)DC_I;
            DC_R += mu * R - (int)DC_R;
            
            // Gère les naissances et les décés
            naissance((int)new_S);
            décés((int)DC_S,(int)DC_E,(int)DC_I,(int)DC_R);
           
            // Gere les vaccinations 
            if (vaccination){
                nbPersVaccin = S*probaVaccin;
                vaccination((int)nbPersVaccin);
            }
            
            // On recalcule le nombre de personne dans chaque catégorie après les naissance et les décés
            S = p.getS().size();
            E = p.getE().size();
            I = p.getI().size();
            R = p.getR().size();
            N = S+E+I+R;
            
            // Affichage
            System.out.println("Jour " + j +  " Population = " + N + " S = " + S + " E = " + E + " I = " + I
                + " R = " + R);
        }
    }
}
