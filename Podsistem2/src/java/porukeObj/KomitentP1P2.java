/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package porukeObj;

import entities.Komitent;
import entities.Mesto;
import java.io.Serializable;

/**
 *
 * @author Milos
 */
public class KomitentP1P2 implements Serializable{
    private int id;
    private String naziv;
    private String adresa;
    private Mesto mesto;
    private String akcija;

    public KomitentP1P2(int id, String naziv, String adresa, Mesto mesto, String akcija) {
        this.id = id;
        this.naziv = naziv;
        this.adresa = adresa;
        this.mesto = mesto;
        this.akcija = akcija;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public Mesto getMesto() {
        return mesto;
    }

    public void setMesto(Mesto mesto) {
        this.mesto = mesto;
    }

    public String getAkcija() {
        return akcija;
    }

    public void setAkcija(String akcija) {
        this.akcija = akcija;
    }

   
    
    
}
