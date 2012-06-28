/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demo.sab.wlsjms.consumer;

import demo.sab.wlsjms.interceptor.TraceMethod;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 *
 * @author sbutton
 */
@MessageDriven(mappedName = "jms/q", name = "ListenerMDB",
activationConfig = {
    @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
    @ActivationConfigProperty(propertyName = "connectionFactoryJndiName", propertyValue = "jms/cf"),
    @ActivationConfigProperty(propertyName = "destinationJndiName", propertyValue = "jms/q")
})

public class ListenerMDB implements MessageListener {
    
    @Inject 
    Event<String> event;
    
    final String FMT = "Received Message: %s";

    @PostConstruct
    public void init() {
        
    }
    
    @TraceMethod
    public void onMessage(Message message) {
        
        String msg = null;
        
        try {
            System.out.println(
                    String.format(FMT,
                    message instanceof TextMessage? ((TextMessage)message).getText(): message.toString()));
//            if (message instanceof TextMessage) {
//                msg = String.format(FMT, ((TextMessage) message).getText());
//                
//            } else {
//                msg = String.format(FMT, message.toString());
//            }
        } catch (JMSException ex) {
            Logger.getLogger(ListenerMDB.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }

    }
    
}
