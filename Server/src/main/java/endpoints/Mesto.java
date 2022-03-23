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
import porukeObj.DohvatiSvaMesta;
import porukeObj.KreiranjeMesta;

/**
 *
 * @author Milos
 */

@Path("mesto")
public class Mesto {
    
    @Resource(lookup = "q188887")
    public Queue queue;
    
    @Resource(lookup = "qs88887")
    public Queue queueResponse;
    
    @Resource(lookup = "jms/__defaultConnectionFactory")
    public ConnectionFactory connectionFactory;
    
    @POST
    @Path("kreirajMesto")
    public Response kreirajMesto(@QueryParam("postanskiBroj") String postanskiBroj, 
            @QueryParam("naziv") String naziv){
        
        JMSContext context = connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        
        KreiranjeMesta kreiranjeMesta = new KreiranjeMesta(postanskiBroj, naziv);
        ObjectMessage objMsg = context.createObjectMessage(kreiranjeMesta);
        producer.send(queue, objMsg);
        
        JMSConsumer consumer = context.createConsumer(queueResponse);
        
        Message msg = consumer.receive();
        if (msg instanceof TextMessage){
            try {
                return Response.ok().entity(((TextMessage) msg).getText()).build();
            } catch (JMSException ex) {
                Logger.getLogger(Mesto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return Response.ok().entity("OK").build();
        
//        return Response.ok().entity("Request: [postanskiBroj: " + postanskiBroj + ", naziv: " + naziv + "]").build();
    }
    
//    @GET
//    @Path("kreirajMestoOdgovor")
//    public Response kreirajMestoOdgovor(){
//        JMSContext context = connectionFactory.createContext();
//        JMSConsumer consumer = context.createConsumer(queueResponse);
//        
//        Message msg = consumer.receive();
//        if (msg instanceof TextMessage){
//            try {
//                return Response.ok().entity(((TextMessage) msg).getText()).build();
//            } catch (JMSException ex) {
//                Logger.getLogger(Mesto.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        
//        return Response.ok().entity("OK").build();
//    }
    
    @GET
    @Path("dohvatiSvaMesta")
    public Response dohvatiSvaMesta(){
        JMSContext context = connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        
        DohvatiSvaMesta dohvatiSvaMesta = new DohvatiSvaMesta();
        ObjectMessage objMsg = context.createObjectMessage(dohvatiSvaMesta);
        producer.send(queue, objMsg);
        
        JMSConsumer consumer = context.createConsumer(queueResponse);
        
        Message msg = consumer.receive();
        if (msg instanceof TextMessage){
            try {
                return Response.ok().entity(((TextMessage) msg).getText()).build();
            } catch (JMSException ex) {
                Logger.getLogger(Mesto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return Response.ok().entity("OK").build();
        
//        return Response.ok().entity("Request: Dohvati sva mesta").build();
    }
    
//    @GET
//    @Path("dohvatiSvaMestaOdgovor")
//    public Response dohvatiSvaMestaOdgovor(){
//        JMSContext context = connectionFactory.createContext();
//        JMSConsumer consumer = context.createConsumer(queueResponse);
//        
//        Message msg = consumer.receive();
//        if (msg instanceof TextMessage){
//            try {
//                return Response.ok().entity(((TextMessage) msg).getText()).build();
//            } catch (JMSException ex) {
//                Logger.getLogger(Mesto.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        
//        return Response.ok().entity("OK").build();
//    }
}
