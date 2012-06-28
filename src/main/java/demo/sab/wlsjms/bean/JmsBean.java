/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demo.sab.wlsjms.bean;

import demo.sab.wlsjms.resource.OrderMessageProducer;
import demo.sab.wlsjms.resource.OrderSession;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 *
 * @author sbutton
 */
@Named
@RequestScoped
public class JmsBean {

    @Inject
    JmsPublisher jmsPublisher;
    String msg = "hola!";
    String name = "pepe";

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String publishMessage() {
        String message = String.format("JSF %s, %s", getName(), getMsg());
        try {
            jmsPublisher.publishMessage(message);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Posted: " + message));
        } catch (JMSException ex) {
            Logger.getLogger(JmsBean.class.getName()).log(Level.SEVERE, null, ex);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error: " + ex.getMessage()));
        }
        return "sender";
    }
}
