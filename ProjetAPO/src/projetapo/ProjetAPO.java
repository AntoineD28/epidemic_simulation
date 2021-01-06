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
        Sir s = new Sir(0.00218, 0.44, 762, 1, 0, 30);
        s.simulationSIR();
    }
    
}
