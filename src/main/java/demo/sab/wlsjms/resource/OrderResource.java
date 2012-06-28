/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demo.sab.wlsjms.resource;

import demo.sab.wlsjms.interceptor.TraceMethod;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.New;
import javax.enterprise.inject.Produces;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 *
 * @author sbutton
 */
@TraceMethod
//@ApplicationScoped
@RequestScoped
public class OrderResource {
    
    @Resource(mappedName = "jms/cf", type=javax.jms.ConnectionFactory.class)
    ConnectionFactory connectionFactory;

    @Resource(mappedName="jms/q", type=javax.jms.Queue.class)            
    Destination orderQueue;
    
    @Produces
    @OrderConnection
    public Connection createOrderConnection() throws JMSException {       
        return connectionFactory.createConnection();
    }

    public void closeOrderConnection(@Disposes @OrderConnection Connection connection) throws JMSException {
        connection.close();
    }

    @Produces
    @OrderSession
    public Session createOrderSession(@OrderConnection Connection connection) throws JMSException {
        return connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }

    public void closeOrderSession(@Disposes @OrderSession Session session) throws JMSException {
        session.close();
    }

    @Produces
    @OrderMessageProducer
    public MessageProducer createOrderMessageProducer(@OrderSession Session session) throws JMSException {
        return session.createProducer(orderQueue);
    }

    public void closeOrderMessageProducer(@Disposes @OrderMessageProducer MessageProducer producer) throws JMSException {
        producer.close();
    }
        
}
