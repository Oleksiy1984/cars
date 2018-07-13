package ua.nure.orlovskyi.SummaryTask4.listener;


import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.nure.orlovskyi.SummaryTask4.dao.factory.DAOFactory;



/**
 * Application Lifecycle Listener implementation class DAOManagerSetup
 *
 */
@WebListener
public class DAOFactorySetup implements ServletContextListener {
	private DAOFactory dao = null;
	
	 private static final Logger LOGGER = LoggerFactory.getLogger(DAOFactorySetup.class);

	/**
	 * Default constructor.
	 */
	public DAOFactorySetup() {
		// TODO Auto-generated constructor stub
	}
	

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent sce) {
		// cleanup the connection when the context is destroyed
		LOGGER.info("contextDestroyed");
		
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent sce) {
		// access the servlet context from the event argument (renamed sce)
		// get db con info from context init params
		ServletContext sc = sce.getServletContext();

		// create the factory manager
		 dao = DAOFactory.getInstance("production");
		// put the manager into the servlet context attributes for global use in
		// the application
		sc.setAttribute("DAOManager", dao);

		LOGGER.info("DAOManager was created and added to context.");
	}

}
