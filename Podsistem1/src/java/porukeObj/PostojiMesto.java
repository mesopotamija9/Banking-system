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
public class PostojiMesto implements Serializable{
    private String postanskiBroj;
    private boolean postoji;

    public PostojiMesto(String postanskiBroj, boolean postoji) {
        this.postanskiBroj = postanskiBroj;
        this.postoji = postoji;
    }

    public String getPostanskiBroj() {
        return postanskiBroj;
    }

    public void setPostanskiBroj(String postanskiBroj) {
        this.postanskiBroj = postanskiBroj;
    }

    public boolean isPostoji() {
        return postoji;
    }

    public void setPostoji(boolean postoji) {
        this.postoji = postoji;
    }
    
    
}
