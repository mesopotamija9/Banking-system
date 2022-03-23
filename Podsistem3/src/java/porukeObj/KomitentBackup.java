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
public class KomitentBackup implements Serializable{
    private int id;
    private String naziv;
    private String adresa;
    private String postanskiBroj;
    
    public KomitentBackup() {
    }

    public KomitentBackup(int id, String naziv, String adresa, String postanskiBroj) {
        this.id = id;
        this.naziv = naziv;
        this.adresa = adresa;
        this.postanskiBroj = postanskiBroj;
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

    public String getPostanskiBroj() {
        return postanskiBroj;
    }

    public void setPostanskiBroj(String postanskiBroj) {
        this.postanskiBroj = postanskiBroj;
    }
    
    
}
