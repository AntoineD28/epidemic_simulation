/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetapo;

import java.util.Scanner;

/**
 *
 * @author Antoine
 */
public class ProjetAPO {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean spacialisation, polPublique, confinement = false, masque = false, quarantaine = false, vaccination = false;
        String conf, ma, quar, vaccin;
        int N = 0, M = 0; 
        double beta = 0, alpha = 0, mu = 0, eta = 0, exposé = 0, probaVaccin = 0;
        
        System.out.println("-------------SIMULATION D'UNE EPIDEMIE-------------");
        
        System.out.println("\n-------------CHOIX DU MODELE-------------");
        // Choix Modele 
        System.out.println("Veuillez choisir un modele parmis : SIR / SEIR / SEIRN");
        String modele = sc.nextLine();
        while(!modele.equals("SIR") && !modele.equals("SEIR") && !modele.equals("SEIRN") && 
                !modele.equals("sir") && !modele.equals("seir") && !modele.equals("seirn")){
            System.out.println("Vous n'avez pas entré un modèle proposé. Veuillez choisir un modele parmis : SIR / SEIR / SEIRN");
            modele = sc.nextLine();
        } 
        System.out.println("Vous avez choisi le modele : " + modele);

        
        System.out.println("\n-------------CHOIX DES PARAMETRES-------------");
        // Choix Parametres 
        // Parametre beta
        if(modele.equals("SIR") || modele.equals("sir")){
            System.out.print("Veuillez choisir la valeur du parametre beta (Probabilité de se faire infecter) : ");
            beta = sc.nextDouble();
            while((beta < 0) || (beta > 1)){
                System.out.println("Vous n'avez pas entré une valeur entre 0 et 1");
                beta = sc.nextDouble();
            } 
        }
        else{
            System.out.print("Veuillez choisir la valeur du parametre beta (Probabilité de devenir exposé) : ");
            beta = sc.nextDouble();
            while((beta < 0) || (beta > 1)){
                System.out.println("Vous n'avez pas entré une valeur entre 0 et 1");
                beta = sc.nextDouble();
            }
        }
        // Parametre gamma
        System.out.print("Veuillez choisir la valeur du parametre gamma (Probabilité de ne plus être infecté) : ");
        double gamma = sc.nextDouble();
        while((gamma < 0) || (gamma > 1)){
            System.out.println("Vous n'avez pas entré une valeur entre 0 et 1");
            gamma = sc.nextDouble();
        }
        // Parametre alpha 
        if(modele.equals("SEIR") || modele.equals("SEIRN") || modele.equals("seir") || modele.equals("seirn")){
            System.out.print("Veuillez choisir la valeur du parametre alpha (Probabilité de devenir infecté) : ");
            alpha = sc.nextDouble();
            while((alpha < 0) || (alpha > 1)){
                System.out.println("Vous n'avez pas entré une valeur entre 0 et 1");
                alpha = sc.nextDouble();
            } 
        }
        // Parametre mu
        if(modele.equals("SEIRN") || modele.equals("seirn")){
            System.out.print("Veuillez choisir la valeur du parametre mu (Probabilité décés) : ");
            mu = sc.nextDouble();
            while((mu < 0) || (mu > 1)){
                System.out.println("Vous n'avez pas entré une valeur entre 0 et 1");
                mu = sc.nextDouble();
            } 
            System.out.print("Veuillez choisir la valeur du parametre eta (Probilité naissance) : ");
            eta = sc.nextDouble();
            while((eta < 0) || (eta > 1)){
                System.out.println("Vous n'avez pas entré une valeur entre 0 et 1");
                eta = sc.nextDouble();
            } 
        }
        
        
        System.out.println("\n-------------TEMPS-------------");
        // Choix du temps
        System.out.print("Temps de la simulation : ");
        int temps = sc.nextInt();
        while(temps < 0){
            System.out.print("\nVous n'avez pas entré une valeur positive, rééssayez : ");
            temps = sc.nextInt();
        } 

        System.out.println("\n-------------POPULATIONS-------------");
        // Choix des populations
        // Personnes saines
        System.out.print("Nombre de personnes saines : ");
        double sain = sc.nextDouble();
        while(sain < 0){
            System.out.print("\nVous n'avez pas entré une valeur positive, rééssayez : ");
            sain = sc.nextDouble();
        } 
        // Personnes exposées
        if(modele.equals("SEIR") || modele.equals("seir") || modele.equals("SEIRN") || modele.equals("seirn")){
            System.out.print("Nombre de personnes exposées : ");
            exposé = sc.nextDouble();
            while(exposé < 0){
                System.out.print("\nVous n'avez pas entré une valeur positive, rééssayez : ");
                exposé = sc.nextDouble();
            } 
        }
        // Personnes infectées
        System.out.print("Nombre de personnes infectées : ");
        double infecté = sc.nextDouble();
        while(infecté < 0){
            System.out.print("\nVous n'avez pas entré une valeur positive, rééssayez : ");
            infecté = sc.nextDouble();
        } 
        // Personnes guéries
        System.out.print("Nombre de personnes guéries : ");
        double guéris = sc.nextDouble();
        while(guéris < 0){
            System.out.print("\nVous n'avez pas entré une valeur positive, rééssayez : ");
            guéris = sc.nextDouble();
        } 
        
        
        System.out.println("\n-------------CHOIX SPACIALISATION-------------");
        // Choix Spacialisation  
        System.out.print("Voulez vous activer la Spacialisation ? (oui/non) : ");
        sc.nextLine();
        String spac = sc.nextLine();
        while(!(spac.equals("oui") || spac.equals("non") || spac.equals("o") || 
                spac.equals("n") || spac.equals("Oui") || spac.equals("Non"))){
            System.out.println("\nVous n'avez pas répondu par oui ou non, voulez vous activer la Spacialisation ? ");
            spac = sc.nextLine();
        }
        
