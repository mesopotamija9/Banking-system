package entities;

import entities.Racun;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-01-31T12:01:22")
@StaticMetamodel(Transakcija.class)
public class Transakcija_ { 

    public static volatile SingularAttribute<Transakcija, Double> iznos;
    public static volatile SingularAttribute<Transakcija, Racun> idRacuna;
    public static volatile SingularAttribute<Transakcija, Date> datumVreme;
    public static volatile SingularAttribute<Transakcija, String> svrha;
    public static volatile SingularAttribute<Transakcija, String> tip;
    public static volatile SingularAttribute<Transakcija, Integer> id;
    public static volatile SingularAttribute<Transakcija, Integer> idFilijale;
    public static volatile SingularAttribute<Transakcija, Integer> redniBroj;

}