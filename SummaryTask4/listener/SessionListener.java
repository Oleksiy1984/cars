package ua.nure.orlovskyi.SummaryTask4.listener;

import java.util.Locale;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Application Lifecycle Listener implementation class SessionListener
 *
 */
@WebListener
public class SessionListener implements HttpSessionListener {
	private static final Logger LOGGER = LoggerFactory.getLogger(SessionListener.class);
    /**
     * Default constructor. 
     */
    public SessionListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent se)  { 
         
         HttpSession session = se.getSession();
         LOGGER.info("Session created with id = " + session.getId());
         session.setAttribute("locale", Locale.getDefault().getLanguage());
         LOGGER.info("Default locale = " + Locale.getDefault().getLanguage());
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent se)  { 
    	 LOGGER.info("Session destroyed.");
    }
	
}