        if(spac.equals("oui") || spac.equals("Oui") || spac.equals("o"))
            spacialisation = true;
        else 
            spacialisation = false; 
        
        // Si la spacialisation est activée, on peut choisir la taille du Monde
        if(spacialisation){
            System.out.println("\n-------------CHOIX TAILLE MONDE-------------");
            System.out.println("Veuillez choisir la taille du monde :");
            System.out.print("N = ");
            N = sc.nextInt();
            System.out.print("M = ");
            M = sc.nextInt();
        }
        
        System.out.println("\n-------------CHOIX DES POLITIQUES PUBLIQUES-------------");
        // Choix Politiques Publiques  
        System.out.print("Voulez vous activer les Politiques Publiques ? (oui/non) : ");
        sc.nextLine();
        String pp = sc.nextLine();
        while(!(pp.equals("oui") || pp.equals("non") || pp.equals("o") || 
                pp.equals("n") || pp.equals("Oui") || pp.equals("Non"))){
            System.out.println("Vous n'avez pas répondu par oui ou non, voulez vous activer les politiques publiques ? : ");
            pp = sc.nextLine();
        }
        
        if(pp.equals("oui") || pp.equals("Oui") || pp.equals("o"))
            polPublique = true;
        else 
            polPublique = false; 
        
        // Si les politiques publiques sont activées, on peut choisir lesquelles
        if(polPublique){
            // Confinement
            System.out.print("Confinement ? (oui/non) : ");
            conf = sc.nextLine();
            while(!(conf.equals("oui") || conf.equals("non") || conf.equals("o") || 
                conf.equals("n") || conf.equals("Oui") || conf.equals("Non"))){
            System.out.print("Vous n'avez pas répondu par oui ou non, voulez vous activer le Confinement ? : ");
            conf = sc.nextLine();
            }
            if(conf.equals("oui") || conf.equals("Oui") || conf.equals("o"))
                confinement = true;
            else 
                confinement = false; 
        
            // Port du masque
            System.out.print("Masque ? (oui/non) : ");
            ma = sc.nextLine();
            while(!(ma.equals("oui") || ma.equals("non") || ma.equals("o") || 
                ma.equals("n") || ma.equals("Oui") || ma.equals("Non"))){
            System.out.print("Vous n'avez pas répondu par oui ou non, voulez vous activer le port du masque ? : ");
            ma = sc.nextLine();
            }
            if(ma.equals("oui") || ma.equals("Oui") || ma.equals("o"))
                masque = true;
            else 
                masque = false; 
            
            // Quarantaine 
            System.out.print("Quarantaine ? (oui/non) : ");
            quar = sc.nextLine();
            while(!(quar.equals("oui") || quar.equals("non") || quar.equals("o") || 
                quar.equals("n") || quar.equals("Oui") || quar.equals("Non"))){
            System.out.print("Vous n'avez pas répondu par oui ou non, voulez vous activer la mise en quarantaine ? : ");
            quar = sc.nextLine();
            }
            if(quar.equals("oui") || quar.equals("Oui") || quar.equals("o"))
                quarantaine = true;
            else 
                quarantaine = false; 
            
            // Vaccination
            System.out.print("Vaccination ? (oui/non) : ");
            vaccin = sc.nextLine();
            while(!(vaccin.equals("oui") || vaccin.equals("non") || vaccin.equals("o") || 
                vaccin.equals("n") || vaccin.equals("Oui") || vaccin.equals("Non"))){
            System.out.print("Vous n'avez pas répondu par oui ou non, voulez vous activer la Vaccination ? : ");
            vaccin = sc.nextLine();
            }
            if(vaccin.equals("oui") || vaccin.equals("Oui") || vaccin.equals("o"))
                vaccination = true;
            else 
                vaccination = false; 
            
            // Si la vaccination est activée, il faut demander la probabilité de se faire vacciner
            if(vaccination){
                System.out.print("Quel est la probabilité pour une personne saine de se faire vacciner : ");
                probaVaccin = sc.nextDouble();
                while(probaVaccin<0 || probaVaccin>1){
                    System.out.print("Vous n'avez pas entré une valeur entre 0 et 1, recommencez : ");
                    probaVaccin = sc.nextDouble();
                }
            }
        }
        
        
        System.out.println("\n\n\n\n-------------RESULTAT SIMULATION-------------");
        if(modele.equals("SIR") || modele.equals("sir")){
            Sir s = new Sir(beta,gamma,sain,infecté,guéris,temps,N,M,confinement,masque,quarantaine,vaccination,probaVaccin);
            s.simulationSpacialisation();
        }
        
        if(modele.equals("SEIR") || modele.equals("seir")){
            Seir s = new Seir(beta,alpha,gamma,sain,exposé,infecté,guéris,temps,N,M,confinement,masque,quarantaine,vaccination,probaVaccin);
            s.simulationSpacialisation();
        }
        
        if(modele.equals("SEIRN") || modele.equals("seirn")){
            SeirN s = new SeirN(beta,alpha,gamma,mu,eta,sain,exposé,infecté,guéris,temps,N,M,confinement,masque,quarantaine,vaccination,probaVaccin);
            s.simulationSpacialisation();
        }
    }
    
}
