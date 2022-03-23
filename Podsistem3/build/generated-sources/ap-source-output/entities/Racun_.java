package entities;

import entities.Komitent;
import entities.Mesto;
import entities.Transakcija;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-01-31T12:01:22")
@StaticMetamodel(Racun.class)
public class Racun_ { 

    public static volatile SingularAttribute<Racun, Integer> brojTransakcija;
    public static volatile SingularAttribute<Racun, Mesto> idMesta;
    public static volatile SingularAttribute<Racun, Double> stanje;
    public static volatile ListAttribute<Racun, Transakcija> transakcijaList;
    public static volatile SingularAttribute<Racun, Date> datumVremeOtvaranja;
    public static volatile SingularAttribute<Racun, Integer> id;
    public static volatile SingularAttribute<Racun, Double> dozvoljeniMinus;
    public static volatile SingularAttribute<Racun, Komitent> idKomitenta;
    public static volatile SingularAttribute<Racun, String> status;

}