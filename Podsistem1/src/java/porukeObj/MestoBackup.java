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
public class MestoBackup implements Serializable{
    private String postanskiBroj;
    private String naziv;

    public MestoBackup(String postanskiBroj, String naziv) {
        this.postanskiBroj = postanskiBroj;
        this.naziv = naziv;
    }

    public String getPostanskiBroj() {
        return postanskiBroj;
    }

    public void setPostanskiBroj(String postanskiBroj) {
        this.postanskiBroj = postanskiBroj;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }
    
}
