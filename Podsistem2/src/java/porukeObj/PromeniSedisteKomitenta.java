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
public class PromeniSedisteKomitenta implements Serializable{
    private int id;
    private String postanskiBroj;

    public PromeniSedisteKomitenta(int id, String postanskiBroj) {
        this.id = id;
        this.postanskiBroj = postanskiBroj;
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
    
    
}
