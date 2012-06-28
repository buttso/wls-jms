wls-jms
=======
This is a webapp with an mdb that pushes a message onto a queue and the mdb displays it.  It uses the documented CDI approach for @Produces, @Disposes to create the required JMS resources to allow the webapp components (JSF, Servlet) to post a message onto the queue.