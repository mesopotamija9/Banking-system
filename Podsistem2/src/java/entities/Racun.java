/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Milos
 */
@Entity
@Table(name = "racun")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Racun.findAll", query = "SELECT r FROM Racun r"),
    @NamedQuery(name = "Racun.findById", query = "SELECT r FROM Racun r WHERE r.id = :id"),
    @NamedQuery(name = "Racun.findByStanje", query = "SELECT r FROM Racun r WHERE r.stanje = :stanje"),
    @NamedQuery(name = "Racun.findByDozvoljeniMinus", query = "SELECT r FROM Racun r WHERE r.dozvoljeniMinus = :dozvoljeniMinus"),
    @NamedQuery(name = "Racun.findByStatus", query = "SELECT r FROM Racun r WHERE r.status = :status"),
    @NamedQuery(name = "Racun.findByDatumVremeOtvaranja", query = "SELECT r FROM Racun r WHERE r.datumVremeOtvaranja = :datumVremeOtvaranja"),
    @NamedQuery(name = "Racun.findByBrojTransakcija", query = "SELECT r FROM Racun r WHERE r.brojTransakcija = :brojTransakcija"),
    @NamedQuery(name = "Racun.findByIdMesta", query = "SELECT r FROM Racun r WHERE r.idMesta = :idMesta")})
public class Racun implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "stanje")
    private double stanje;
    @Basic(optional = false)
    @NotNull
    @Column(name = "dozvoljeni_minus")
    private double dozvoljeniMinus;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "status")
    private String status;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datum_vreme_otvaranja")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumVremeOtvaranja;
    @Basic(optional = false)
    @NotNull
    @Column(name = "broj_transakcija")
    private int brojTransakcija;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "id_mesta")
    private String idMesta;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idRacuna")
    private List<Transakcija> transakcijaList;
    @JoinColumn(name = "id_komitenta", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Komitent idKomitenta;

    public Racun() {
    }

    public Racun(Integer id) {
        this.id = id;
    }

    public Racun(Integer id, double stanje, double dozvoljeniMinus, String status, Date datumVremeOtvaranja, int brojTransakcija, String idMesta) {
        this.id = id;
        this.stanje = stanje;
        this.dozvoljeniMinus = dozvoljeniMinus;
        this.status = status;
        this.datumVremeOtvaranja = datumVremeOtvaranja;
        this.brojTransakcija = brojTransakcija;
        this.idMesta = idMesta;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getIdMesta() {
        return idMesta;
    }

    public void setIdMesta(String idMesta) {
        this.idMesta = idMesta;
    }

    @XmlTransient
    public List<Transakcija> getTransakcijaList() {
        return transakcijaList;
    }

    public void setTransakcijaList(List<Transakcija> transakcijaList) {
        this.transakcijaList = transakcijaList;
    }

    public Komitent getIdKomitenta() {
        return idKomitenta;
    }

    public void setIdKomitenta(Komitent idKomitenta) {
        this.idKomitenta = idKomitenta;
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
        if (!(object instanceof Racun)) {
            return false;
        }
        Racun other = (Racun) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Racun[ id=" + id + " ]";
    }
    
}
