package ua.nure.orlovskyi.SummaryTask4.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Application Lifecycle Listener implementation class SessionAttributeListener
 *
 */
@WebListener
public class SessionAttributeListener implements HttpSessionAttributeListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(SessionAttributeListener.class);
    /**
     * Default constructor. 
     */
    public SessionAttributeListener() {
    }

	/**
     * @see HttpSessionAttributeListener#attributeAdded(HttpSessionBindingEvent)
     */
    public void attributeAdded(HttpSessionBindingEvent event)  { 
    	String attributeName = event.getName();
		Object attributeValue = event.getValue();
		LOGGER.info("Attribute added : " + attributeName + " : " + attributeValue);
    }

	/**
     * @see HttpSessionAttributeListener#attributeRemoved(HttpSessionBindingEvent)
     */
    public void attributeRemoved(HttpSessionBindingEvent event)  { 
    	String attributeName = event.getName();
		Object attributeValue = event.getValue();
		LOGGER.info("Attribute removed : " + attributeName + " : " + attributeValue);
    }

	/**
     * @see HttpSessionAttributeListener#attributeReplaced(HttpSessionBindingEvent)
     */
    public void attributeReplaced(HttpSessionBindingEvent event)  { 
    	String attributeName = event.getName();
		Object attributeValue = event.getValue();
		if(!attributeName.equals("javax.servlet.jsp.jstl.fmt.request.charset")) {
			LOGGER.info("Attribute replaced : " + attributeName + " : " + attributeValue);
		}
    }
	
}
