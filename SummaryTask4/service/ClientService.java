package ua.nure.orlovskyi.SummaryTask4.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.nure.orlovskyi.SummaryTask4.dao.UserDAO;
import ua.nure.orlovskyi.SummaryTask4.dao.factory.DAOFactory;
import ua.nure.orlovskyi.SummaryTask4.model.Entity;
import ua.nure.orlovskyi.SummaryTask4.model.User;

public class ClientService {
	private DAOFactory dao;
	private static final Logger LOGGER = LoggerFactory.getLogger(ClientService.class);

	public ClientService(DAOFactory dao) {
		this.dao = dao;
	}

	public Integer addClient(User user) {
		LOGGER.info("addClient method call");
		PasswordService handler = new PasswordService();
		String code = handler.getHashedPassword(user.getPassword());
		user.setPassword(code);
		UserDAO userDAO = (UserDAO) dao.getDAO(Entity.User);
		String passport = null;
		String login = null;
		passport = userDAO.getPassport(user.getPassport());
		login = userDAO.getLogin(user.getLogin());
		if (passport == null && login == null) {
			return userDAO.insertClient(user);
		}
		return 0;
	}

	public User getUserByCredential(String login, String password) {
		LOGGER.info("getUserByCredential method call");
		UserDAO userDAO = (UserDAO) dao.getDAO(Entity.User);
		PasswordService handler = new PasswordService();
		String code = handler.getHashedPassword(password);
		return userDAO.getUserByCredentials(login, code);

	}
}
