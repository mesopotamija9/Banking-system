/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package endpoints;

import java.util.Date;
import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.TimerService;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.ws.rs.Path;
import porukeObj.RadiTimerBackup;

/**
 *
 * @author Milos
 */
@Path("timer")
@Stateless
public class Timer {
    @Resource
    private TimerService timerService;
    
    @Resource(lookup = "qp3")
    public Queue queue;
    
    @Resource(lookup = "qps3333333")
    public Queue queueResponse;
    
    @Resource(lookup = "jms/__defaultConnectionFactory")
    public ConnectionFactory connectionFactory;
    
    @Schedule(hour = "*", minute = "*/2", persistent = false)
    public void timer(){
        System.out.println("Automatski backup: " + new Date());
        
        JMSContext context = connectionFactory.createContext();
        JMSProducer producer = context.createProducer();

        RadiTimerBackup radiTimerBackup = new RadiTimerBackup();
        ObjectMessage objMsg = context.createObjectMessage(radiTimerBackup);
//        producer.send(queue, objMsg);
        
        System.out.println("");
    }
}
