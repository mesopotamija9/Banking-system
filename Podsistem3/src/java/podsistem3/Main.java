/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podsistem3;

import entities.Filijala;
import entities.Komitent;
import entities.Mesto;
import entities.Racun;
import entities.Transakcija;
import java.io.Serializable;
import java.util.ArrayList;
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
import porukeObj.DohvatiBackup;
import porukeObj.DohvatiRazlikuPodataka;
import porukeObj.FilijalaBackup;
import porukeObj.KomitentBackup;
import porukeObj.MestoBackup;
import porukeObj.RacunBackup;
import porukeObj.RadiBackup;
import porukeObj.RadiTimerBackup;
import porukeObj.TransakcijaBackup;

/**
 *
 * @author Milos
 */
public class Main {

    @Resource(lookup = "qp3")
    public static Queue queue;
    
    @Resource(lookup = "qps3333333")
    public static Queue queueResponse;
    
    @Resource(lookup = "queueP3P1")
    public static Queue queueP3P1;
    
    @Resource(lookup = "queueP1P33")
    public static Queue queueP1P3;
    
    @Resource(lookup = "queueP3P2")
    public static Queue queueP3P2;
    
    @Resource(lookup = "queueP2P333")
    public static Queue queueP2P3;
    
    @Resource(lookup = "jms/__defaultConnectionFactory")
    private static ConnectionFactory connectionFactory;
    
