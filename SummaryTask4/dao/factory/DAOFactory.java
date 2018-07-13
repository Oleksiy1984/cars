package ua.nure.orlovskyi.SummaryTask4.dao.factory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.nure.orlovskyi.SummaryTask4.connection.ConnectionPool;
import ua.nure.orlovskyi.SummaryTask4.dao.AccidentDAO;
import ua.nure.orlovskyi.SummaryTask4.dao.CarDAO;
import ua.nure.orlovskyi.SummaryTask4.dao.CreateTablesDAO;
import ua.nure.orlovskyi.SummaryTask4.dao.GenericDAO;
import ua.nure.orlovskyi.SummaryTask4.dao.OrderDAO;
import ua.nure.orlovskyi.SummaryTask4.dao.UserDAO;
import ua.nure.orlovskyi.SummaryTask4.model.Entity;

public class DAOFactory {

	private ConnectionPool pool = null;
	private Connection connection = null;
	private static ResourceBundle BUNDLE = null;
	private static String url = null;
	private static String username = null;
	private static String password = null;
	private static String driver = null;
	private static Integer connectionNumber = null;
	private static final Logger LOGGER = LoggerFactory.getLogger(DAOFactory.class);

	public static DAOFactory getInstance(String name) {
		BUNDLE = ResourceBundle.getBundle(name);
		url = BUNDLE.getString("url");
		username = BUNDLE.getString("username");
		password = BUNDLE.getString("password");
		driver = BUNDLE.getString("driver");
		connectionNumber = 5;
		return DAOManagerSingleton.INSTANCE.get();
	}

	public void startTransaction() throws SQLException {
		LOGGER.info("Transaction starts...");
		connection.setAutoCommit(false);
	}

	public void rollback() {
		LOGGER.info("Rollback transaction.");
		try {

			connection.rollback();
		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
		}

	}

	

	public void commit() throws SQLException {
		LOGGER.info("Commit transaction.");
		connection.commit();
		connection.setAutoCommit(true);
	}

	public boolean isConnected() {
		return connection != null;
	}

	public Boolean openConnection() throws SQLException {

		try {
			if (this.connection == null || this.connection.isClosed()) {
				connection = pool.getConnection();
				return true;
			} else {
				LOGGER.info("Using existing connection");
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public void close() throws SQLException {
		try {
			if (this.connection != null && !this.connection.isClosed())
				this.connection.close();
			LOGGER.info("Close connection");
		} catch (SQLException e) {
			LOGGER.info(e.getMessage());
		}
	}

	private DAOFactory() throws Exception {
		pool = ConnectionPool.getInstance();
		pool.setDriverClassName(driver);
		pool.setUrl(url);
		pool.setUsername(username);
		pool.setPassword(password);
		pool.setConnectionNumber(connectionNumber);
		pool.initConnections();

	}

	public GenericDAO getDAO(Entity entity) {

		try {
			if (this.connection == null || this.connection.isClosed()) // Let's ensure our connection is open
				this.openConnection();
		} catch (SQLException e) {
			LOGGER.info(e.getMessage());
		}

		switch (entity) {
		case Car:
			return new CarDAO(this.connection);
		case User:
			return new UserDAO(this.connection);
		case Order:
			return new OrderDAO(this.connection);
		case Accident:
			return new AccidentDAO(this.connection);
		case Create:
			return new CreateTablesDAO(this.connection);
		default:
			return null;
		}

	}

	private static class DAOManagerSingleton {

		public static final ThreadLocal<DAOFactory> INSTANCE;
		static {
			ThreadLocal<DAOFactory> dm;
			try {
				dm = new ThreadLocal<DAOFactory>() {
					@Override
					protected DAOFactory initialValue() {
						try {
							return new DAOFactory();
						} catch (Exception e) {
							return null;
						}
					}
				};
			} catch (Exception e) {
				dm = null;
			}
			INSTANCE = dm;
		}

	}

}
