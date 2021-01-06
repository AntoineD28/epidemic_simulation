/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetapo;

/**
 * Classe servant à représenter une population
 *
 * @author Antoine
 */
public class Population {

    private double s;
    private double i;
    private double r;
    private double e;

    /**
     * Constructeur par défaut
     */
    public Population() {
        this(100, 1, 0);
    }

    /**
     * Constructeur pour une simulation avec 3 catégories (SIR)
     *
     * @param s nombre de personnes saines
     * @param i nombre de personnes infectées
     * @param r nombre de personnes retirées
     */
    public Population(double s, double i, double r) {
        this.s = s;
        this.i = i;
        this.r = r;
    }

    /**
     * Constructeur pour une simulation avec 4 catégories
     *
     * @param s nombre de personnes saines
     * @param i nombre de personnes infectées
     * @param r nombre de personnes retirées
     * @param e nombre de personnes exposées
     */
    public Population(double s, double i, double r, double e) {
        this.s = s;
        this.i = i;
        this.r = r;
        this.e = e;
    }

    /**
     * 
     * @return le nombre de personnes Saines 
     */
    public double getS() {
        return s;
    }
    
    /**
     * 
     * @return le nombre de personnes Infectées 
     */
    public double getI() {
        return i;
    }

    /**
     * 
     * @return le nombre de personnes Retirées 
     */
    public double getR() {
        return r;
    }

    /**
     * 
     * @return le nombre de personnes Exposées 
     */
    public double getE() {
        return e;
    }

    /**
     * 
     * @param s mettre à jour le nombre de personnes Saines
     */
    public void setS(double s) {
        this.s = s;
    }

    /**
     * 
     * @param i mettre à jour le nombre de personnes Infectées
     */
    public void setI(double i) {
        this.i = i;
    }

    /**
     * 
     * @param r mettre à jour le nombre de personnes Retirées
     */
    public void setR(double r) {
        this.r = r;
    }

    /**
     * 
     * @param e mettre à jour le nombre de personnes Exposées
     */
    public void setE(double e) {
        this.e = e;
    }
}
