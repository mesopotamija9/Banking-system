/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package porukeObj;

import java.io.Serializable;

/**
 *
 * @author Milos
 */
public class IsplataSaRacuna implements Serializable{
    private int id;
    private double suma;
    private int idF;
    String svrha;

    public IsplataSaRacuna(int id, double suma, int idF, String svrha) {
        this.id = id;
        this.suma = suma;
        this.idF = idF;
        this.svrha = svrha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getSuma() {
        return suma;
    }

    public void setSuma(double suma) {
        this.suma = suma;
    }

    public int getIdF() {
        return idF;
    }

    public void setIdF(int idF) {
        this.idF = idF;
    }

    public String getSvrha() {
        return svrha;
    }

    public void setSvrha(String svrha) {
        this.svrha = svrha;
    }
}
