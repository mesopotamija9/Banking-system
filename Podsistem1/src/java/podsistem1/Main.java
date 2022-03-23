/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podsistem1;

import entities.Filijala;
import entities.Komitent;
import entities.Mesto;
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
import porukeObj.DohvatiRazlikuPodataka;
import porukeObj.DohvatiSvaMesta;
import porukeObj.DohvatiSveFilijale;
import porukeObj.DohvatiSveKomitente;
import porukeObj.FilijalaBackup;
import porukeObj.KomitentBackup;
import porukeObj.KomitentP1P2;
import porukeObj.KreiranjeFilijale;
import porukeObj.KreiranjeKomitenta;
import porukeObj.KreiranjeMesta;
import porukeObj.MestoBackup;
import porukeObj.PostojiMesto;
import porukeObj.PromeniSedisteKomitenta;

/**
 *
 * @author Milos
 */
public class Main {
    
    @Resource(lookup = "q188887")
    private static Queue queue;
    
    @Resource(lookup = "qs88887")
    public static Queue queueResponse;
    
    @Resource(lookup = "qp1p22")
    public static Queue queueP1P2;
    
    @Resource(lookup = "postojiMesto")
    private static Queue queuePostojiMesto;
    
    @Resource(lookup = "queueP3P1")
    public static Queue queueP3P1;
    
    @Resource(lookup = "queueP1P33")
    public static Queue queueP1P3;
    
    @Resource(lookup = "jms/__defaultConnectionFactory")
    private static ConnectionFactory connectionFactory;            
    