    public static void main(String[] args) {
        JMSContext context = connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        JMSConsumer consumer = context.createConsumer(queue);
        JMSConsumer consumerP1P3 = context.createConsumer(queueP1P3);
        JMSConsumer consumerP2P3 = context.createConsumer(queueP2P3);
        System.out.println("Podsistem 3 je pokrenut");
        
        while(true){
            Message m = consumer.receive();
            if (!(m instanceof ObjectMessage)) {
                System.out.println("Poruka odbacena.");
                continue;
            } else {
                try {
                    ObjectMessage objMsg= (ObjectMessage)m;
                    Serializable obj = objMsg.getObject();
                    
                    if (obj instanceof RadiBackup){
                        System.out.println("Radi backup");
                        
                        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem3PU");
                        EntityManager em = emf.createEntityManager();
                        
                        // Backup Mesto
                        MestoBackup mestoBackup = new MestoBackup("", "");
                        ObjectMessage mestoBackupObj = context.createObjectMessage(mestoBackup);
                        producer.send(queueP3P1, mestoBackupObj);
                        
                        Message mestaMsg = consumerP1P3.receive();
                        ObjectMessage mestaObjMsg = (ObjectMessage)mestaMsg;
                        ArrayList<MestoBackup> listaMesta = (ArrayList<MestoBackup>)mestaObjMsg.getObject();
                        
                        for(MestoBackup mBackup: listaMesta){
                            Mesto mesto = em.find(Mesto.class, mBackup.getPostanskiBroj());
                            
                            if (mesto == null){
                                mesto = new Mesto();
                            } 
                            
                            mesto.setPostanskiBroj(mBackup.getPostanskiBroj());
                            mesto.setNaziv(mBackup.getNaziv());

                            em.getTransaction().begin();
                            em.persist(mesto);
                            em.getTransaction().commit();
                            
                            System.out.println("postanski broj: " + mBackup.getPostanskiBroj() + ", naziv: " + mBackup.getNaziv());
                        }
                        
                        // Backup Komitent
                        KomitentBackup komitentBackup = new KomitentBackup();
                        ObjectMessage komitentBackupObj = context.createObjectMessage(komitentBackup);
                        producer.send(queueP3P1, komitentBackupObj);
                        
                        Message komitentiMsg = consumerP1P3.receive();
                        ObjectMessage komitentiObjMsg = (ObjectMessage)komitentiMsg;
                        ArrayList<KomitentBackup> listaKomitenata = (ArrayList<KomitentBackup>)komitentiObjMsg.getObject();
                        
                        for(KomitentBackup kBackup: listaKomitenata){
                            Komitent komitent = em.find(Komitent.class, kBackup.getId());
                            
                            if (komitent == null){
                                komitent = new Komitent();
                            } 
                            
                            komitent.setId(kBackup.getId());
                            komitent.setNaziv(kBackup.getNaziv());
                            komitent.setAdresa(kBackup.getAdresa());
                            Mesto mesto = em.find(Mesto.class, kBackup.getPostanskiBroj());
                            komitent.setIdMesta(mesto);
                            
                            em.getTransaction().begin();
                            em.persist(komitent);
                            em.getTransaction().commit();
                            
                            System.out.println("id: " + kBackup.getId() + ", naziv: " + kBackup.getNaziv() + ", adresa: " + kBackup.getAdresa() + ", postanskiBroj: " + kBackup.getPostanskiBroj());
                        }
                        
                        // Backup Filijala
                        FilijalaBackup filijalaBackup = new FilijalaBackup();
                        ObjectMessage filijalaBackupObj = context.createObjectMessage(filijalaBackup);
                        producer.send(queueP3P1, filijalaBackupObj);
                        
                        Message filijaleMsg = consumerP1P3.receive();
                        ObjectMessage filijaleObjMsg = (ObjectMessage)filijaleMsg;
                        ArrayList<FilijalaBackup> listaFilijala = (ArrayList<FilijalaBackup>)filijaleObjMsg.getObject();
                        
                        for(FilijalaBackup fBackup: listaFilijala){
                            Filijala filijala = em.find(Filijala.class, fBackup.getId());
                            
                            if (filijala == null){
                                filijala = new Filijala();
                            } 
                            
                            filijala.setId(fBackup.getId());
                            filijala.setNaziv(fBackup.getNaziv());
                            filijala.setAdresa(fBackup.getAdresa());
                            Mesto mesto = em.find(Mesto.class, fBackup.getPostanskiBroj());
                            filijala.setIdMesta(mesto);
                            
                            em.getTransaction().begin();
                            em.persist(filijala);
                            em.getTransaction().commit();
                            
                            System.out.println("id: " + fBackup.getId() + ", naziv: " + fBackup.getNaziv() + ", adresa: " + fBackup.getAdresa() + ", postanski broj: " + fBackup.getPostanskiBroj());
                        }
                        
                        // Backup Racun
                        RacunBackup racunBackup = new RacunBackup();
                        ObjectMessage racunBackupObj = context.createObjectMessage(racunBackup);
                        producer.send(queueP3P2, racunBackupObj);
                        
                        Message racuniMsg = consumerP2P3.receive();
                        ObjectMessage racuniObjMsg = (ObjectMessage)racuniMsg;
                        ArrayList<RacunBackup> listaRacuna = (ArrayList<RacunBackup>)racuniObjMsg.getObject();
                        
                        for(RacunBackup rBackup: listaRacuna){
                            Racun racun = em.find(Racun.class, rBackup.getId());
                            
                            if (racun == null){
                                racun = new Racun();
                            } 
                            
                            racun.setId(rBackup.getId());
                            racun.setStanje(rBackup.getStanje());
                            racun.setDozvoljeniMinus(rBackup.getDozvoljeniMinus());
                            racun.setStatus(rBackup.getStatus());
                            racun.setDatumVremeOtvaranja(rBackup.getDatumVremeOtvaranja());
                            racun.setBrojTransakcija(rBackup.getBrojTransakcija());
                            Komitent komitent = em.find(Komitent.class, rBackup.getIdKomitenta());
                            racun.setIdKomitenta(komitent);
                            Mesto mesto = em.find(Mesto.class, rBackup.getPostanskiBroj());
                            racun.setIdMesta(mesto);
                            
                            em.getTransaction().begin();
                            em.persist(racun);
                            em.getTransaction().commit();
                            
                            System.out.println("id: " + rBackup.getId() + ", stanje: " + rBackup.getStanje() + ", dozvoljeni minus: " + rBackup.getDozvoljeniMinus() + ", datum i vreme otvaranja: " + rBackup.getDatumVremeOtvaranja() + ", brojTransakcija: " + rBackup.getBrojTransakcija() + ", id komitenta: " + rBackup.getIdKomitenta() + ", id mesta: " + rBackup.getPostanskiBroj());
                        }
                        
                        // Backup Transakcija
                        TransakcijaBackup transakcijaBackup = new TransakcijaBackup();
                        ObjectMessage transakcijaBackupObj = context.createObjectMessage(transakcijaBackup);
                        producer.send(queueP3P2, transakcijaBackupObj);
                        
                        Message transakcijeMsg = consumerP2P3.receive();
                        ObjectMessage transakcijeObjMsg = (ObjectMessage)transakcijeMsg;
                        ArrayList<TransakcijaBackup> listaTransakcija = (ArrayList<TransakcijaBackup>)transakcijeObjMsg.getObject();
                        
                        for(TransakcijaBackup tBackup: listaTransakcija){
                            Transakcija transakcija = em.find(Transakcija.class, tBackup.getId());
                            
                            if (transakcija == null){
                                transakcija = new Transakcija();
                            } 
                            
                            transakcija.setId(tBackup.getId());
                            transakcija.setTip(tBackup.getTip());
                            transakcija.setDatumVreme(tBackup.getDatumVreme());
                            transakcija.setIznos(tBackup.getIznos());
                            transakcija.setRedniBroj(tBackup.getRedniBroj());
                            transakcija.setSvrha(tBackup.getSvrha());
                            Racun racun = em.find(Racun.class, tBackup.getIdRacuna());
                            transakcija.setIdRacuna(racun);
                            if (tBackup.getIdFilijale() == -1){
                                transakcija.setIdFilijale(null);
                            } else {
                                Filijala filijala = em.find(Filijala.class, tBackup.getIdFilijale());
                                transakcija.setIdFilijale(filijala);
                            }
                            
                            em.getTransaction().begin();
                            em.persist(transakcija);
                            em.getTransaction().commit();
                            
                            System.out.println("id: " + tBackup.getId() + ", tip: " + tBackup.getTip() + ", datum i vreme: " + tBackup.getDatumVreme() + ", iznos: " + tBackup.getIznos() + ", redni broj: " + tBackup.getRedniBroj() + ", svrha: " + tBackup.getSvrha() + ", id racuna: " + tBackup.getIdRacuna() + ", id filijale: " + tBackup.getIdFilijale());
                        }
                        
                        em.clear();
                        emf.close();
                        
                        System.out.println("Backup usposno uradjen");
                        TextMessage txtMsg = context.createTextMessage("Backup usposno uradjen");
                        producer.send(queueResponse, txtMsg);
                    } else if (obj instanceof RadiTimerBackup){
                        System.out.println("Radi timer backup");
                        
                        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem3PU");
                        EntityManager em = emf.createEntityManager();
                        
                        // Backup Mesto
                        MestoBackup mestoBackup = new MestoBackup("", "");
                        ObjectMessage mestoBackupObj = context.createObjectMessage(mestoBackup);
                        producer.send(queueP3P1, mestoBackupObj);
                        
                        Message mestaMsg = consumerP1P3.receive();
                        ObjectMessage mestaObjMsg = (ObjectMessage)mestaMsg;
                        ArrayList<MestoBackup> listaMesta = (ArrayList<MestoBackup>)mestaObjMsg.getObject();
                        
                        for(MestoBackup mBackup: listaMesta){
                            Mesto mesto = em.find(Mesto.class, mBackup.getPostanskiBroj());
                            
                            if (mesto == null){
                                mesto = new Mesto();
                            } 
                            
                            mesto.setPostanskiBroj(mBackup.getPostanskiBroj());
                            mesto.setNaziv(mBackup.getNaziv());

                            em.getTransaction().begin();
                            em.persist(mesto);
                            em.getTransaction().commit();
                            
                            System.out.println("postanski broj: " + mBackup.getPostanskiBroj() + ", naziv: " + mBackup.getNaziv());
                        }
                        
                        // Backup Komitent
                        KomitentBackup komitentBackup = new KomitentBackup();
                        ObjectMessage komitentBackupObj = context.createObjectMessage(komitentBackup);
                        producer.send(queueP3P1, komitentBackupObj);
                        
                        Message komitentiMsg = consumerP1P3.receive();
                        ObjectMessage komitentiObjMsg = (ObjectMessage)komitentiMsg;
                        ArrayList<KomitentBackup> listaKomitenata = (ArrayList<KomitentBackup>)komitentiObjMsg.getObject();
                        
                        for(KomitentBackup kBackup: listaKomitenata){
                            Komitent komitent = em.find(Komitent.class, kBackup.getId());
                            
                            if (komitent == null){
                                komitent = new Komitent();
                            } 
                            
                            komitent.setId(kBackup.getId());
                            komitent.setNaziv(kBackup.getNaziv());
                            komitent.setAdresa(kBackup.getAdresa());
                            Mesto mesto = em.find(Mesto.class, kBackup.getPostanskiBroj());
                            komitent.setIdMesta(mesto);
                            
                            em.getTransaction().begin();
                            em.persist(komitent);
                            em.getTransaction().commit();
                            
                            System.out.println("id: " + kBackup.getId() + ", naziv: " + kBackup.getNaziv() + ", adresa: " + kBackup.getAdresa() + ", postanskiBroj: " + kBackup.getPostanskiBroj());
                        }
                        
                        // Backup Filijala
                        FilijalaBackup filijalaBackup = new FilijalaBackup();
                        ObjectMessage filijalaBackupObj = context.createObjectMessage(filijalaBackup);
                        producer.send(queueP3P1, filijalaBackupObj);
                        
                        Message filijaleMsg = consumerP1P3.receive();
                        ObjectMessage filijaleObjMsg = (ObjectMessage)filijaleMsg;
                        ArrayList<FilijalaBackup> listaFilijala = (ArrayList<FilijalaBackup>)filijaleObjMsg.getObject();
                        
                        for(FilijalaBackup fBackup: listaFilijala){
                            Filijala filijala = em.find(Filijala.class, fBackup.getId());
                            
                            if (filijala == null){
                                filijala = new Filijala();
                            } 
                            
                            filijala.setId(fBackup.getId());
                            filijala.setNaziv(fBackup.getNaziv());
                            filijala.setAdresa(fBackup.getAdresa());
                            Mesto mesto = em.find(Mesto.class, fBackup.getPostanskiBroj());
                            filijala.setIdMesta(mesto);
                            
                            em.getTransaction().begin();
                            em.persist(filijala);
                            em.getTransaction().commit();
                            
                            System.out.println("id: " + fBackup.getId() + ", naziv: " + fBackup.getNaziv() + ", adresa: " + fBackup.getAdresa() + ", postanski broj: " + fBackup.getPostanskiBroj());
                        }
                        
                        // Backup Racun
                        RacunBackup racunBackup = new RacunBackup();
                        ObjectMessage racunBackupObj = context.createObjectMessage(racunBackup);
                        producer.send(queueP3P2, racunBackupObj);
                        
                        Message racuniMsg = consumerP2P3.receive();
                        ObjectMessage racuniObjMsg = (ObjectMessage)racuniMsg;
                        ArrayList<RacunBackup> listaRacuna = (ArrayList<RacunBackup>)racuniObjMsg.getObject();
                        
                        for(RacunBackup rBackup: listaRacuna){
                            Racun racun = em.find(Racun.class, rBackup.getId());
                            
                            if (racun == null){
                                racun = new Racun();
                            } 
                            
                            racun.setId(rBackup.getId());
                            racun.setStanje(rBackup.getStanje());
                            racun.setDozvoljeniMinus(rBackup.getDozvoljeniMinus());
                            racun.setStatus(rBackup.getStatus());
                            racun.setDatumVremeOtvaranja(rBackup.getDatumVremeOtvaranja());
                            racun.setBrojTransakcija(rBackup.getBrojTransakcija());
                            Komitent komitent = em.find(Komitent.class, rBackup.getIdKomitenta());
                            racun.setIdKomitenta(komitent);
                            Mesto mesto = em.find(Mesto.class, rBackup.getPostanskiBroj());
                            racun.setIdMesta(mesto);
                            
                            em.getTransaction().begin();
                            em.persist(racun);
                            em.getTransaction().commit();
                            
                            System.out.println("id: " + rBackup.getId() + ", stanje: " + rBackup.getStanje() + ", dozvoljeni minus: " + rBackup.getDozvoljeniMinus() + ", datum i vreme otvaranja: " + rBackup.getDatumVremeOtvaranja() + ", brojTransakcija: " + rBackup.getBrojTransakcija() + ", id komitenta: " + rBackup.getIdKomitenta() + ", id mesta: " + rBackup.getPostanskiBroj());
                        }
                        
                        // Backup Transakcija
                        TransakcijaBackup transakcijaBackup = new TransakcijaBackup();
                        ObjectMessage transakcijaBackupObj = context.createObjectMessage(transakcijaBackup);
                        producer.send(queueP3P2, transakcijaBackupObj);
                        
                        Message transakcijeMsg = consumerP2P3.receive();
                        ObjectMessage transakcijeObjMsg = (ObjectMessage)transakcijeMsg;
                        ArrayList<TransakcijaBackup> listaTransakcija = (ArrayList<TransakcijaBackup>)transakcijeObjMsg.getObject();
                        
                        for(TransakcijaBackup tBackup: listaTransakcija){
                            Transakcija transakcija = em.find(Transakcija.class, tBackup.getId());
                            
                            if (transakcija == null){
                                transakcija = new Transakcija();
                            } 
                            
                            transakcija.setId(tBackup.getId());
                            transakcija.setTip(tBackup.getTip());
                            transakcija.setDatumVreme(tBackup.getDatumVreme());
                            transakcija.setIznos(tBackup.getIznos());
                            transakcija.setRedniBroj(tBackup.getRedniBroj());
                            transakcija.setSvrha(tBackup.getSvrha());
                            Racun racun = em.find(Racun.class, tBackup.getIdRacuna());
                            transakcija.setIdRacuna(racun);
                            if (tBackup.getIdFilijale() == -1){
                                transakcija.setIdFilijale(null);
                            } else {
                                Filijala filijala = em.find(Filijala.class, tBackup.getIdFilijale());
                                transakcija.setIdFilijale(filijala);
                            }
                            
                            em.getTransaction().begin();
                            em.persist(transakcija);
                            em.getTransaction().commit();
                            
                            System.out.println("id: " + tBackup.getId() + ", tip: " + tBackup.getTip() + ", datum i vreme: " + tBackup.getDatumVreme() + ", iznos: " + tBackup.getIznos() + ", redni broj: " + tBackup.getRedniBroj() + ", svrha: " + tBackup.getSvrha() + ", id racuna: " + tBackup.getIdRacuna() + ", id filijale: " + tBackup.getIdFilijale());
                        }
                        
                        em.clear();
                        emf.close();
                        
                        System.out.println("Timer backup usposno uradjen");
                    } else if (obj instanceof DohvatiBackup){
                        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem3PU");
                        EntityManager em = emf.createEntityManager();
                        
                        StringBuilder sb = new StringBuilder();
                        
                        // Dohvati mesta
                        List<Mesto> mesta = em.createNamedQuery("Mesto.findAll", Mesto.class).getResultList();
                        for (Mesto mm: mesta){
                            sb.append("postanski broj: ").append(mm.getPostanskiBroj()).append(", naziv: ").append(mm.getNaziv()).append("\n");
                        }
                        
                        sb.append("\n");
                        
                        // Dohvati komitente
                        List<Komitent> komitenti = em.createNamedQuery("Komitent.findAll", Komitent.class).getResultList();
                        
                        for (Komitent kk: komitenti){
                            sb.append("id: ").append(kk.getId()).append(", naziv: ").append(kk.getNaziv()).append(", adresa: ").append(kk.getAdresa()).append(", postanskiBroj: ").append(kk.getIdMesta().getPostanskiBroj()).append("\n");
                        }
                        
                        sb.append("\n");
                        
                        // Dohvati filijale
                        List<Filijala> filijale = em.createNamedQuery("Filijala.findAll", Filijala.class).getResultList();
                        
                        for (Filijala ff: filijale){
                            sb.append("id: ").append(ff.getId()).append(", naziv: ").append(ff.getNaziv()).append(", adresa: ").append(ff.getAdresa()).append(", postanski broj: ").append(ff.getIdMesta().getPostanskiBroj()).append("\n");
                        }
                        
                        sb.append("\n");
                        
                        // Dohvati racune
                        List<Racun> racuni = em.createNamedQuery("Racun.findAll", Racun.class).getResultList();
                        
                        for(Racun rr: racuni){
                            sb.append("id: ").append(rr.getId()).append(", stanje: ").append(rr.getStanje()).append(", dozvoljeni minus: ").append(rr.getDozvoljeniMinus()).append(", datum i vreme otvaranja: ").append(rr.getDatumVremeOtvaranja()).append(", brojTransakcija: ").append(rr.getBrojTransakcija()).append(", id komitenta: ").append(rr.getIdKomitenta().getId()).append(", id mesta: ").append(rr.getIdMesta().getPostanskiBroj()).append("\n");
                        }
                        
                        sb.append("\n");
                        
                        // Dohvati transakcije
                        List<Transakcija> transakcije = em.createNamedQuery("Transakcija.findAll", Transakcija.class).getResultList();
                        
                        for (Transakcija tt: transakcije){
                            String idFil = "null";
                            if (tt.getIdFilijale() != null){
                                idFil = String.valueOf(tt.getIdFilijale().getId());
                            }
                            sb.append("id: ").append(tt.getId()).append(", tip: ").append(tt.getTip()).append(", datum i vreme: ").append(tt.getDatumVreme()).append(", iznos: ").append(tt.getIznos()).append(", redni broj: ").append(tt.getRedniBroj()).append(", svrha: ").append(tt.getSvrha()).append(", id racuna: ").append(tt.getIdRacuna().getId()).append(", id filijale: ").append(idFil).append("\n");
                        }
                        
                        sb.append("\n");
                        
                        System.out.println(sb.toString());
                        
                        TextMessage txtMsg = context.createTextMessage(sb.toString());
			producer.send(queueResponse, txtMsg);
                        
                        em.clear();
                        emf.close();
                    } else if (obj instanceof DohvatiRazlikuPodataka){
                        System.out.println("Dohvati Razliku u podacima");
                        
                        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem3PU");
                        EntityManager em = emf.createEntityManager();
                        
                        DohvatiRazlikuPodataka dohvatiRazlikuPodataka = new DohvatiRazlikuPodataka();
                        ObjectMessage dohvatiRazlikuPodatakaObj = context.createObjectMessage(dohvatiRazlikuPodataka);
                        producer.send(queueP3P1, dohvatiRazlikuPodatakaObj);
                        producer.send(queueP3P2, dohvatiRazlikuPodatakaObj);
                        
                        StringBuilder sb = new StringBuilder();
                        
                        Message mestaMsg = consumerP1P3.receive();
                        ObjectMessage mestaObjMsg = (ObjectMessage)mestaMsg;
                        ArrayList<MestoBackup> listaMesta = (ArrayList<MestoBackup>)mestaObjMsg.getObject();
                        
                        sb.append("Razlika mesta\n");
                        
                        for(MestoBackup mBackup: listaMesta){
                            Mesto mesto = em.find(Mesto.class, mBackup.getPostanskiBroj());
                            
                            if (mesto == null){
                                sb.append("Original -> postanski broj: ").append(mBackup.getPostanskiBroj()).append(", naziv: ").append(mBackup.getNaziv()).append("\n");
                                sb.append("Kopija   -> null").append("\n");
                            } else {
                                if (!mesto.getPostanskiBroj().equals(mBackup.getPostanskiBroj()) ||
                                        !mesto.getNaziv().equals(mBackup.getNaziv())){
                                    sb.append("Original -> postanski broj: ").append(mBackup.getPostanskiBroj()).append(", naziv: ").append(mBackup.getNaziv()).append("\n");
                                    sb.append("Kopija   -> postanski broj: ").append(mesto.getPostanskiBroj()).append(", naziv: ").append(mesto.getNaziv()).append("\n");
                                }
                            }
                        }
                        
                        sb.append("\nRazlika komitenata\n");
                        
                        Message komitentiMsg = consumerP1P3.receive();
                        ObjectMessage komitentiObjMsg = (ObjectMessage)komitentiMsg;
                        ArrayList<KomitentBackup> listaKomitenata = (ArrayList<KomitentBackup>)komitentiObjMsg.getObject();
                        
                        for(KomitentBackup kBackup: listaKomitenata){
                            Komitent komitent = em.find(Komitent.class, kBackup.getId());
                            
                            if (komitent == null){
                                sb.append("Original -> ").append("id: ").append(kBackup.getId()).append(", naziv: ").append(kBackup.getNaziv()).append(", adresa: ").append(kBackup.getAdresa()).append(", postanskiBroj: ").append(kBackup.getPostanskiBroj()).append("\n");
                                sb.append("Kopija   -> null").append("\n");
                            } else {
                                if (!komitent.getId().equals(kBackup.getId()) ||
                                        !komitent.getNaziv().equals(kBackup.getNaziv()) ||
                                        !komitent.getAdresa().equals(kBackup.getAdresa()) ||
                                        !komitent.getIdMesta().getPostanskiBroj().equals(kBackup.getPostanskiBroj())){
                                        sb.append("Original -> ").append("id: ").append(kBackup.getId()).append(", naziv: ").append(kBackup.getNaziv()).append(", adresa: ").append(kBackup.getAdresa()).append(", postanskiBroj: ").append(kBackup.getPostanskiBroj()).append("\n");
                                        sb.append("Kopija   -> ").append("id: ").append(komitent.getId()).append(", naziv: ").append(komitent.getNaziv()).append(", adresa: ").append(komitent.getAdresa()).append(", postanskiBroj: ").append(komitent.getIdMesta().getPostanskiBroj()).append("\n");
                                }
                            }
                        }
                        
                        sb.append("\nRazlika filijala\n");
                        
                        Message filijaleMsg = consumerP1P3.receive();
                        ObjectMessage filijaleObjMsg = (ObjectMessage)filijaleMsg;
                        ArrayList<FilijalaBackup> listaFilijala = (ArrayList<FilijalaBackup>)filijaleObjMsg.getObject();
                        
                        for(FilijalaBackup fBackup: listaFilijala){
                            Filijala filijala = em.find(Filijala.class, fBackup.getId());
                            
                            if (filijala == null){
                                sb.append("Original -> ").append("id: ").append(fBackup.getId()).append(", naziv: ").append(fBackup.getNaziv()).append(", adresa: ").append(fBackup.getAdresa()).append(", postanski broj: ").append(fBackup.getPostanskiBroj()).append("\n");
                                sb.append("Kopija   -> null").append("\n");
                            } else {
                                if (!filijala.getId().equals(fBackup.getId()) ||
                                        !filijala.getNaziv().equals(fBackup.getNaziv()) ||
                                        !filijala.getAdresa().equals(fBackup.getAdresa()) ||
                                        !filijala.getIdMesta().getPostanskiBroj().equals(fBackup.getPostanskiBroj())){
                                    sb.append("Original -> ").append("id: ").append(fBackup.getId()).append(", naziv: ").append(fBackup.getNaziv()).append(", adresa: ").append(fBackup.getAdresa()).append(", postanski broj: ").append(fBackup.getPostanskiBroj()).append("\n");
                                    sb.append("Kopija   -> ").append("id: ").append(filijala.getId()).append(", naziv: ").append(filijala.getNaziv()).append(", adresa: ").append(filijala.getAdresa()).append(", postanski broj: ").append(filijala.getIdMesta().getPostanskiBroj()).append("\n");
                                }
                            }
                        }
                        
                        sb.append("\nRazlika racuna\n");
                        
                        Message racuniMsg = consumerP2P3.receive();
                        ObjectMessage racuniObjMsg = (ObjectMessage)racuniMsg;
                        ArrayList<RacunBackup> listaRacuna = (ArrayList<RacunBackup>)racuniObjMsg.getObject();
                        
                        for(RacunBackup rBackup: listaRacuna){
                            Racun racun = em.find(Racun.class, rBackup.getId());
                            
                            if (racun == null){
                                sb.append("Original -> ").append("id: ").append(rBackup.getId()).append(", stanje: ").append(rBackup.getStanje()).append(", status: ").append(rBackup.getStatus()).append(", dozvoljeni minus: ").append(rBackup.getDozvoljeniMinus()).append(", datum i vreme otvaranja: ").append(rBackup.getDatumVremeOtvaranja()).append(", brojTransakcija: ").append(rBackup.getBrojTransakcija()).append(", id komitenta: ").append(rBackup.getIdKomitenta()).append(", id mesta: ").append(rBackup.getPostanskiBroj()).append("\n");
                                sb.append("Kopija   -> null").append("\n");
                            } else {
                                if (!racun.getId().equals(rBackup.getId()) ||
                                        racun.getStanje() != rBackup.getStanje() ||
                                        racun.getDozvoljeniMinus() != rBackup.getDozvoljeniMinus() ||
                                        !racun.getStatus().equals(rBackup.getStatus()) ||
                                        !racun.getDatumVremeOtvaranja().equals(rBackup.getDatumVremeOtvaranja()) ||
                                        racun.getBrojTransakcija() != rBackup.getBrojTransakcija() ||
                                        !racun.getIdKomitenta().getId().equals(rBackup.getIdKomitenta()) ||
                                        !racun.getIdMesta().getPostanskiBroj().equals(rBackup.getPostanskiBroj())){
                                    sb.append("Original -> ").append("id: ").append(rBackup.getId()).append(", stanje: ").append(rBackup.getStanje()).append(", status: ").append(rBackup.getStatus()).append(", dozvoljeni minus: ").append(rBackup.getDozvoljeniMinus()).append(", datum i vreme otvaranja: ").append(rBackup.getDatumVremeOtvaranja()).append(", brojTransakcija: ").append(rBackup.getBrojTransakcija()).append(", id komitenta: ").append(rBackup.getIdKomitenta()).append(", id mesta: ").append(rBackup.getPostanskiBroj()).append("\n");
                                    sb.append("Kopija   -> ").append("id: ").append(racun.getId()).append(", stanje: ").append(racun.getStanje()).append(", status: ").append(racun.getStatus()).append(", dozvoljeni minus: ").append(racun.getDozvoljeniMinus()).append(", datum i vreme otvaranja: ").append(racun.getDatumVremeOtvaranja()).append(", brojTransakcija: ").append(racun.getBrojTransakcija()).append(", id komitenta: ").append(racun.getIdKomitenta().getId()).append(", id mesta: ").append(racun.getIdMesta().getPostanskiBroj()).append("\n");
                                }
                            }
                        }
                        
                        sb.append("\nRazlika transakcija\n");
                        
                        Message transakcijeMsg = consumerP2P3.receive();
                        ObjectMessage transakcijeObjMsg = (ObjectMessage)transakcijeMsg;
                        ArrayList<TransakcijaBackup> listaTransakcija = (ArrayList<TransakcijaBackup>)transakcijeObjMsg.getObject();
                        
                        for(TransakcijaBackup tBackup: listaTransakcija){
                            Transakcija transakcija = em.find(Transakcija.class, tBackup.getId());
                            
                            if (transakcija == null){
                                sb.append("Original -> ").append("id: ").append(tBackup.getId()).append(", tip: ").append(tBackup.getTip()).append(", datum i vreme: ").append(tBackup.getDatumVreme()).append(", iznos: ").append(tBackup.getIznos()).append(", redni broj: ").append(tBackup.getRedniBroj()).append(", svrha: ").append(tBackup.getSvrha()).append(", id racuna: ").append(tBackup.getIdRacuna()).append(", id filijale: ").append(tBackup.getIdFilijale()).append("\n");
                                sb.append("Kopija   -> null").append("\n");
                            } else {
                                int idFil;
                                if (transakcija.getIdFilijale() == null){
                                    idFil = 0;
                                } else {
                                    idFil = transakcija.getIdFilijale().getId();
                                }
                                if (!transakcija.getId().equals(tBackup.getId()) ||
                                        !transakcija.getTip().equals(tBackup.getTip()) ||
                                        !transakcija.getDatumVreme().equals(tBackup.getDatumVreme()) ||
                                        transakcija.getIznos() != tBackup.getIznos() ||
                                        transakcija.getRedniBroj() != tBackup.getRedniBroj() ||
                                        !transakcija.getSvrha().equals(tBackup.getSvrha()) ||
                                        !transakcija.getIdRacuna().getId().equals(tBackup.getIdRacuna()) ||
                                        idFil != tBackup.getIdFilijale()){
                                    sb.append("Original -> ").append("id: ").append(tBackup.getId()).append(", tip: ").append(tBackup.getTip()).append(", datum i vreme: ").append(tBackup.getDatumVreme()).append(", iznos: ").append(tBackup.getIznos()).append(", redni broj: ").append(tBackup.getRedniBroj()).append(", svrha: ").append(tBackup.getSvrha()).append(", id racuna: ").append(tBackup.getIdRacuna()).append(", id filijale: ").append(tBackup.getIdFilijale()).append("\n");
                                    sb.append("Kopija   -> ").append("id: ").append(transakcija.getId()).append(", tip: ").append(transakcija.getTip()).append(", datum i vreme: ").append(transakcija.getDatumVreme()).append(", iznos: ").append(transakcija.getIznos()).append(", redni broj: ").append(transakcija.getRedniBroj()).append(", svrha: ").append(transakcija.getSvrha()).append(", id racuna: ").append(transakcija.getIdRacuna().getId()).append(", id filijale: ").append(idFil).append("\n");
                                }
                            }
                        }
                        
                        sb.append("\n");
                        
                        System.out.println(sb.toString());
                        TextMessage txtMsg = context.createTextMessage(sb.toString());
                        producer.send(queueResponse, txtMsg);
                    }
                } catch (JMSException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
                    
            }}
    }
    
}
