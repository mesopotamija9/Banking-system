/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endpoints;

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
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import porukeObj.DohvatiSveRacuneZaKomitenta;
import porukeObj.DohvatiSveTransakcijeZaRacun;
import porukeObj.IsplataSaRacuna;
import porukeObj.OtvaranjeRacuna;
import porukeObj.PrenosNovca;
import porukeObj.UplataNaRacun;
import porukeObj.ZatvaranjeRacuna;

/**
 *
 * @author Milos
 */

@Path("racun")
public class Racun {
    @Resource(lookup = "qp2")
    public Queue queue;
    
    @Resource(lookup = "qps22")
    public Queue queueResponse;
    
    @Resource(lookup = "jms/__defaultConnectionFactory")
    public ConnectionFactory connectionFactory;
    
    @POST
    @Path("otvoriRacun")
    public Response otvoriRacun(@QueryParam("id") int id, 
            @QueryParam("postanskiBroj") String postanskiBroj, 
            @QueryParam("dozvoljeniMinus") double dozvoljeniMinus){
        
        JMSContext context = connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        
        OtvaranjeRacuna otvaranjeRacuna = new OtvaranjeRacuna(id, postanskiBroj, dozvoljeniMinus);
        ObjectMessage objMsg = context.createObjectMessage(otvaranjeRacuna);
        producer.send(queue, objMsg);
        
        JMSConsumer consumer = context.createConsumer(queueResponse);
        
        Message msg = consumer.receive();
        if (msg instanceof TextMessage){
            try {
                return Response.ok().entity(((TextMessage) msg).getText()).build();
            } catch (JMSException ex) {
                Logger.getLogger(Racun.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return Response.ok().entity("OK").build();
        
//        return Response.ok().entity("Request: [id: " + id + ", postanskiBroj: " + postanskiBroj + ", dozvoljeniMinus: " + dozvoljeniMinus + "]").build();
    }
    
//    @GET
//    @Path("otvoriRacunOdgovor")
//    public Response otvoriRacunOdgovor(){
//        JMSContext context = connectionFactory.createContext();
//        JMSConsumer consumer = context.createConsumer(queueResponse);
//        
//        Message msg = consumer.receive();
//        if (msg instanceof TextMessage){
//            try {
//                return Response.ok().entity(((TextMessage) msg).getText()).build();
//            } catch (JMSException ex) {
//                Logger.getLogger(Racun.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        
//        return Response.ok().entity("OK").build();
//    }
    
    @POST
    @Path("zatvoriRacun")
    public Response zatvoriRacun(@QueryParam("id") int id){
        JMSContext context = connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        
        ZatvaranjeRacuna zatvaranjeRacuna = new ZatvaranjeRacuna(id);
        ObjectMessage objMsg = context.createObjectMessage(zatvaranjeRacuna);
        producer.send(queue, objMsg);
        
        JMSConsumer consumer = context.createConsumer(queueResponse);
        
        Message msg = consumer.receive();
        if (msg instanceof TextMessage){
            try {
                return Response.ok().entity(((TextMessage) msg).getText()).build();
            } catch (JMSException ex) {
                Logger.getLogger(Racun.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return Response.ok().entity("OK").build();
        
//        return Response.ok().entity("Request: [id: " + id + "]").build();
    }
    
//    @GET
//    @Path("zatvoriRacunOdgovor")
//    public Response zatvoriRacunOdgovor(){
//        JMSContext context = connectionFactory.createContext();
//        JMSConsumer consumer = context.createConsumer(queueResponse);
//        
//        Message msg = consumer.receive();
//        if (msg instanceof TextMessage){
//            try {
//                return Response.ok().entity(((TextMessage) msg).getText()).build();
//            } catch (JMSException ex) {
//                Logger.getLogger(Racun.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        
//        return Response.ok().entity("OK").build();
//    }
    
    @POST
    @Path("uplatiNaRacun")
    public Response uplatiNaRacun(@QueryParam("id") int id,
            @QueryParam("suma") double suma,
            @QueryParam("idF") int idF,
            @QueryParam("svrha") String svrha){
        
        JMSContext context = connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        
        UplataNaRacun uplataNaRacun = new UplataNaRacun(id, suma, idF, svrha);
        ObjectMessage objMsg = context.createObjectMessage(uplataNaRacun);
        producer.send(queue, objMsg);
        
        JMSConsumer consumer = context.createConsumer(queueResponse);
        
        Message msg = consumer.receive();
        if (msg instanceof TextMessage){
            try {
                return Response.ok().entity(((TextMessage) msg).getText()).build();
            } catch (JMSException ex) {
                Logger.getLogger(Racun.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return Response.ok().entity("OK").build();
        
//        return Response.ok().entity("Request: [id: " + id + ", suma: " + suma + ", idF: " + idF + ", svrha: " + svrha + "]").build();
    }
    
//    @GET
//    @Path("uplatiNaRacunOdgovor")
//    public Response uplatiNaRacunOdgovor(){
//        JMSContext context = connectionFactory.createContext();
//        JMSConsumer consumer = context.createConsumer(queueResponse);
//        
//        Message msg = consumer.receive();
//        if (msg instanceof TextMessage){
//            try {
//                return Response.ok().entity(((TextMessage) msg).getText()).build();
//            } catch (JMSException ex) {
//                Logger.getLogger(Racun.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        
//        return Response.ok().entity("OK").build();
//    }
    
    @POST
    @Path("isplatiSaRacuna")
    public Response isplatiSaRacuna(@QueryParam("id") int id,
            @QueryParam("suma") double suma,
            @QueryParam("idF") int idF,
            @QueryParam("svrha") String svrha){
        
        JMSContext context = connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        
        IsplataSaRacuna isplataSaRacuna = new IsplataSaRacuna(id, suma, idF, svrha);
        ObjectMessage objMsg = context.createObjectMessage(isplataSaRacuna);
        producer.send(queue, objMsg);
        
        JMSConsumer consumer = context.createConsumer(queueResponse);
        
        Message msg = consumer.receive();
        if (msg instanceof TextMessage){
            try {
                return Response.ok().entity(((TextMessage) msg).getText()).build();
            } catch (JMSException ex) {
                Logger.getLogger(Racun.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return Response.ok().entity("OK").build();
        
//        return Response.ok().entity("Request: [id: " + id + ", suma: " + suma + ", idF: " + idF + ", svrha: " + svrha + "]").build();
    }
    
//    @GET
//    @Path("isplatiSaRacunaOdgovor")
//    public Response isplatiSaRacunaOdgovor(){
//        JMSContext context = connectionFactory.createContext();
//        JMSConsumer consumer = context.createConsumer(queueResponse);
//        
//        Message msg = consumer.receive();
//        if (msg instanceof TextMessage){
//            try {
//                return Response.ok().entity(((TextMessage) msg).getText()).build();
//            } catch (JMSException ex) {
//                Logger.getLogger(Racun.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        
//        return Response.ok().entity("OK").build();
//    }
    
    @POST
    @Path("prenesiNovac")
    public Response prenesiNovac(@QueryParam("idSaKogSePrenosi") int idSaKogSePrenosi,
            @QueryParam("idNaKojiSePrenosi") int idNaKojiSePrenosi,
            @QueryParam("sumaPrenosa") double sumaPrenosa,
            @QueryParam("svrha") String svrha){
        
        JMSContext context = connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        
        PrenosNovca prenosNovca = new PrenosNovca(idSaKogSePrenosi, idNaKojiSePrenosi, sumaPrenosa, svrha);
        ObjectMessage objMsg = context.createObjectMessage(prenosNovca);
        producer.send(queue, objMsg);
        
        JMSConsumer consumer = context.createConsumer(queueResponse);
        
        Message msg = consumer.receive();
        if (msg instanceof TextMessage){
            try {
                return Response.ok().entity(((TextMessage) msg).getText()).build();
            } catch (JMSException ex) {
                Logger.getLogger(Racun.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return Response.ok().entity("OK").build();
        
//        return Response.ok().entity("Request: [idSaKogSePrenosi: " + idSaKogSePrenosi + ", idNaKojiSePrenosi: " + idNaKojiSePrenosi + ", sumaPrenosa: " + sumaPrenosa + ", svrha: " + svrha + "]").build();
    }
    
//    @GET
//    @Path("prenesiNovacOdgovor")
//    public Response prenesiNovacOdgovor(){
//        JMSContext context = connectionFactory.createContext();
//        JMSConsumer consumer = context.createConsumer(queueResponse);
//        
//        Message msg = consumer.receive();
//        if (msg instanceof TextMessage){
//            try {
//                return Response.ok().entity(((TextMessage) msg).getText()).build();
//            } catch (JMSException ex) {
//                Logger.getLogger(Racun.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        
//        return Response.ok().entity("OK").build();
//    }
    
    @GET
    @Path("dohvatiSveRacuneZaKomitenta")
    public Response dohvatiSveRacuneZaKomitenta(@QueryParam("id") int id){
        JMSContext context = connectionFactory.createContext();
        JMSConsumer consumer = context.createConsumer(queueResponse);
        JMSProducer producer = context.createProducer();
        
        DohvatiSveRacuneZaKomitenta dohvatiSveRacuneZaKomitenta = new DohvatiSveRacuneZaKomitenta(id);
        ObjectMessage objMsg = context.createObjectMessage(dohvatiSveRacuneZaKomitenta);
        producer.send(queue, objMsg);
        
        Message msg = consumer.receive();
        if (msg instanceof TextMessage){
            try {
                return Response.ok().entity(((TextMessage) msg).getText()).build();
            } catch (JMSException ex) {
                Logger.getLogger(Racun.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return Response.ok().entity("OK").build();
        
//        return Response.ok().entity("Request: [id: " + id + "]").build();
    }
    
//    @GET
//    @Path("dohvatiSveRacuneZaKomitentaOdgovor")
//    public Response dohvatiSveRacuneZaKomitentaOdgovor(){
//        JMSContext context = connectionFactory.createContext();
//        JMSConsumer consumer = context.createConsumer(queueResponse);
//        
//        Message msg = consumer.receive();
//        if (msg instanceof TextMessage){
//            try {
//                return Response.ok().entity(((TextMessage) msg).getText()).build();
//            } catch (JMSException ex) {
//                Logger.getLogger(Racun.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        
//        return Response.ok().entity("OK").build();
//    }
    
    @GET
    @Path("dohvatiSveTransakcijeZaRacun")
    public Response dohvatiSveTransakcijeZaRacun(@QueryParam("id") int id){
        JMSContext context = connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        
        DohvatiSveTransakcijeZaRacun dohvatiSveTransakcijeZaRacun = new DohvatiSveTransakcijeZaRacun(id);
        ObjectMessage objMsg = context.createObjectMessage(dohvatiSveTransakcijeZaRacun);
        producer.send(queue, objMsg);
        
        JMSConsumer consumer = context.createConsumer(queueResponse);
        
        Message msg = consumer.receive();
        if (msg instanceof TextMessage){
            try {
                return Response.ok().entity(((TextMessage) msg).getText()).build();
            } catch (JMSException ex) {
                Logger.getLogger(Racun.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return Response.ok().entity("OK").build();
        
//        return Response.ok().entity("Request: [id: " + id + "]").build();
    }
    
//    @GET
//    @Path("dohvatiSveTransakcijeZaRacunOdgovor")
//    public Response dohvatiSveTransakcijeZaRacunOdgovor(){
//        JMSContext context = connectionFactory.createContext();
//        JMSConsumer consumer = context.createConsumer(queueResponse);
//        
//        Message msg = consumer.receive();
//        if (msg instanceof TextMessage){
//            try {
//                return Response.ok().entity(((TextMessage) msg).getText()).build();
//            } catch (JMSException ex) {
//                Logger.getLogger(Racun.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        
//        return Response.ok().entity("OK").build();
//    }
}
