/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podsistem2;

import entities.Komitent;
import entities.Racun;
import entities.Transakcija;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import porukeObj.DohvatiRazlikuPodataka;
import porukeObj.DohvatiSveRacuneZaKomitenta;
import porukeObj.DohvatiSveTransakcijeZaRacun;
import porukeObj.IsplataSaRacuna;
import porukeObj.KomitentP1P2;
import porukeObj.OtvaranjeRacuna;
import porukeObj.PrenosNovca;
import porukeObj.RacunBackup;
import porukeObj.TransakcijaBackup;
import porukeObj.UplataNaRacun;
import porukeObj.ZatvaranjeRacuna;

/**
 *
 * @author Milos
 */
public class Main {
    
    @Resource(lookup = "qp2")
    public static Queue queue;
    
    @Resource(lookup = "qp1p22")
    private static Queue queueP1P2;
    
    @Resource(lookup = "qp2p11")
    private static Queue queueP2P1;
    
    @Resource(lookup = "postojiMesto")
    private static Queue postojiMesto;
    
    @Resource(lookup = "queueP3P2")
    public static Queue queueP3P2;
    
    @Resource(lookup = "queueP2P333")
    public static Queue queueP2P3;
    
    @Resource(lookup = "qps22")
    public static Queue queueResponse;
    
    @Resource(lookup = "jms/__defaultConnectionFactory")
    private static ConnectionFactory connectionFactory;
    
