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
import porukeObj.DohvatiSveFilijale;
import porukeObj.KreiranjeFilijale;

/**
 *
 * @author Milos
 */

@Path("filijala")
public class Filijala {
    @Resource(lookup = "q188887")
    public Queue queue;
    
    @Resource(lookup = "qs88887")
    public Queue queueResponse;
    
    @Resource(lookup = "jms/__defaultConnectionFactory")
    public ConnectionFactory connectionFactory;
    
    @POST
    @Path("kreirajFilijalu")
    public Response kreirajFilijalu(@QueryParam("naziv") String naziv, 
            @QueryParam("adresa") String adresa, 
            @QueryParam("postanskiBroj") String postanskiBroj){
        
        JMSContext context = connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        
        KreiranjeFilijale kreiranjeFilijale = new KreiranjeFilijale(naziv, adresa, postanskiBroj);
        ObjectMessage objMsg = context.createObjectMessage(kreiranjeFilijale);
        producer.send(queue, objMsg);
        
        JMSConsumer consumer = context.createConsumer(queueResponse);
        
        Message msg = consumer.receive();
        if (msg instanceof TextMessage){
            try {
                return Response.ok().entity(((TextMessage) msg).getText()).build();
            } catch (JMSException ex) {
                Logger.getLogger(Filijala.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return Response.ok().entity("OK").build();
        
//        return Response.ok().entity("Request: [naziv: " + naziv + ", adresa: " + adresa + ", postanskiBroj: " + postanskiBroj + "]").build();
    }
    
//    @GET
//    @Path("kreirajFilijaluOdgovor")
//    public Response kreirajFilijaluOdgovor(){
//        JMSContext context = connectionFactory.createContext();
//        JMSConsumer consumer = context.createConsumer(queueResponse);
//        
//        Message msg = consumer.receive();
//        if (msg instanceof TextMessage){
//            try {
//                return Response.ok().entity(((TextMessage) msg).getText()).build();
//            } catch (JMSException ex) {
//                Logger.getLogger(Filijala.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        
//        return Response.ok().entity("OK").build();
//    }
    
    @GET
    @Path("dohvatiSveFilijale")
    public Response dohvatiSveFilijale(){
        JMSContext context = connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        
        DohvatiSveFilijale dohvatiSveFilijale= new DohvatiSveFilijale();
        ObjectMessage objMsg = context.createObjectMessage(dohvatiSveFilijale);
        producer.send(queue, objMsg);
        
        JMSConsumer consumer = context.createConsumer(queueResponse);
        
        Message msg = consumer.receive();
        if (msg instanceof TextMessage){
            try {
                return Response.ok().entity(((TextMessage) msg).getText()).build();
            } catch (JMSException ex) {
                Logger.getLogger(Filijala.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return Response.ok().entity("OK").build();
        
//        return Response.ok().entity("Request: Dohvati sve filijale").build();
    }
    
//    @GET
//    @Path("dohvatiSveFilijaleOdgovor")
//     public Response dohvatiSveFilijaleOdgovor(){
//        JMSContext context = connectionFactory.createContext();
//        JMSConsumer consumer = context.createConsumer(queueResponse);
//        
//        Message msg = consumer.receive();
//        if (msg instanceof TextMessage){
//            try {
//                return Response.ok().entity(((TextMessage) msg).getText()).build();
//            } catch (JMSException ex) {
//                Logger.getLogger(Filijala.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        
//        return Response.ok().entity("OK").build();
//     }
}
