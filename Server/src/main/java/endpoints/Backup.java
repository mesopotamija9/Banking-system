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
import javax.ws.rs.core.Response;
import porukeObj.DohvatiBackup;
import porukeObj.DohvatiRazlikuPodataka;
import porukeObj.RadiBackup;

/**
 *
 * @author Milos
 */
//@Stateless
@Path("backup")
public class Backup {
    
    @Resource(lookup = "qp3")
    public Queue queue;
    
    @Resource(lookup = "qps3333333")
    public Queue queueResponse;
    
    @Resource(lookup = "jms/__defaultConnectionFactory")
    public ConnectionFactory connectionFactory;
    
    @POST
    @Path("backup")
    public Response backup(){
        JMSContext context = connectionFactory.createContext();
        JMSProducer producer = context.createProducer();

        RadiBackup radiBackup = new RadiBackup();
        ObjectMessage objMsg = context.createObjectMessage(radiBackup);
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
    }
    
    @GET
    @Path("dohvatiSvePodatkeIzRezervneKopije")
    public Response dohvatiSvePodatkeIzRezervneKopije(){
        JMSContext context = connectionFactory.createContext();
        JMSProducer producer = context.createProducer();

        DohvatiBackup dohvatiBackup = new DohvatiBackup();
        ObjectMessage objMsg = context.createObjectMessage(dohvatiBackup);
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
    }
    
    @GET
    @Path("dohvatiRazlikuUPodacima")
    public Response dohvatiRazlikuUPodacima(){
        JMSContext context = connectionFactory.createContext();
        JMSProducer producer = context.createProducer();

        DohvatiRazlikuPodataka dohvatiRazlikuPodataka = new DohvatiRazlikuPodataka();
        ObjectMessage objMsg = context.createObjectMessage(dohvatiRazlikuPodataka);
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
    }
}
