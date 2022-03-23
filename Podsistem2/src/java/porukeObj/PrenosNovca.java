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
public class PrenosNovca implements Serializable{
    private int idSaKogSePrenosi;
    private int idNaKojiSePrenosi;
    private double sumaPrenosa;
    private String svrha;

    public PrenosNovca(int idSaKogSePrenosi, int idNaKojiSePrenosi, double sumaPrenosa, String svrha) {
        this.idSaKogSePrenosi = idSaKogSePrenosi;
        this.idNaKojiSePrenosi = idNaKojiSePrenosi;
        this.sumaPrenosa = sumaPrenosa;
        this.svrha = svrha;
    }

    public int getIdSaKogSePrenosi() {
        return idSaKogSePrenosi;
    }

    public void setIdSaKogSePrenosi(int idSaKogSePrenosi) {
        this.idSaKogSePrenosi = idSaKogSePrenosi;
    }

    public int getIdNaKojiSePrenosi() {
        return idNaKojiSePrenosi;
    }

    public void setIdNaKojiSePrenosi(int idNaKojiSePrenosi) {
        this.idNaKojiSePrenosi = idNaKojiSePrenosi;
    }

    public double getSumaPrenosa() {
        return sumaPrenosa;
    }

    public void setSumaPrenosa(double sumaPrenosa) {
        this.sumaPrenosa = sumaPrenosa;
    }

    public String getSvrha() {
        return svrha;
    }

    public void setSvrha(String svrha) {
        this.svrha = svrha;
    }
    
    
}
