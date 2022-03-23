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
public class OtvaranjeRacuna implements Serializable {
    private int id;
    private String postanskiBroj;
    private double dozvoljeniMinus;

    public OtvaranjeRacuna(int id, String postanskiBroj, double dozvoljeniMinus) {
        this.id = id;
        this.postanskiBroj = postanskiBroj;
        this.dozvoljeniMinus = dozvoljeniMinus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPostanskiBroj() {
        return postanskiBroj;
    }

    public void setPostanskiBroj(String postanskiBroj) {
        this.postanskiBroj = postanskiBroj;
    }

    public double getDozvoljeniMinus() {
        return dozvoljeniMinus;
    }

    public void setDozvoljeniMinus(double dozvoljeniMinus) {
        this.dozvoljeniMinus = dozvoljeniMinus;
    }
    
    
}
