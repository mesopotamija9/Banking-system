/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Milos
 */
@Entity
@Table(name = "transakcija")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Transakcija.findAll", query = "SELECT t FROM Transakcija t"),
    @NamedQuery(name = "Transakcija.findById", query = "SELECT t FROM Transakcija t WHERE t.id = :id"),
    @NamedQuery(name = "Transakcija.findByTip", query = "SELECT t FROM Transakcija t WHERE t.tip = :tip"),
    @NamedQuery(name = "Transakcija.findByDatumVreme", query = "SELECT t FROM Transakcija t WHERE t.datumVreme = :datumVreme"),
    @NamedQuery(name = "Transakcija.findByIznos", query = "SELECT t FROM Transakcija t WHERE t.iznos = :iznos"),
    @NamedQuery(name = "Transakcija.findByRedniBroj", query = "SELECT t FROM Transakcija t WHERE t.redniBroj = :redniBroj"),
    @NamedQuery(name = "Transakcija.findBySvrha", query = "SELECT t FROM Transakcija t WHERE t.svrha = :svrha"),
    @NamedQuery(name = "Transakcija.findByIdFilijale", query = "SELECT t FROM Transakcija t WHERE t.idFilijale = :idFilijale")})
public class Transakcija implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "tip")
    private String tip;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datum_vreme")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumVreme;
    @Basic(optional = false)
    @NotNull
    @Column(name = "iznos")
    private double iznos;
    @Basic(optional = false)
    @NotNull
    @Column(name = "redni_broj")
    private int redniBroj;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "svrha")
    private String svrha;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_filijale")
    private int idFilijale;
    @JoinColumn(name = "id_racuna", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Racun idRacuna;

    public Transakcija() {
    }

    public Transakcija(Integer id) {
        this.id = id;
    }

    public Transakcija(Integer id, String tip, Date datumVreme, double iznos, int redniBroj, String svrha, int idFilijale) {
        this.id = id;
        this.tip = tip;
        this.datumVreme = datumVreme;
        this.iznos = iznos;
        this.redniBroj = redniBroj;
        this.svrha = svrha;
        this.idFilijale = idFilijale;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public int getIdFilijale() {
        return idFilijale;
    }

    public void setIdFilijale(int idFilijale) {
        this.idFilijale = idFilijale;
    }

    public Racun getIdRacuna() {
        return idRacuna;
    }

    public void setIdRacuna(Racun idRacuna) {
        this.idRacuna = idRacuna;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transakcija)) {
            return false;
        }
        Transakcija other = (Transakcija) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Transakcija[ id=" + id + " ]";
    }
    
}
