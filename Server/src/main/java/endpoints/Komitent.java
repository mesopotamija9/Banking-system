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
import porukeObj.DohvatiSveKomitente;
import porukeObj.KreiranjeKomitenta;
import porukeObj.PromeniSedisteKomitenta;

/**
 *
 * @author Milos
 */

@Path("komitent")
public class Komitent {
    @Resource(lookup = "q188887")
    public Queue queue;
    
    @Resource(lookup = "qs88887")
    public Queue queueResponse;
    
    @Resource(lookup = "jms/__defaultConnectionFactory")
    public ConnectionFactory connectionFactory;
    
    @POST
    @Path("kreirajKomitenta")
    public Response kreirajKomitenta(@QueryParam("naziv") String naziv, 
            @QueryParam("adresa") String adresa, 
            @QueryParam("postanskiBroj") String postanskiBroj){
        
        JMSContext context = connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        
        KreiranjeKomitenta kreiranjeKomitenta = new KreiranjeKomitenta(naziv, adresa, postanskiBroj);
        ObjectMessage objMsg = context.createObjectMessage(kreiranjeKomitenta);
        producer.send(queue, objMsg);
        
        JMSConsumer consumer = context.createConsumer(queueResponse);
        
        Message msg = consumer.receive();
        if (msg instanceof TextMessage){
            try {
                return Response.ok().entity(((TextMessage) msg).getText()).build();
            } catch (JMSException ex) {
                Logger.getLogger(Komitent.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return Response.ok().entity("OK").build();
        
//        return Response.ok().entity("Request: [naziv: " + naziv + ", adresa: " + adresa + ", postanskiBroj: " + postanskiBroj + "]").build();
    }
    
//    @GET
//    @Path("kreirajKomitentaOdgovor")
//    public Response kreirajKomitentaOdgovor(){
//        JMSContext context = connectionFactory.createContext();
//        JMSConsumer consumer = context.createConsumer(queueResponse);
//        
//        Message msg = consumer.receive();
//        if (msg instanceof TextMessage){
//            try {
//                return Response.ok().entity(((TextMessage) msg).getText()).build();
//            } catch (JMSException ex) {
//                Logger.getLogger(Komitent.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        
//        return Response.ok().entity("OK").build();
//    }
    
    @POST
    @Path("promeniSediste")
    public Response promeniSediste(@QueryParam("id") int id, 
            @QueryParam("postanskiBroj") String postanskiBroj){
        
        JMSContext context = connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        
        PromeniSedisteKomitenta promeniSedisteKomitenta = new PromeniSedisteKomitenta(id, postanskiBroj);
        ObjectMessage objMsg = context.createObjectMessage(promeniSedisteKomitenta);
        producer.send(queue, objMsg);
        
        JMSConsumer consumer = context.createConsumer(queueResponse);
        
        Message msg = consumer.receive();
        if (msg instanceof TextMessage){
            try {
                return Response.ok().entity(((TextMessage) msg).getText()).build();
            } catch (JMSException ex) {
                Logger.getLogger(Komitent.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return Response.ok().entity("OK").build();
        
//        return Response.ok().entity("Request: [id: " + id + ", postanskiBroj: " + postanskiBroj + "]").build();
    }
    
//    @GET
//    @Path("promeniSedisteOdgovor")
//    public Response promeniSedisteOdgovor(){
//        JMSContext context = connectionFactory.createContext();
//        JMSConsumer consumer = context.createConsumer(queueResponse);
//        
//        Message msg = consumer.receive();
//        if (msg instanceof TextMessage){
//            try {
//                return Response.ok().entity(((TextMessage) msg).getText()).build();
//            } catch (JMSException ex) {
//                Logger.getLogger(Komitent.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        
//        return Response.ok().entity("OK").build();
//    }
    
    @GET
    @Path("dohvatiSveKomitente")
    public Response dohvatiSveKomitente(){
        JMSContext context = connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        
        DohvatiSveKomitente dohvatiSveKomitente = new DohvatiSveKomitente();
        ObjectMessage objMsg = context.createObjectMessage(dohvatiSveKomitente);
        producer.send(queue, objMsg);
        
        JMSConsumer consumer = context.createConsumer(queueResponse);
        
        Message msg = consumer.receive();
        if (msg instanceof TextMessage){
            try {
                return Response.ok().entity(((TextMessage) msg).getText()).build();
            } catch (JMSException ex) {
                Logger.getLogger(Komitent.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return Response.ok().entity("OK").build();
        
//        return Response.ok().entity("Request: Dohvati sve komitente").build();
    }
    
//    @GET
//    @Path("dohvatiSveKomitenteOdgovor")
//    public Response dohvatiSveKomitenteOdgovor(){
//        JMSContext context = connectionFactory.createContext();
//        JMSConsumer consumer = context.createConsumer(queueResponse);
//        
//        Message msg = consumer.receive();
//        if (msg instanceof TextMessage){
//            try {
//                return Response.ok().entity(((TextMessage) msg).getText()).build();
//            } catch (JMSException ex) {
//                Logger.getLogger(Komitent.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        
//        return Response.ok().entity("OK").build();
//    }
}