    public static void main(String[] args) {
        JMSContext context = connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        JMSConsumer consumer = context.createConsumer(queue);
        JMSConsumer consumerP1P2 = context.createConsumer(queueP1P2);
        JMSConsumer consumerP3P2 = context.createConsumer(queueP3P2);
        JMSConsumer consumerPostojiMesto = context.createConsumer(postojiMesto);
        System.out.println("Podsistem 2 je pokrenut");
        
        consumerP3P2.setMessageListener((Message m) -> {
            if (!(m instanceof ObjectMessage)) {
                System.out.println("Poruka odbacena.");
            } else {
                try {
                    ObjectMessage objMsg= (ObjectMessage)m;
                    Serializable obj = objMsg.getObject();
                    
                    if (obj instanceof RacunBackup){
                        System.out.println("Podsistem 3 trazi backup racuna");
                        
                        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem2PU");
                        EntityManager em = emf.createEntityManager();
                        
                        List<Racun> racuni = em.createNamedQuery("Racun.findAll", Racun.class).getResultList();
                        ArrayList<RacunBackup> racuniZaSlanje = new ArrayList<>();
                        
                        for (Racun ra: racuni){
                            RacunBackup racunBackup = new RacunBackup(ra.getId(), ra.getStanje(), ra.getDozvoljeniMinus(), ra.getStatus(), ra.getDatumVremeOtvaranja(), ra.getBrojTransakcija(), ra.getIdKomitenta().getId(), ra.getIdMesta());
                            racuniZaSlanje.add(racunBackup);
                        }
                        
                        ObjectMessage racuniBackupObj = context.createObjectMessage(racuniZaSlanje);
                        
                        producer.send(queueP2P3, racuniBackupObj);
                        System.out.println("Racuni poslati podsistemu 3");
                        
                        em.clear();
                        emf.close();
                    } else if (obj instanceof TransakcijaBackup){
                        System.out.println("Podsistem 3 trazi backup transakcija");
                        
                        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem2PU");
                        EntityManager em = emf.createEntityManager();
                        
                        List<Transakcija> transakcije = em.createNamedQuery("Transakcija.findAll", Transakcija.class).getResultList();
                        ArrayList<TransakcijaBackup> transakcijeZaSlanje = new ArrayList<>();
                        
                        for (Transakcija tr: transakcije){
                            TransakcijaBackup transakcijaBackup = new TransakcijaBackup(tr.getId(), tr.getTip(), tr.getDatumVreme(), tr.getIznos(), tr.getRedniBroj(), tr.getSvrha(), tr.getIdRacuna().getId(), tr.getIdFilijale());
                            transakcijeZaSlanje.add(transakcijaBackup);
                        }
                        
                        ObjectMessage transakcijeBackupObj = context.createObjectMessage(transakcijeZaSlanje);
                        
                        producer.send(queueP2P3, transakcijeBackupObj);
                        System.out.println("Transakcije poslati podsistemu 3");
                        
                        em.clear();
                        emf.close();
                    } else if (obj instanceof DohvatiRazlikuPodataka){
                        System.out.println("Dohvatanje razlike podataka");
                        
                        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem2PU");
                        EntityManager em = emf.createEntityManager();
                        
                        List<Racun> racuni = em.createNamedQuery("Racun.findAll", Racun.class).getResultList();
                        ArrayList<RacunBackup> racuniZaSlanje = new ArrayList<>();
                        
                        for (Racun ra: racuni){
                            RacunBackup racunBackup = new RacunBackup(ra.getId(), ra.getStanje(), ra.getDozvoljeniMinus(), ra.getStatus(), ra.getDatumVremeOtvaranja(), ra.getBrojTransakcija(), ra.getIdKomitenta().getId(), ra.getIdMesta());
                            racuniZaSlanje.add(racunBackup);
                        }
                        
                        ObjectMessage racuniBackupObj = context.createObjectMessage(racuniZaSlanje);
                        
                        producer.send(queueP2P3, racuniBackupObj);
                        System.out.println("Racuni poslati podsistemu 3");
                        
                        List<Transakcija> transakcije = em.createNamedQuery("Transakcija.findAll", Transakcija.class).getResultList();
                        ArrayList<TransakcijaBackup> transakcijeZaSlanje = new ArrayList<>();
                        
                        for (Transakcija tr: transakcije){
                            TransakcijaBackup transakcijaBackup = new TransakcijaBackup(tr.getId(), tr.getTip(), tr.getDatumVreme(), tr.getIznos(), tr.getRedniBroj(), tr.getSvrha(), tr.getIdRacuna().getId(), tr.getIdFilijale());
                            transakcijeZaSlanje.add(transakcijaBackup);
                        }
                        
                        ObjectMessage transakcijeBackupObj = context.createObjectMessage(transakcijeZaSlanje);
                        
                        producer.send(queueP2P3, transakcijeBackupObj);
                        System.out.println("Transakcije poslati podsistemu 3");
                        
                        em.clear();
                        emf.close();
                    }
                } catch (JMSException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        consumerP1P2.setMessageListener((Message m) -> {
            if (!(m instanceof ObjectMessage)) {
                System.out.println("Poruka odbacena.");
            } else {
                try {
                    ObjectMessage objMsg= (ObjectMessage)m;
                    Serializable obj = objMsg.getObject();
                    
                    if (obj instanceof KomitentP1P2){
                        KomitentP1P2 komitentP1P2 = (KomitentP1P2)objMsg.getObject();
                        
                        if (komitentP1P2.getAkcija().equals("CREATE")){
                            EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem2PU");
                            EntityManager em = emf.createEntityManager();

                            Komitent noviKomitent = new Komitent();
                            noviKomitent.setId(komitentP1P2.getId());
                            noviKomitent.setNaziv(komitentP1P2.getNaziv());
                            noviKomitent.setAdresa(komitentP1P2.getAdresa());
                            noviKomitent.setIdMesta(komitentP1P2.getMesto().getPostanskiBroj());
                            
                            em.getTransaction().begin();
                            em.persist(noviKomitent);
                            em.getTransaction().commit();
                            em.clear();

                            emf.close();
                            System.out.println("Dodat novi komitent (Podsistem 1 inicirao akciju)");
                        } else if (komitentP1P2.getAkcija().equals("UPDATE")){
                            EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem2PU");
                            EntityManager em = emf.createEntityManager();

                            Komitent komitent = em.find(Komitent.class, komitentP1P2.getId());
                            
                            komitent.setIdMesta(komitentP1P2.getMesto().getPostanskiBroj());
                            
                            em.getTransaction().begin();
                            em.persist(komitent);
                            em.getTransaction().commit();
                            em.clear();

                            emf.close();
                            System.out.println("Komitent uspesno azuriran (Podsistem 1 inicirao akciju)");
                        }
                    }
                    
                } catch (JMSException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
         while(true){
            Message m = consumer.receive();
            if (!(m instanceof ObjectMessage)) {
                System.out.println("Poruka odbacena.");
                continue;
            } else {
                try {
                    ObjectMessage objMsg= (ObjectMessage)m;
                    Serializable obj = objMsg.getObject();
                    
                    if (obj instanceof OtvaranjeRacuna){
                        OtvaranjeRacuna otvaranjeRacuna = (OtvaranjeRacuna)objMsg.getObject();
                        
                        double stanje = 0;
                        double dozvoljeniMinus = otvaranjeRacuna.getDozvoljeniMinus();
                        String status = "aktivan";
                        Date datum = new Date();
                        int brojTransakcija = 0;
                        int idKomitenta = otvaranjeRacuna.getId();
                        String postanskiBroj = otvaranjeRacuna.getPostanskiBroj();
                        
                        // Proveri da li postoji komitent sa cije je id: idKomitenta
                        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem2PU");
                        EntityManager em = emf.createEntityManager();
                        
                        Komitent komitent = em.find(Komitent.class, idKomitenta);
                        
                        if (komitent == null){
                            StringBuilder sb = new StringBuilder();
                            sb.append("Ne postoji komitent ciji je id: " + idKomitenta)
                                    .append("Otvaranje racuna nije uspelo");
                            
                            System.out.println(sb.toString());
                            TextMessage txtMsg = context.createTextMessage(sb.toString());
                            producer.send(queueResponse, txtMsg);
                            
                            continue;
                        }
                        
                        System.out.println("Id komitenta: " + idKomitenta + ", postanskiBroj: " + postanskiBroj + ", " + dozvoljeniMinus);
                        
                        Racun noviRacun = new Racun();
                        noviRacun.setStanje(stanje);
                        noviRacun.setDozvoljeniMinus(dozvoljeniMinus);
                        noviRacun.setStatus(status);
                        noviRacun.setDatumVremeOtvaranja(datum);
                        noviRacun.setBrojTransakcija(brojTransakcija);
                        noviRacun.setIdKomitenta(komitent);
                        noviRacun.setIdMesta(postanskiBroj);
                        
                        em.getTransaction().begin();
                        em.persist(noviRacun);
                        em.getTransaction().commit();
                        em.clear();

                        emf.close();
                        
                        System.out.println("Racun je uspesno otvoren");
                        TextMessage txtMsg = context.createTextMessage("Racun je uspesno otvoren");
                        producer.send(queueResponse, txtMsg);
                    } else if (obj instanceof ZatvaranjeRacuna){
                        ZatvaranjeRacuna zatvaranjeRacuna = (ZatvaranjeRacuna)objMsg.getObject();
                        
                        int id = zatvaranjeRacuna.getId();
                        
                        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem2PU");
                        EntityManager em = emf.createEntityManager();
                        
                        Racun racun = em.find(Racun.class, id);
                        
                        if (racun == null){
                            StringBuilder sb = new StringBuilder();
                            sb.append("Ne postoji racun cije je id: " + id + "\n")
                                    .append("Zatvaranje racuna nije uspelo");
                            System.out.println(sb.toString());
                            
                            TextMessage txtMsg = context.createTextMessage(sb.toString());
                            producer.send(queueResponse, txtMsg);
                            
                            continue;
                        }
                        
                        racun.setStatus("zatvoren");
                        
                        em.getTransaction().begin();
                        em.persist(racun);
                        em.getTransaction().commit();
                        em.clear();

                        emf.close();
                        System.out.println("Racun je uspesno zatvoren");
                        TextMessage txtMsg = context.createTextMessage("Racun je uspesno zatvoren");
                        producer.send(queueResponse, txtMsg);
                    } else if (obj instanceof UplataNaRacun){
                        UplataNaRacun uplataNaRacun = (UplataNaRacun)objMsg.getObject();
                        
                        int id = uplataNaRacun.getId();
                        double suma = uplataNaRacun.getSuma();
                        int idF = uplataNaRacun.getIdF();
                        String svrha = uplataNaRacun.getSvrha();
                        
                        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem2PU");
                        EntityManager em = emf.createEntityManager();
                        
                        Racun racun = em.find(Racun.class, id);
                        
                        if (racun == null){
                            StringBuilder sb = new StringBuilder();
                            sb.append("Ne postoji racun cije je id: " + id + "\n")
                                    .append("Uplata na racun nije uspela");
                            System.out.println(sb.toString());
                            
                            TextMessage txtMsg = context.createTextMessage(sb.toString());
                            producer.send(queueResponse, txtMsg);
                            
                            continue;
                        }
                        
                        if (racun.getStatus().equals("zatvoren")){
                            StringBuilder sb = new StringBuilder();
                            sb.append("Racun je zatvoren\n")
                                    .append("Uplata na racun nije uspela");
                            System.out.println(sb.toString());
                            
                            TextMessage txtMsg = context.createTextMessage(sb.toString());
                            producer.send(queueResponse, txtMsg);
                            
                            continue;
                        }
                        
                        racun.setStanje(racun.getStanje() + suma);
                        if (racun.getStanje() >= racun.getDozvoljeniMinus() * (-1)){
                            racun.setStatus("aktivan");
                        }
                        
                        racun.setBrojTransakcija(racun.getBrojTransakcija() + 1);
                        
                        Transakcija transakcija = new Transakcija();
                        transakcija.setTip("uplata");
                        transakcija.setDatumVreme(new Date());
                        transakcija.setIznos(suma);
                        transakcija.setRedniBroj(racun.getBrojTransakcija());
                        transakcija.setSvrha(svrha);
                        transakcija.setIdRacuna(racun);
                        transakcija.setIdFilijale(idF);
                        
                        em.getTransaction().begin();
                        em.persist(racun);
                        em.persist(transakcija);
                        em.getTransaction().commit();
                        em.clear();
                        emf.close();
                        
                        System.out.println("Transakcija je uspesno izvresna");
                        TextMessage txtMsg = context.createTextMessage("Transakcija je uspesno izvresna");
			producer.send(queueResponse, txtMsg);
                    } else if (obj instanceof IsplataSaRacuna){
                        IsplataSaRacuna isplataSaRacuna = (IsplataSaRacuna)objMsg.getObject();
                        
                        int id = isplataSaRacuna.getId();
                        double suma = isplataSaRacuna.getSuma();
                        int idF = isplataSaRacuna.getIdF();
                        String svrha = isplataSaRacuna.getSvrha();
                        
                        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem2PU");
                        EntityManager em = emf.createEntityManager();
                        
                        Racun racun = em.find(Racun.class, id);
                        
                        if (racun == null){
                            StringBuilder sb = new StringBuilder();
                            sb.append("Ne postoji racun cije je id: " + id + "\n")
                                    .append("Isplata sa racuna nije uspela");
                            System.out.println(sb.toString());
                            
                            TextMessage txtMsg = context.createTextMessage(sb.toString());
                            producer.send(queueResponse, txtMsg);
                            
                            continue;
                        }
                        
                        if (racun.getStatus().equals("zatvoren")){
                            StringBuilder sb = new StringBuilder();
                            sb.append("Racun je zatvoren\n")
                                    .append("Isplata sa racuna nije uspela");
                            System.out.println(sb.toString());
                            
                            TextMessage txtMsg = context.createTextMessage(sb.toString());
                            producer.send(queueResponse, txtMsg);
                            
                            continue;
                        }
                        
                        if (racun.getStatus().equals("blokiran")){
                            StringBuilder sb = new StringBuilder();
                            sb.append("Racun je blokiran\n")
                                    .append("Isplata sa racuna nije uspela");
                            System.out.println(sb.toString());
                            
                            TextMessage txtMsg = context.createTextMessage(sb.toString());
                            producer.send(queueResponse, txtMsg);
                            
                            continue;
                        }
                        
                        racun.setStanje(racun.getStanje() - suma);
                        if (racun.getStanje() < racun.getDozvoljeniMinus() * (-1)){
                            racun.setStatus("blokiran");
                        }
                        
                        racun.setBrojTransakcija(racun.getBrojTransakcija() + 1);
                        
                        Transakcija transakcija = new Transakcija();
                        transakcija.setTip("isplata");
                        transakcija.setDatumVreme(new Date());
                        transakcija.setIznos(suma);
                        transakcija.setRedniBroj(racun.getBrojTransakcija());
                        transakcija.setSvrha(svrha);
                        transakcija.setIdRacuna(racun);
                        transakcija.setIdFilijale(idF);
                        
                        em.getTransaction().begin();
                        em.persist(racun);
                        em.persist(transakcija);
                        em.getTransaction().commit();
                        em.clear();
                        emf.close();
                        
                        System.out.println("Transakcija je uspesno izvresna");
                        TextMessage txtMsg = context.createTextMessage("Transakcija je uspesno izvresna");
			producer.send(queueResponse, txtMsg);
                    } else if (obj instanceof PrenosNovca){
                        PrenosNovca prenosNovca = (PrenosNovca)objMsg.getObject();
                        
                        int idSaKogSePrenosi = prenosNovca.getIdSaKogSePrenosi();
                        int idNaKojiSePrenosi = prenosNovca.getIdNaKojiSePrenosi();
                        double sumaPrenosa = prenosNovca.getSumaPrenosa();
                        String svrha = prenosNovca.getSvrha();
                        
                        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem2PU");
                        EntityManager em = emf.createEntityManager();
                        
                        Racun racunSaKogSePrenosi = em.find(Racun.class, idSaKogSePrenosi);
                        
                        if (racunSaKogSePrenosi == null){
                            StringBuilder sb = new StringBuilder();
                            sb.append("Ne postoji racun cije je id: " + idSaKogSePrenosi + "\n")
                                    .append("Prenos novca nije uspeo");
                            System.out.println(sb.toString());
                            
                            TextMessage txtMsg = context.createTextMessage(sb.toString());
                            producer.send(queueResponse, txtMsg);
                            
                            continue;
                        }
                        
                        if (racunSaKogSePrenosi.getStatus().equals("zatvoren")){
                            StringBuilder sb = new StringBuilder();
                            sb.append("Racun sa kog se prenosi novac je zatvoren\n")
                                    .append("Prenos novca nije uspeo");
                            System.out.println(sb.toString());
                            
                            TextMessage txtMsg = context.createTextMessage(sb.toString());
                            producer.send(queueResponse, txtMsg);
                            
                            continue;
                        }
                        
                        if (racunSaKogSePrenosi.getStatus().equals("blokiran")){
                            StringBuilder sb = new StringBuilder();
                            sb.append("Racun sa kog se prenosi novac je blokiran\n")
                                    .append("Prenos novca nije uspeo");
                            System.out.println(sb.toString());
                            
                            TextMessage txtMsg = context.createTextMessage(sb.toString());
                            producer.send(queueResponse, txtMsg);
                            
                            continue;
                        }
                        
                        Racun racunNaKojiSePrenosi = em.find(Racun.class, idNaKojiSePrenosi);
                        
                        if (racunNaKojiSePrenosi == null){
                            StringBuilder sb = new StringBuilder();
                            sb.append("Ne postoji racun cije je id: " + idNaKojiSePrenosi + "\n")
                                    .append("Prenos novca nije uspeo");
                            System.out.println(sb.toString());
                            
                            TextMessage txtMsg = context.createTextMessage(sb.toString());
                            producer.send(queueResponse, txtMsg);
                            
                            continue;
                        }
                        
                        if (racunNaKojiSePrenosi.getStatus().equals("zatvoren")){
                            StringBuilder sb = new StringBuilder();
                            sb.append("Racun na koji se prenosi novac je zatvoren\n")
                                    .append("Prenos novca nije uspeo");
                            System.out.println(sb.toString());
                            
                            TextMessage txtMsg = context.createTextMessage(sb.toString());
                            producer.send(queueResponse, txtMsg);
                            
                            continue;
                        }
                        
                        racunSaKogSePrenosi.setStanje(racunSaKogSePrenosi.getStanje() - sumaPrenosa);
                        if (racunSaKogSePrenosi.getStanje() < racunSaKogSePrenosi.getDozvoljeniMinus() * (-1)){
                            racunSaKogSePrenosi.setStatus("blokiran");
                        }
                        
                        Date datumPrenosa = new Date();
                        
                        racunSaKogSePrenosi.setBrojTransakcija(racunSaKogSePrenosi.getBrojTransakcija() + 1);
                        
                        Transakcija transakcijaSaKogSePrenosi = new Transakcija();
                        transakcijaSaKogSePrenosi.setTip("prenos posiljalac");
                        transakcijaSaKogSePrenosi.setDatumVreme(datumPrenosa);
                        transakcijaSaKogSePrenosi.setIznos(sumaPrenosa);
                        transakcijaSaKogSePrenosi.setRedniBroj(racunSaKogSePrenosi.getBrojTransakcija());
                        transakcijaSaKogSePrenosi.setSvrha(svrha);
                        transakcijaSaKogSePrenosi.setIdRacuna(racunSaKogSePrenosi);
                        
                        racunNaKojiSePrenosi.setStanje(racunNaKojiSePrenosi.getStanje() + sumaPrenosa);
                        if (racunNaKojiSePrenosi.getStanje() >= racunNaKojiSePrenosi.getDozvoljeniMinus() * (-1)){
                            racunNaKojiSePrenosi.setStatus("aktivan");
                        }
                        
                        racunNaKojiSePrenosi.setBrojTransakcija(racunNaKojiSePrenosi.getBrojTransakcija() + 1);
                        
                        Transakcija transakcijaNaKojiSePrenosi = new Transakcija();
                        transakcijaNaKojiSePrenosi.setTip("prenos primalac");
                        transakcijaNaKojiSePrenosi.setDatumVreme(datumPrenosa);
                        transakcijaNaKojiSePrenosi.setIznos(sumaPrenosa);
                        transakcijaNaKojiSePrenosi.setRedniBroj(racunNaKojiSePrenosi.getBrojTransakcija());
                        transakcijaNaKojiSePrenosi.setSvrha(svrha);
                        transakcijaNaKojiSePrenosi.setIdRacuna(racunNaKojiSePrenosi);
                        
                        em.getTransaction().begin();
                        em.persist(racunSaKogSePrenosi);
                        em.persist(racunNaKojiSePrenosi);
                        em.persist(transakcijaSaKogSePrenosi);
                        em.persist(transakcijaNaKojiSePrenosi);
                        em.getTransaction().commit();
                        em.clear();
                        emf.close();
                        
                        System.out.println("Transakcija je uspesno izvresna");
                        TextMessage txtMsg = context.createTextMessage("Transakcija je uspesno izvresna");
			producer.send(queueResponse, txtMsg);
                    } else if (obj instanceof DohvatiSveRacuneZaKomitenta){
                        DohvatiSveRacuneZaKomitenta dohvatiSveRacuneZaKomitenta = (DohvatiSveRacuneZaKomitenta)objMsg.getObject();
                        
                        int id = dohvatiSveRacuneZaKomitenta.getId();
                        
                        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem2PU");
                        EntityManager em = emf.createEntityManager();
                        
                        Komitent komitent = em.find(Komitent.class, id);
                        
                        if (komitent == null){
                            StringBuilder sb = new StringBuilder();
                            sb.append("Ne postoji komitent cije je id: " + id + "\n")
                                    .append("Dohvatanje racuna nije uspelo");
                            System.out.println(sb.toString());
                            
                            TextMessage txtMsg = context.createTextMessage(sb.toString());
                            producer.send(queueResponse, txtMsg);
                            
                            continue;
                        }
                        
                        List<Racun> racuni = em.createNamedQuery("Racun.findAll", Racun.class).getResultList();
                        
                        StringBuilder sb = new StringBuilder();
                        
                        for(Racun r: racuni){
                            if (r.getIdKomitenta() == komitent){
                                sb.append("id: " + r.getId() + ", stanje: " + r.getStanje() + ", dozvoljeni minus: " + r.getDozvoljeniMinus() + ", datum i vreme otvaranja: " + r.getDatumVremeOtvaranja() + ", broj transakcija: " + r.getBrojTransakcija() + ", id komitenta: " + r.getIdKomitenta().getId() + ", id mesta: " + r.getIdMesta() + "\n");
                            }
                        }
                        
                        System.out.println(sb.toString());
                        
                        TextMessage txtMsg = context.createTextMessage(sb.toString());
			producer.send(queueResponse, txtMsg);
                        
                        em.clear();
                        emf.close();
                    } else if (obj instanceof DohvatiSveTransakcijeZaRacun){
                        DohvatiSveTransakcijeZaRacun dohvatiSveTransakcijeZaRacun = (DohvatiSveTransakcijeZaRacun)objMsg.getObject();
                        
                        int id = dohvatiSveTransakcijeZaRacun.getId();
                        
                        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem2PU");
                        EntityManager em = emf.createEntityManager();
                        
                        Racun racun = em.find(Racun.class, id);
                        
                        if (racun == null){
                            StringBuilder sb = new StringBuilder();
                            sb.append("Ne postoji racun cije je id: " + id + "\n")
                                    .append("Dohvatanje transakcija nije uspelo");
                            System.out.println(sb.toString());
                            
                            TextMessage txtMsg = context.createTextMessage(sb.toString());
                            producer.send(queueResponse, txtMsg);
                            
                            continue;
                        }
                        
                        List<Transakcija> transakcije = em.createNamedQuery("Transakcija.findAll", Transakcija.class).getResultList();
                        
                        StringBuilder sb = new StringBuilder();
                        
                        for(Transakcija t: transakcije){
                            if (t.getIdRacuna() == racun){
                                sb.append("id: " + t.getId() + ", tip: " + t.getTip() + ", datum i vreme: " + t.getDatumVreme() + ", iznos: " + t.getIznos() + ", redni broj: " + t.getRedniBroj() + ", svrha: " + t.getSvrha() + ", id racuna: " + t.getIdRacuna().getId() + ", id filijale: " + t.getIdFilijale() + "\n");
                            }
                        }
                        
                        System.out.println(sb.toString());
                        
                        TextMessage txtMsg = context.createTextMessage(sb.toString());
			producer.send(queueResponse, txtMsg);
                        
                        em.clear();
                        emf.close();
                    }
                    
                } catch (JMSException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
}
