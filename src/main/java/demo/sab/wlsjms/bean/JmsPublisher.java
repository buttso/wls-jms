/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demo.sab.wlsjms.bean;

import demo.sab.wlsjms.resource.OrderMessageProducer;
import demo.sab.wlsjms.resource.OrderSession;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 *
 * @author sbutton
 */
@RequestScoped
public class JmsPublisher {
    
    @Inject
    @OrderMessageProducer
    MessageProducer producer;
    
    @Inject
    @OrderSession
    Session orderSession;

    public void publishMessage(String message) throws JMSException {
            TextMessage msg = orderSession.createTextMessage();
            msg.setText(message);
            producer.send(msg);
    }
    
    
}