    public static void main(String[] args) {
        JMSContext context = connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        JMSConsumer consumer = context.createConsumer(queue);
        JMSConsumer consumerPostojiMesto = context.createConsumer(queuePostojiMesto);
        JMSConsumer consumerP3P1 = context.createConsumer(queueP3P1);
        System.out.println("Podsistem 1 je pokrenut");
        
        consumerP3P1.setMessageListener((Message m) -> {
            if (!(m instanceof ObjectMessage)) {
                System.out.println("Poruka odbacena.");
            } else {
                try {
                    ObjectMessage objMsg= (ObjectMessage)m;
                    Serializable obj = objMsg.getObject();
                    
                    if (obj instanceof MestoBackup){
                        System.out.println("Podsistem 3 trazi backup mesta");
                        
                        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem1PU");
                        EntityManager em = emf.createEntityManager();
                        
                        List<Mesto> mesta = em.createNamedQuery("Mesto.findAll", Mesto.class).getResultList();
                        ArrayList<MestoBackup> mestaZaSlanje = new ArrayList<>();
                        
                        for (Mesto me: mesta){
                            MestoBackup mestoBackup = new MestoBackup(me.getPostanskiBroj(), me.getNaziv());
                            mestaZaSlanje.add(mestoBackup);
                        }
                        
                        ObjectMessage mestoBackupObj = context.createObjectMessage(mestaZaSlanje);
                        
                        producer.send(queueP1P3, mestoBackupObj);
                        System.out.println("Mesta poslata podsistemu 3");
                        
                        em.clear();
                        emf.close();
                    } else if (obj instanceof KomitentBackup){
                        System.out.println("Podsistem 3 trazi backup komitenata");
                        
                        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem1PU");
                        EntityManager em = emf.createEntityManager();
                        
                        List<Komitent> komitenti = em.createNamedQuery("Komitent.findAll", Komitent.class).getResultList();
                        ArrayList<KomitentBackup> komitentiZaSlanje = new ArrayList<>();
                        
                        for (Komitent ko: komitenti){
                            KomitentBackup komitentBackup = new KomitentBackup(ko.getId(), ko.getNaziv(), ko.getAdresa(), ko.getIdMesta().getPostanskiBroj());
                            komitentiZaSlanje.add(komitentBackup);
                        }
                        
                        ObjectMessage komitentiBackupObj = context.createObjectMessage(komitentiZaSlanje);
                        
                        producer.send(queueP1P3, komitentiBackupObj);
                        System.out.println("Komitenti poslati podsistemu 3");
                        
                        em.clear();
                        emf.close();
                    } else if (obj instanceof FilijalaBackup){
                        System.out.println("Podsistem 3 trazi backup filijala");
                        
                        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem1PU");
                        EntityManager em = emf.createEntityManager();
                        
                        List<Filijala> filijale = em.createNamedQuery("Filijala.findAll", Filijala.class).getResultList();
                        ArrayList<FilijalaBackup> filijaleZaSlanje = new ArrayList<>();
                        
                        for (Filijala fi: filijale){
                            FilijalaBackup filijalaBackup = new FilijalaBackup(fi.getId(), fi.getNaziv(), fi.getAdresa(), fi.getIdMesta().getPostanskiBroj());
                            filijaleZaSlanje.add(filijalaBackup);
                        }
                        
                        ObjectMessage filijaleBackupObj = context.createObjectMessage(filijaleZaSlanje);
                        
                        producer.send(queueP1P3, filijaleBackupObj);
                        System.out.println("Filijale poslate podsistemu 3");
                        
                        em.clear();
                        emf.close();
                    } else if (obj instanceof DohvatiRazlikuPodataka){
                        System.out.println("Dohvatanje razlike podataka");
                        
                        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem1PU");
                        EntityManager em = emf.createEntityManager();
                        
                        List<Mesto> mesta = em.createNamedQuery("Mesto.findAll", Mesto.class).getResultList();
                        ArrayList<MestoBackup> mestaZaSlanje = new ArrayList<>();
                        
                        for (Mesto me: mesta){
                            MestoBackup mestoBackup = new MestoBackup(me.getPostanskiBroj(), me.getNaziv());
                            mestaZaSlanje.add(mestoBackup);
                        }
                        
                        ObjectMessage mestoBackupObj = context.createObjectMessage(mestaZaSlanje);
                        
                        producer.send(queueP1P3, mestoBackupObj);
                        System.out.println("Mesta poslata podsistemu 3");
                        
                        List<Komitent> komitenti = em.createNamedQuery("Komitent.findAll", Komitent.class).getResultList();
                        ArrayList<KomitentBackup> komitentiZaSlanje = new ArrayList<>();
                        
                        for (Komitent ko: komitenti){
                            KomitentBackup komitentBackup = new KomitentBackup(ko.getId(), ko.getNaziv(), ko.getAdresa(), ko.getIdMesta().getPostanskiBroj());
                            komitentiZaSlanje.add(komitentBackup);
                        }
                        
                        ObjectMessage komitentiBackupObj = context.createObjectMessage(komitentiZaSlanje);
                        
                        producer.send(queueP1P3, komitentiBackupObj);
                        System.out.println("Komitenti poslati podsistemu 3");
                        
                        List<Filijala> filijale = em.createNamedQuery("Filijala.findAll", Filijala.class).getResultList();
                        ArrayList<FilijalaBackup> filijaleZaSlanje = new ArrayList<>();
                        
                        for (Filijala fi: filijale){
                            FilijalaBackup filijalaBackup = new FilijalaBackup(fi.getId(), fi.getNaziv(), fi.getAdresa(), fi.getIdMesta().getPostanskiBroj());
                            filijaleZaSlanje.add(filijalaBackup);
                        }
                        
                        ObjectMessage filijaleBackupObj = context.createObjectMessage(filijaleZaSlanje);
                        
                        producer.send(queueP1P3, filijaleBackupObj);
                        System.out.println("Filijale poslate podsistemu 3");
                        
                        em.clear();
                        emf.close();
                    }
                } catch (JMSException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        consumerPostojiMesto.setMessageListener((Message m) -> {
            if (!(m instanceof ObjectMessage)) {
                System.out.println("Poruka odbacena.");
            } else {
                try {
                    ObjectMessage objMsg= (ObjectMessage)m;
                    Serializable obj = objMsg.getObject();
                    
                    if (obj instanceof PostojiMesto){
                        PostojiMesto postojiMesto = (PostojiMesto) obj;
                        String psotanskiBroj = postojiMesto.getPostanskiBroj();
                        
                        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem1PU");
                        EntityManager em = emf.createEntityManager();
                        
                        Mesto mesto = em.find(Mesto.class, psotanskiBroj);
                        
                        if (mesto != null){
                            postojiMesto.setPostoji(true);
                        }
                        
                        ObjectMessage postojiMestoObj = context.createObjectMessage(postojiMesto);
                        producer.send(queueP1P2, postojiMestoObj);
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
                    if (obj instanceof KreiranjeMesta){
                        try {
                            KreiranjeMesta kreiranjeMesta=(KreiranjeMesta)objMsg.getObject();
                            
                            String postanskiBroj = kreiranjeMesta.getPostanskiBroj();
                            String naziv = kreiranjeMesta.getNaziv();
                            
                            System.out.println("Postanski broj: " + postanskiBroj + " Naziv: " + naziv);
                            
                            EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem1PU");
                            
                            EntityManager em = emf.createEntityManager();
                            
                            // Proveri da li vec postoji mesto sa istim postanskim brojem
                            Mesto mesto = em.find(Mesto.class, postanskiBroj);
                            
                            if (mesto != null){
                                StringBuilder sb = new StringBuilder();
                                sb.append("Vec postoji mesto sa postanskim brojem: " + postanskiBroj + "\n")
                                        .append("Kreiranje mesta nije uspelo");
                                System.out.println(sb.toString());
                                TextMessage txtMsg = context.createTextMessage(sb.toString());
                                producer.send(queueResponse, txtMsg);
                                
                                continue;
                            }
                            
                            Mesto novoMesto = new Mesto();
                            novoMesto.setPostanskiBroj(postanskiBroj);
                            novoMesto.setNaziv(naziv);
                            
                            em.getTransaction().begin();
                            em.persist(novoMesto);
                            em.getTransaction().commit();
                            em.clear();
                            
                            emf.close();
                            System.out.println("Kreiranje mesta je uspelo");
                            TextMessage txtMsg = context.createTextMessage("Kreiranje mesta je uspelo");
                            producer.send(queueResponse, txtMsg);
                        } catch (JMSException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else if (obj instanceof KreiranjeFilijale){
                        try {
                            KreiranjeFilijale kreiranjeFilijale = (KreiranjeFilijale)objMsg.getObject();
                            
                            String naziv = kreiranjeFilijale.getNaziv();
                            String adresa = kreiranjeFilijale.getAdresa();
                            String postanskiBroj = kreiranjeFilijale.getPostanskiBroj();
                            
                            System.out.println("Naziv: " + naziv + " Adresa: " + adresa + " Postanski broj: " + postanskiBroj);
                            
                            EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem1PU");
                            EntityManager em = emf.createEntityManager();
                            
                            // Proveri da li vec postoji mesto sa istim postanskim brojem
                            Mesto mesto = em.find(Mesto.class, postanskiBroj);
                            
                            if (mesto == null){
                                StringBuilder sb = new StringBuilder();
                                sb.append("Ne postoji mesto sa postanskim brojem: " + postanskiBroj + "\n")
                                        .append("Kreiranje filijale nije uspelo");
                                System.out.println(sb.toString());
                                TextMessage txtMsg = context.createTextMessage(sb.toString());
                                producer.send(queueResponse, txtMsg);
                                
                                continue;
                            }
                            
                            boolean adresaZauzeta = false;
                            // Proveri da li je adresa zauzeta
                            List<Filijala> filijale = em.createNamedQuery("Filijala.findAll", Filijala.class).getResultList();
                            for (Filijala f: filijale){
                                if (f.getAdresa().equals(adresa) && f.getIdMesta().getPostanskiBroj().equals(postanskiBroj)){
                                    adresaZauzeta = true;
                                    break;
                                }
                            }
                            
                            if (adresaZauzeta){
                                StringBuilder sb = new StringBuilder();
                                sb.append("Adresa: " + adresa + " Postanski broj: " + postanskiBroj + " je zauzeta\n")
                                        .append("Kreiranje filijale nije uspelo");
                                System.out.println(sb.toString());
                                TextMessage txtMsg = context.createTextMessage(sb.toString());
                                producer.send(queueResponse, txtMsg);
                                
                                continue;
                            }
                            
                            Filijala novaFilijala = new Filijala();
                            novaFilijala.setNaziv(naziv);
                            novaFilijala.setAdresa(adresa);
                            novaFilijala.setIdMesta(mesto);
                            
                            em.getTransaction().begin();
                            em.persist(novaFilijala);
                            em.getTransaction().commit();
                            em.clear();
                            
                            emf.close();
                            System.out.println("Kreiranje filijale je uspelo");
                            TextMessage txtMsg = context.createTextMessage("Kreiranje filijale je uspelo");
                            producer.send(queueResponse, txtMsg);
                        } catch (JMSException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else if (obj instanceof KreiranjeKomitenta){
                        KreiranjeKomitenta kreiranjeKomitenta = (KreiranjeKomitenta)objMsg.getObject();
                            
                        String naziv = kreiranjeKomitenta.getNaziv();
                        String adresa = kreiranjeKomitenta.getAdresa();
                        String postanskiBroj = kreiranjeKomitenta.getPostanskiBroj();

                        System.out.println("Naziv: " + naziv + " Adresa: " + adresa + " Postanski broj: " + postanskiBroj);
                    
                        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem1PU");
                        EntityManager em = emf.createEntityManager();
                        
                        // Proveri da li vec postoji mesto sa istim postanskim brojem
                        Mesto mesto = em.find(Mesto.class, postanskiBroj);

                        if (mesto == null){
                            StringBuilder sb = new StringBuilder();
                            sb.append("Ne postoji mesto sa postanskim brojem: " + postanskiBroj + "\n")
                                    .append("Kreiranje komitenta nije uspelo");
                            System.out.println(sb.toString());
                            TextMessage txtMsg = context.createTextMessage(sb.toString());
                            producer.send(queueResponse, txtMsg);

                            continue;
                        }
                        
                        Komitent nnoviKomitent = new Komitent();
                        nnoviKomitent.setNaziv(naziv);
                        nnoviKomitent.setAdresa(adresa);
                        nnoviKomitent.setIdMesta(mesto);

                        em.getTransaction().begin();
                        em.persist(nnoviKomitent);
                        em.getTransaction().commit();
                        em.clear();

                        emf.close();
                        System.out.println("Kreiranje komitenta je uspelo");
                        TextMessage txtMsg = context.createTextMessage("Kreiranje komitenta je uspelo");
                        producer.send(queueResponse, txtMsg);
                        
                        
                        // Posalji novog komitenta podsistemu 2
                        
                        KomitentP1P2 komitentP1P2 = new KomitentP1P2(nnoviKomitent.getId(), nnoviKomitent.getNaziv(), nnoviKomitent.getAdresa(), nnoviKomitent.getIdMesta(), "CREATE");
                        ObjectMessage objMsgP1P2 = context.createObjectMessage(komitentP1P2);
                        producer.send(queueP1P2, objMsgP1P2);
                        
                    } else if (obj instanceof PromeniSedisteKomitenta){
                        PromeniSedisteKomitenta promeniSedisteKomitenta = (PromeniSedisteKomitenta)objMsg.getObject();
                            
                        int id = promeniSedisteKomitenta.getId();
                        String postanskiBroj = promeniSedisteKomitenta.getPostanskiBroj();

                        System.out.println("Id: " + id + " Postanski broj: " + postanskiBroj);
                        
                        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem1PU");
                        EntityManager em = emf.createEntityManager();
                        
                        Komitent komitent = em.find(Komitent.class, id);
                        if (komitent == null){
                            StringBuilder sb = new StringBuilder();
                            sb.append("Ne postoji komitent cije je id: " + id + "\n")
                                    .append("Promena sedista komitenta njie uspela");
                            System.out.println(sb.toString());
                            
                            TextMessage txtMsg = context.createTextMessage(sb.toString());
                            producer.send(queueResponse, txtMsg);
                            
                            continue;
                        }
                        
                        // Proveri da li postoji mesto sa novim postanskim brojem
                        Mesto mesto = em.find(Mesto.class, postanskiBroj);

                        if (mesto == null){
                            StringBuilder sb = new StringBuilder();
                            sb.append("Ne postoji mesto sa postanskim brojem: " + postanskiBroj + "\n")
                                    .append("Promena sedista komitenta njie uspela");
                            System.out.println(sb.toString());
                            
                            TextMessage txtMsg = context.createTextMessage(sb.toString());
                            producer.send(queueResponse, txtMsg);

                            continue;
                        }
                        
                        komitent.setIdMesta(mesto);
                        
                        em.getTransaction().begin();
                        em.persist(komitent);
                        em.getTransaction().commit();
                        em.clear();
                        
                        emf.close();
                        System.out.println("Promena sedista komitenta je uspela");
                        TextMessage txtMsg = context.createTextMessage("Promena sedista komitenta je uspela");
                        producer.send(queueResponse, txtMsg);
                    
                        // Posalji izmenjenog komitenta podsistemu 2
                        
                        KomitentP1P2 komitentP1P2 = new KomitentP1P2(komitent.getId(), komitent.getNaziv(), komitent.getAdresa(), komitent.getIdMesta(), "UPDATE");
                        ObjectMessage objMsgP1P2 = context.createObjectMessage(komitentP1P2);
                        producer.send(queueP1P2, objMsgP1P2);
                        
                    } else if (obj instanceof DohvatiSvaMesta) {
                        System.out.println("Dohvati sva mesta");
                        
                        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem1PU");
                        EntityManager em = emf.createEntityManager();
                        
                        List<Mesto> mesta = em.createNamedQuery("Mesto.findAll", Mesto.class).getResultList();
                        
                        StringBuilder sb = new StringBuilder();
                        
                        for(Mesto mesto: mesta){
                            sb.append("PostanskiBroj: " + mesto.getPostanskiBroj() + ", Naziv: " + mesto.getNaziv() + "\n");
                        }
                        
                        System.out.println(sb.toString());
                        
                        em.clear();
                        emf.close();
                        
                        TextMessage txtMsg = context.createTextMessage(sb.toString());
                        producer.send(queueResponse, txtMsg);
                    } else if (obj instanceof DohvatiSveFilijale) {
                        System.out.println("Dohvati sve filijale");
                        
                        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem1PU");
                        EntityManager em = emf.createEntityManager();
                        
                        List<Filijala> filijale = em.createNamedQuery("Filijala.findAll", Filijala.class).getResultList();
                        
                        StringBuilder sb = new StringBuilder();
                        
                        for(Filijala filijala: filijale){
                            sb.append("Id: " + filijala.getId() + ", Naziv: " + filijala.getNaziv() + ", Adresa: " + filijala.getAdresa() + ", Id mesta: " + filijala.getIdMesta().getPostanskiBroj() + "\n");
                        }
                        
                        System.out.println(sb.toString());
                        
                        em.clear();
                        emf.close();
                        
                        TextMessage txtMsg = context.createTextMessage(sb.toString());
                        producer.send(queueResponse, txtMsg);
                    } else if (obj instanceof DohvatiSveKomitente) {
                        System.out.println("Dohvati sve komitente");
                        
                        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem1PU");
                        EntityManager em = emf.createEntityManager();
                        
                        List<Komitent> komitenti = em.createNamedQuery("Komitent.findAll", Komitent.class).getResultList();
                        
                        StringBuilder sb = new StringBuilder();
                        
                        for(Komitent komitent: komitenti){
                            sb.append("Id: " + komitent.getId() + ", Naziv: " + komitent.getNaziv() + ", Adresa: " + komitent.getAdresa() + ", Id mesta: " + komitent.getIdMesta().getPostanskiBroj() + "\n");
                        }
                        
                        System.out.println(sb.toString());
                        
                        em.clear();
                        emf.close();
                        
                        TextMessage txtMsg = context.createTextMessage(sb.toString());
                        producer.send(queueResponse, txtMsg);
                    }
                } catch (JMSException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
             
        }
    }
    
}
