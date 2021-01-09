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
public class ProjetAPO {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Sir s = new Sir(0.8, 0.44, 762, 1, 0, 30);
        s.simulationSIR();
        System.out.println();
        
        Seir s2 = new Seir(0.8, 0.2, 0.5, 600, 100, 400, 0, 30);
        s2.simulationSEIR();
        System.out.println();
        
        SeirN s3 = new SeirN(0.8, 0.2, 0.5, 0.05, 0.06, 600, 100, 400, 0, 30);
        s3.simulationSEIRN();
    }
    
}
