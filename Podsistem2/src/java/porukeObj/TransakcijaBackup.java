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
public class TransakcijaBackup implements Serializable{
    private int id;
    private String tip;
    private Date datumVreme;
    private double iznos;
    private int redniBroj;
    private String svrha;
    private int idRacuna;
    private int idFilijale;
    
    public TransakcijaBackup(){}

    public TransakcijaBackup(int id, String tip, Date datumVreme, double iznos, int redniBroj, String svrha, int idRacuna, int idFilijale) {
        this.id = id;
        this.tip = tip;
        this.datumVreme = datumVreme;
        this.iznos = iznos;
        this.redniBroj = redniBroj;
        this.svrha = svrha;
        this.idRacuna = idRacuna;
        this.idFilijale = idFilijale;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public Date getDatumVreme() {
        return datumVreme;
    }

    public void setDatumVreme(Date datumVreme) {
        this.datumVreme = datumVreme;
    }

    public double getIznos() {
        return iznos;
    }

    public void setIznos(double iznos) {
        this.iznos = iznos;
    }

    public int getRedniBroj() {
        return redniBroj;
    }

    public void setRedniBroj(int redniBroj) {
        this.redniBroj = redniBroj;
    }

    public String getSvrha() {
        return svrha;
    }

    public void setSvrha(String svrha) {
        this.svrha = svrha;
    }

    public int getIdRacuna() {
        return idRacuna;
    }

    public void setIdRacuna(int idRacuna) {
        this.idRacuna = idRacuna;
    }

    public int getIdFilijale() {
        return idFilijale;
    }

    public void setIdFilijale(int idFilijale) {
        this.idFilijale = idFilijale;
    }
    
}
