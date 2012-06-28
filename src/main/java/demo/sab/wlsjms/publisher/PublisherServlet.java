/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demo.sab.wlsjms.publisher;

import demo.sab.wlsjms.bean.JmsBean;
import demo.sab.wlsjms.interceptor.TraceMethod;
import demo.sab.wlsjms.resource.OrderMessageProducer;
import demo.sab.wlsjms.resource.OrderSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Date;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author sbutton
 */
@WebServlet(name = "PublisherServlet", urlPatterns = {"/publisher"})
@TraceMethod
public class PublisherServlet extends HttpServlet {

    @Inject @OrderMessageProducer
    MessageProducer producer;
    
    @Inject @OrderSession
    Session orderSession;
    
//    @Inject JmsBean jmsBean;
    
//    @Inject
//    OrderResourceBean orb;
    
//    @Resource(name = "jms/q", type = javax.jms.Queue.class)
//    private Destination queue;
//    @Resource(name = "jms/cf", type = javax.jms.ConnectionFactory.class)
//    private ConnectionFactory connectionFactory;
    
    
    // hack to for quick message sends
    static int std, cdi = 0;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        Connection con = null;
        Session session = null;
        MessageProducer sender = null;
        TextMessage msg = null;        

        try {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet NewServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet NewServlet at " + request.getContextPath() + "</h1>");

            try {
//                con = connectionFactory.createConnection();
//                session = con.createSession(false, /*true,*/ Session.AUTO_ACKNOWLEDGE);
//                sender = session.createProducer(null);
//

//                
//                msg = session.createTextMessage(String.format("Standard approach [%s]", ++std));
//                sender.send(queue, msg);
//                printMessage(out, msg);

                // Now use CDI managed resources as a test   
                String message = String.format("CDI approach [%s]", ++cdi);
                msg = orderSession.createTextMessage(message);
                producer.send(msg);                
                
                //jmsBean.publishMessage(String.format("jmsBean approach [%s]", ++cdi));
                printMessage(out, msg);                


            } catch (JMSException ex) {
                Logger.getLogger(PublisherServlet.class.getName()).log(Level.SEVERE, null, ex);
                ex.printStackTrace();
                // dump the exception to the browser
                out.printf("<h3>Error: %s</h3>", ex.getMessage());
                out.printf("<p><code><pre>");
                ex.printStackTrace(out);
                out.printf("</pre></code></p>");
            } finally {

                if (con != null) {
                    try {
                        con.close();
                    } catch (JMSException ex) {
                        Logger.getLogger(PublisherServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            out.println("</body>");
            out.println("</html>");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void printMessage(PrintWriter out, TextMessage message) throws JMSException {
        out.printf("<p>Sending: %s %s %s</p>",
                message.getJMSMessageID(),
                DateFormat.getDateTimeInstance().format(new Date(message.getJMSTimestamp())),
                message.getText());
    }
}
