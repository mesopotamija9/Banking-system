/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package porukeObj;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Milos
 */
public class RacunBackup implements Serializable{
    private int id;
    private double stanje;
    private double dozvoljeniMinus;
    private String status;
    private Date datumVremeOtvaranja;
    private int brojTransakcija;
    private int idKomitenta;
    private String postanskiBroj;
    
    public RacunBackup(){}

    public RacunBackup(int id, double stanje, double dozvoljeniMinus, String status, Date datumVremeOtvaranja, int brojTransakcija, int idKomitenta, String postanskiBroj) {
        this.id = id;
        this.stanje = stanje;
        this.dozvoljeniMinus = dozvoljeniMinus;
        this.status = status;
        this.datumVremeOtvaranja = datumVremeOtvaranja;
        this.brojTransakcija = brojTransakcija;
        this.idKomitenta = idKomitenta;
        this.postanskiBroj = postanskiBroj;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getStanje() {
        return stanje;
    }

    public void setStanje(double stanje) {
        this.stanje = stanje;
    }

    public double getDozvoljeniMinus() {
        return dozvoljeniMinus;
    }

    public void setDozvoljeniMinus(double dozvoljeniMinus) {
        this.dozvoljeniMinus = dozvoljeniMinus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDatumVremeOtvaranja() {
        return datumVremeOtvaranja;
    }

    public void setDatumVremeOtvaranja(Date datumVremeOtvaranja) {
        this.datumVremeOtvaranja = datumVremeOtvaranja;
    }

    public int getBrojTransakcija() {
        return brojTransakcija;
    }

    public void setBrojTransakcija(int brojTransakcija) {
        this.brojTransakcija = brojTransakcija;
    }

    public int getIdKomitenta() {
        return idKomitenta;
    }

    public void setIdKomitenta(int idKomitenta) {
        this.idKomitenta = idKomitenta;
    }

    public String getPostanskiBroj() {
        return postanskiBroj;
    }

    public void setPostanskiBroj(String postanskiBroj) {
        this.postanskiBroj = postanskiBroj;
    }
    
}
